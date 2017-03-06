package smartcash.smartcash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NB_SC_01 
{
	WebDriver driver;
	Common com=new Common();
	SCRequest request=new SCRequest();
	Compare compare=new Compare();
	@BeforeClass
	public void setUp() throws Exception
	{
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Dimension d = new Dimension(1535,741);
		driver.manage().window().setSize(d);
		driver.manage().window().maximize();
		driver.get(com.getBaseURL());
	}
	@Test 
	public void smartcash() throws Exception
	{
		HashMap<String,String> insured=new HashMap<String,String>();
		CustomDate startdob=new CustomDate();
		startdob=startdob.add(Period.days, -90);
		CustomDate enddob=new CustomDate();
		enddob=enddob.add(Period.years, -100);
		CustomDate eldest_dob=new CustomDate();
		eldest_dob=eldest_dob.add(Period.years, -com.generateRandomNumber(18, 60));
		CustomDate nominee_dob=new CustomDate();
		nominee_dob=com.generateRandomDate(enddob,startdob);
		assertEquals(driver.getTitle(), "Royal Sundaram | Smart Cash");
		driver.findElement(By.className("buynewRadio")).click();
		WebElement date;
		//Choosing Plan type and daily benefit
		fill_plan_type();
		driver.findElement(By.className("cp-rightarrow")).click();
		//Choosing No Of persons and Eldest person DOB
		fill_noOfInsured(eldest_dob);
		driver.findElement(By.className("cp-rightarrow")).click();
        //Proposer Info
        driver.findElement(By.id("prospername")).sendKeys(com.getNewUsername());
        driver.findElement(By.id("prospermobileno")).sendKeys(com.getMobileNo());
        driver.findElement(By.id("prosperemail")).sendKeys(com.getrandomEmailId());
        request.setUsername( driver.findElement(By.id("prospername")).getAttribute("value"));
        request.setEmailid(driver.findElement(By.id("prosperemail")).getAttribute("value"));
        request.setMobile(driver.findElement(By.id("prospermobileno")).getAttribute("value"));
        
        driver.findElement(By.className("cp-rightarrow")).click();
        Waiting.waitForTextToBePresent(driver, By.id("planTypeView"), request.getPlantype(), 120);
        
        compare.comparetabs(driver, "premium", request);
        
        //Premium Details
        String poi=com.getRandomValue("one|two|three");
        driver.findElement(By.className("premium"+poi)).click();
      
        compare.comparetabs(driver, "premium", request);
        driver.findElement(By.id("proceed")).click();
        
        //Close Alert
        driver.findElement(By.id("closeproceed")).click();
        
        //Proposer Details
        //Last name
        driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[2]/div[1]/div/div[1]/p")).click();
        driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[2]/div[2]/div/input")).sendKeys(com.getLastName());
        //Gender
        driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[3]/div[1]/div/div[1]/p")).click();
        driver.findElements(By.name("proposerGender")).get(com.generateRandomNumber(0,1)).click();
        //Annual Income
        driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[4]/div[1]/div/div[1]/p")).click();
        new Select(driver.findElement(By.id("annualIncome"))).selectByIndex(com.generateRandomNumber(1, 5));
        //Marital Status
        driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[5]/div[1]/div/div[1]/p")).click();
        driver.findElements(By.name("maritalStatus")).get(com.generateRandomNumber(0,1)).click();
        //Occupation
        driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[6]/div[1]/div/div[1]/p")).click();
        new Select(driver.findElement(By.id("occupation"))).selectByIndex(com.generateRandomNumber(1, 10));
        //Pan Number
        driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[9]/div[1]/div/div[1]/p")).click();
        driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[9]/div[2]/div/input")).sendKeys(com.getPan());
        //DOB
        driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[10]/div[1]/div/div[1]/p")).click();
        driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[10]/div[2]/div/span")).click();
        new Select(driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/div/div/select[2]"))).selectByValue(eldest_dob.getDateTimeStamp("yyyy"));
        new Select(driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/div/div/select[1]"))).selectByVisibleText(eldest_dob.getDateTimeStamp("MMM"));    
        date=driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/table"));
        date.findElement(By.linkText(String.valueOf(Math.round(Integer.parseInt(eldest_dob.getDateTimeStamp("dd")))))).click();
        //Nominee First name 
        driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[12]/div[1]/div/div[1]/p")).click();
        driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[12]/div[2]/div/input")).sendKeys(com.getNominee_name());
        //Nominee Last name
        driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[13]/div[1]/div/div[1]/p")).click();
        driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[13]/div[2]/div/input")).sendKeys(com.getNominee_lname());
        //Nominee DOB
        driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[14]/div[1]/div/div[1]/p")).click();
        driver.findElement(By.xpath(".//*[@id='nomineeDob']")).click();
        new Select(driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/div/div/select[2]"))).selectByValue(nominee_dob.getDateTimeStamp("yyyy"));
        new Select(driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/div/div/select[1]"))).selectByVisibleText(nominee_dob.getDateTimeStamp("MMM"));    
        date=driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/table"));
        date.findElement(By.linkText(String.valueOf(Math.round(Integer.parseInt(nominee_dob.getDateTimeStamp("dd")))))).click();
        //Nominee Relationship
        driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[15]/div[1]/div/div[1]/p")).click();
        new Select(driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[15]/div[2]/div/span/select"))).selectByVisibleText(com.getNominee_rel());
        
        //Guardian Details
        if(com.find_years(nominee_dob)<18)
        {
        	//Guardian First name
            driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[17]/div[1]/div/div[1]/p")).click();
            driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[17]/div[2]/div/input")).sendKeys(com.getGuardian_name());
            //Guardian Last name
            driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[18]/div[1]/div/div[1]/p")).click();
            driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[18]/div[2]/div/input")).sendKeys(com.getGuardian_lname());
            //Guardian Age
            driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[19]/div[1]/div/div[1]/p")).click();
            driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[19]/div[2]/div/input")).sendKeys(com.getGuardian_age());
            //Guardian Relationship
            driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[20]/div[1]/div/div[1]/p")).click();
            new Select(driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[20]/div[2]/div/span/select"))).selectByVisibleText(com.getGuardian_rel());
            
        }
        loaddetails("proposer");
        driver.findElement(By.xpath(".//*[@id='proposerdetailsubmit']")).click();
        
        //Contact Info - Address1
        driver.findElement(By.xpath(".//*[@id='contactform-single']/li[1]/div[1]/div/div[1]/p")).click();
        Waiting.waitForElementToBeDisplay(driver,By.xpath(".//*[@id='contactform-single']/li[1]/div[2]/div/input") , 100);
        driver.findElement(By.xpath(".//*[@id='contactform-single']/li[1]/div[2]/div/input")).sendKeys(com.getAddress1Text());
        //Address2
        driver.findElement(By.xpath(".//*[@id='contactform-single']/li[2]/div[1]/div/div[1]/p")).click();
        Waiting.waitForElementToBeDisplay(driver,By.xpath(".//*[@id='contactform-single']/li[2]/div[2]/div/input") , 100);
        driver.findElement(By.xpath(".//*[@id='contactform-single']/li[2]/div[2]/div/input")).sendKeys(com.getAddress2Text());
        //Address3
        driver.findElement(By.xpath(".//*[@id='contactform-single']/li[3]/div[1]/div/div[1]/p")).click();
        Waiting.waitForElementToBeDisplay(driver,By.xpath(".//*[@id='contactform-single']/li[3]/div[2]/div/input") , 100);
        driver.findElement(By.xpath(".//*[@id='contactform-single']/li[3]/div[2]/div/input")).sendKeys(com.getAddress1Text());
        //City
        driver.findElement(By.xpath(".//*[@id='contactform-single']/li[4]/div[1]/div/div[1]/p")).click();
        Waiting.waitForElementToBeDisplay(driver,By.id("contactCity"), 100);
        driver.findElement(By.id("contactCity")).sendKeys(com.getCityText());
        Thread.sleep(1000);
        //State
        driver.findElement(By.xpath(".//*[@id='contactform-single']/li[5]/div[1]/div/div[1]/p")).click();
        Waiting.waitForElementToBeDisplay(driver,By.id("contactState") , 100);
        driver.findElement(By.id("contactState")).sendKeys("Tamilnadu");
        //Pincode
        driver.findElement(By.xpath(".//*[@id='contactform-single']/li[6]/div[1]/div/div[1]/p")).click();
        Waiting.waitForElementToBeDisplay(driver,By.xpath(".//*[@id='contactform-single']/li[6]/div[2]/div/input") , 100);
        driver.findElement(By.xpath(".//*[@id='contactform-single']/li[6]/div[2]/div/input")).sendKeys(com.getPincodeText());
       
        assertEquals(driver.findElement(By.id("mobilenoValid")).getText(), request.getMobile());
        assertEquals(driver.findElement(By.xpath(".//*[@id='contactform-single']/li[9]/div[1]/div/div[2]/p")).getText(), request.getEmailid());
        loaddetails("contact");
        driver.findElement(By.id("contactdetailsubmit")).click();
        
     //Insured Details
     int j=1;
     List<InsuredPersons> insuredperson=new ArrayList<InsuredPersons>();
     for(int i=1;i<=request.getNoOfpersons();i++)
      {
   	   		InsuredPersons person= new InsuredPersons();
    	  	loaddata(insured, i);
    	  	loadperson(insured,person);
       		driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+(++j)+"]/div[1]/div/div[1]/p")).click(); System.out.println(j);
       		new Select(driver.findElement(By.id("relationship"+i))).selectByValue(insured.get("rel"));
       		if(i!=1 && insured.get("rel").equalsIgnoreCase("Others"))
       		{
       			driver.findElement(By.xpath(".//*[@id='ior"+i+"']/div[1]/div/div[1]/p")).click();
   				driver.findElement(By.xpath(".//*[@id='ior"+i+"']/div[2]/div/input")).sendKeys(insured.get("other_rel"));
       		}
       		j=j+2;
       		//First name
       		driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+j+"]/div[1]/div/div[1]/p")).click();
       		driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+j+"]/div[2]/div/input")).sendKeys(insured.get("fname"));
       		//Last Name
       		driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+(++j)+"]/div[1]/div/div[1]/p")).click();System.out.println(j);
       		driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+j+"]/div[2]/div/input")).sendKeys(insured.get("lname"));
       		//Gender
       		driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+(++j)+"]/div[1]/div/div[1]/p")).click();
       		if(i==1)
       		{
       			driver.findElement(By.id("insuredgender"+insured.get("gen"))).click();
       			System.out.println("self:"+insured.get("gen"));
       		}
       		else
       			driver.findElement(By.id("insuredgender"+insured.get("gen")+i)).click();
       		//DOB
       		driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+(++j)+"]/div[1]/div/div[1]/p")).click();System.out.println(j);
       		driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+j+"]/div[2]/div/span")).click();
       		CustomDate insuredDob;
       		if(i==1)
       			insuredDob=eldest_dob;
       		else
       			insuredDob=com.generateRandomDate(eldest_dob, startdob);
       		person.setDate_of_Birth(insuredDob);
            new Select(driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/div/div/select[2]"))).selectByValue(insuredDob.getDateTimeStamp("yyyy"));
       		new Select(driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/div/div/select[1]"))).selectByVisibleText(insuredDob.getDateTimeStamp("MMM"));    
            date=driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/table"));
            date.findElement(By.linkText(String.valueOf(Math.round(Integer.parseInt(insuredDob.getDateTimeStamp("dd")))))).click();
            //Pre-Existing Disease - Proceeding stops for case No - Has to be fixed
            driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+(++j)+"]/div[1]/div/div[1]/p")).click();System.out.println(j);
            driver.findElement(By.id("preDisease"+insured.get("exist_disease")+i)).click();
       		if(driver.findElements(By.name("preDisease"+i)).get(0).isSelected())
       		{
       			driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+(++j)+"]/div[1]/div/div[1]/p")).click();System.out.println("dis:"+j);
       			new Select(driver.findElement(By.id("diseaseName"+i))).selectByValue(insured.get("disease"));
       			driver.findElement(By.id("diseaseName"+i)).sendKeys(Keys.ENTER);
       			
       		}
       		else
       			j++;
       		//PlanType
       		driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+(++j)+"]/div[1]/div/div[1]/p")).click();System.out.println(j);
       		new Select(driver.findElement(By.id("planType"+i))).selectByValue(insured.get("planType"));
       		//Daily Benefit
       		driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+(++j)+"]/div[1]/div/div[1]/p")).click();System.out.println(j);
       		new Select(driver.findElement(By.id("dailyBenefit"+i))).selectByIndex(Integer.parseInt(insured.get("benefit")));
       		//Personal Accident Cover
       		driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+(++j)+"]/div[1]/div/div[1]/p")).click();System.out.println(j);
       		driver.findElement(By.id("paCover"+insured.get("PAcover")+i)).click();
       		//Sum Insured
       		driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+(++j)+"]/div[1]/div/div[1]/p")).click();System.out.println(j);
       		new Select(driver.findElement(By.id("sumInsured"+i))).selectByIndex(1);
       		person.setSum_Insured(new Select(driver.findElement(By.id("sumInsured"+i))).getFirstSelectedOption().getAttribute("value"));
       		//Occupation
       		driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+(++j)+"]/div[1]/div/div[1]/p")).click(); System.out.println(j);
       		new Select(driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+j+"]/div[2]/div/span/select"))).selectByValue(insured.get("occupation"));
       		if(insured.get("occupation").equalsIgnoreCase("Others") || insured.get("occupation").equalsIgnoreCase("Salaried"))
       		{
       			driver.findElement(By.xpath(".//*[@id='insuredDesignation"+i+"']/div[1]/div/div[1]/p")).click();
       			driver.findElement(By.xpath(".//*[@id='insuredDesignation"+i+"']/div[2]/div/input")).sendKeys(insured.get("designation"));
       		}
       		else if(insured.get("occupation").equalsIgnoreCase("Self Employed"))
       		{
       			driver.findElement(By.xpath(".//*[@id='insuredBusOcc"+i+"']/div[1]/div/div[1]/p")).click();
       			driver.findElement(By.xpath(".//*[@id='insuredBusOcc"+i+"']/div[2]/div/input")).sendKeys(insured.get("business"));
       		}
       		
       		if(driver.findElements(By.name("paCover"+i)).get(0).isSelected()) //If Personal Accident Cover YES
       		{
       			j=j+3;
       			//Nominee First Name
           		driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+(++j)+"]/div[1]/div/div[1]/p")).click();System.out.println("nom:"+j);
           		driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+j+"]/div[2]/div/input")).sendKeys(insured.get("nom_fname"));       
           		//Nominee Last Name
           		driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+(++j)+"]/div[1]/div/div[1]/p")).click();System.out.println(j);
           		driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+j+"]/div[2]/div/input")).sendKeys(insured.get("nom_lname"));
           		//Nominee DOB
           		nominee_dob=com.generateRandomDate(enddob,startdob);
           		person.setNom_dob(nominee_dob);
           		driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+(++j)+"]/div[1]/div/div[1]/p")).click();System.out.println(j);
           		driver.findElement(By.xpath(".//*[@id='insuredNomineeDob"+i+"']")).click();
                new Select(driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/div/div/select[2]"))).selectByValue(nominee_dob.getDateTimeStamp("yyyy"));
           		new Select(driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/div/div/select[1]"))).selectByVisibleText(nominee_dob.getDateTimeStamp("MMM"));    
                date=driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/table"));
                date.findElement(By.linkText(String.valueOf(Math.round(Integer.parseInt(nominee_dob.getDateTimeStamp("dd")))))).click();
                //Nominee Relationship
                driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+(++j)+"]/div[1]/div/div[1]/p")).click();System.out.println(j);
                new Select(driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+j+"]/div[2]/div/span/select"))).selectByValue(insured.get("nom_rel"));;
                //Guardian Details
                if(com.find_years(nominee_dob)<18)
                {
                   	j=j+1;
                   	//Guardian First Name
               		driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+(++j)+"]/div[1]/div/div[1]/p")).click();System.out.println("gd"+j);
               		driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+j+"]/div[2]/div/input")).sendKeys(insured.get("G_fname"));
               		//Guardian Last Name
               		driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+(++j)+"]/div[1]/div/div[1]/p")).click();System.out.println(j);
               		driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+j+"]/div[2]/div/input")).sendKeys(insured.get("G_lname"));
               		//Guardian Age
               		driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+(++j)+"]/div[1]/div/div[1]/p")).click();System.out.println(j);
               		driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+j+"]/div[2]/div/input")).sendKeys(insured.get("G_age"));
               		//Guardian Relationship
                    driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+(++j)+"]/div[1]/div/div[1]/p")).click();System.out.println(j);
                    new Select(driver.findElement(By.xpath(".//*[@id='insuredform-single']/li["+j+"]/div[2]/div/span/select"))).selectByValue(insured.get("G_rel"));
                    j++;
                   }
                   else
                   {
                   	j=j+6;
                   	System.out.println("noG"+j);
                   }
       		}
       		else
       		{
       			j=j+13;
       			System.out.println("Nonom:"+j);
       		}
       		System.out.println("jend:"+j);
       		insuredperson.add(person);
       }
     request.setInsuredPersons(insuredperson);
     driver.findElement(By.xpath(".//*[@id='lifestylesubmit']")).click();
     
     //Already Insured
     driver.findElement(By.xpath(".//*[@id='alreadyinsuredli']/div[1]/div/div[1]/p")).click();
     driver.findElement(By.id("alreadyinsured"+com.getRandomValue("no"))).click();
     //previous insured details
     if(driver.findElement(By.id("alreadyinsured"+com.getRandomValue("yes"))).isSelected())
     {
     //Insured Name
     driver.findElement(By.xpath(".//*[@id='generaldetailsform-single']/li[3]/div[1]/div/div[1]/p")).click();
     new Select(driver.findElement(By.xpath(".//*[@id='generaldetailsform-single']/li[3]/div[2]/div/span/select"))).selectByIndex(1);
     //Insurer name
     driver.findElement(By.xpath(".//*[@id='generaldetailsform-single']/li[4]/div[1]/div/div[1]/p")).click();
     new Select(driver.findElement(By.xpath(".//*[@id='generaldetailsform-single']/li[4]/div[2]/div/span/select"))).selectByIndex(com.generateRandomNumber(1, 10));
     //Policy Number
     driver.findElement(By.xpath(".//*[@id='generaldetailsform-single']/li[5]/div[1]/div/div[1]/p")).click();
     driver.findElement(By.xpath(".//*[@id='generaldetailsform-single']/li[5]/div[2]/div/input")).sendKeys(com.getRandomAlphabetic(8));
     
     CustomDate ins_date=new CustomDate();
     ins_date=ins_date.add(Period.years, -com.generateRandomNumber(0, 10));
     CustomDate ins_edate=com.generateRandomDate(ins_date, ins_date.add(Period.years, 10));
     System.out.println(ins_date.getDateTimeStamp("dd/MM/yyyy"));
     //Policy start date
     driver.findElement(By.xpath(".//*[@id='generaldetailsform-single']/li[6]/div[1]/div/div[1]/p")).click();
     driver.findElement(By.xpath(".//*[@id='generaldetailsform-single']/li[6]/div[1]/div/div[1]/p[1]")).click();
     driver.findElement(By.className("fromdate")).click();
     new Select(driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/div/div/select[2]"))).selectByValue(ins_date.getDateTimeStamp("yyyy"));
	 new Select(driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/div/div/select[1]"))).selectByVisibleText(ins_date.getDateTimeStamp("MMM"));    
     date=driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/table"));
     date.findElement(By.linkText(String.valueOf(Math.round(Integer.parseInt(ins_date.getDateTimeStamp("dd")))))).click();
     
     //Policy End date
     driver.findElement(By.xpath(".//*[@id='generaldetailsform-single']/li[6]/div[1]/div/div[2]/p[1]")).click();
     System.out.println(ins_edate.getDateTimeStamp("dd/MM/yyyy"));
     driver.findElement(By.className("todate")).click();
     new Select(driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/div/div/select[2]"))).selectByValue(ins_edate.getDateTimeStamp("yyyy"));
	 new Select(driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/div/div/select[1]"))).selectByVisibleText(ins_edate.getDateTimeStamp("MMM"));    
     date=driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/table"));
     date.findElement(By.linkText(String.valueOf(Math.round(Integer.parseInt(ins_edate.getDateTimeStamp("dd")))))).click();
     
     //Sum Insured
     driver.findElement(By.xpath(".//*[@id='suminsuredli']/div[1]/div/div[1]/p")).click();
     driver.findElement(By.xpath(".//*[@id='suminsuredli']/div[2]/div/input")).sendKeys(com.getRandomNumeric(5));
     }
     driver.findElement(By.id("generaldetailsubmit")).click();
     compare.comparetabs(driver, "alert", request);
     driver.findElement(By.id("reconfirm")).click();
     //Decleration Page
     driver.findElement(By.xpath(".//*[@id='paymentdetailsForm']/div[1]/div/p[1]/label")).click();
     driver.findElement(By.id("declarationlink")).click();
     driver.findElement(By.xpath(".//*[@id='declarationmodal']/div/div/div[1]/button")).click();
     driver.findElement(By.xpath(".//*[@id='paymentdetailsForm']/div[2]/div[2]/p/label")).click();
     driver.findElement(By.xpath(".//*[@id='paymentdetailsForm']/div[2]/div[1]/p/label")).click();
     driver.findElement(By.id("makepayment")).click();
     
	}
	@AfterClass
	public void tearDown() throws Exception 
	{
	 // driver.quit();
	}
	
	public void loaddata(HashMap<String,String> insured,int i)
	{
		if(i==1)
		{
			insured.put("rel", "Self");
			insured.put("other_rel","");
			insured.put("gen",request.getGender().toLowerCase());
			//insured.put("gen", com.getRandomValue("male|female"));
			insured.put("self_gen", insured.get("gen"));
		}
		else
		{
			insured.put("rel", com.getRandomValue("Spouse|Son|Daughter|Others"));
			insured.put("rel","Spouse");
			if(insured.get("rel").equalsIgnoreCase("Others"))
				insured.put("other_rel", com.getRandomAlphabetic(10));
			if(insured.get("rel").equalsIgnoreCase("Spouse"))
			{
				if(insured.get("self_gen").equalsIgnoreCase("Male"))
					insured.put("gen","female");
				else
					insured.put("gen", "male");
			}
			else if(insured.get("rel").equalsIgnoreCase("daughter"))
				insured.put("gen", "female");
			else
				insured.put("gen", "male");
		}
		insured.put("fname", com.getRandomAlphabetic(15));
		insured.put("lname", com.getRandomAlphabetic(15));
		insured.put("exist_disease",com.getRandomValue("Yes"));//Has to be changed
		insured.put("disease","EPILEPSY");
		insured.put("planType",com.getRandomValue("Silver|Gold|Platinum"));
		insured.put("benefit",com.getBenefit(insured.get("planType")));
		insured.put("PAcover", com.getRandomValue("Yes|No"));
		if(insured.get("gen").equalsIgnoreCase("female"))
			insured.put("occupation", com.getRandomValue("SALARIED|SELF EMPLOYED|HOUSEWIFE|STUDENT|OTHERS"));
		else
			insured.put("occupation", com.getRandomValue("SALARIED|SELF EMPLOYED|STUDENT|OTHERS"));
		insured.put("business", com.getRandomAlphabetic(12));
		insured.put("designation", com.getRandomAlphabetic(12));
		insured.put("nom_fname",com.getRandomAlphabetic(12));
		insured.put("nom_lname",com.getRandomAlphabetic(12));
		insured.put("nom_rel", com.getRandomValue("Aunt|Brother|Cousin|Mother|Father|GrandFather|GrandMother|Daughter|Son|Uncle|Sister"));
		insured.put("G_fname",com.getRandomAlphabetic(12));
		insured.put("G_lname",com.getRandomAlphabetic(12));
		insured.put("G_rel", com.getRandomValue("Aunt|Brother|Cousin|Mother|Uncle|Sister"));
		insured.put("G_age", String.valueOf(com.generateRandomNumber(18, 99)));	
	}
	
	public void loadperson(HashMap<String,String> insured,InsuredPersons person)
	{
		person.setRelationship_with_Proposer(insured.get("rel"));
		person.setOther_Relationship_with_Proposer(insured.get("other_rel"));
		person.setFirst_Name(insured.get("fname"));
		person.setLast_Name(insured.get("lname"));
		person.setGender(insured.get("gen"));
		person.setPre_existing_Disease(insured.get("exist_disease"));
		person.setDisease_list(insured.get("disease"));
		person.setPlan_Type(insured.get("planType"));
		person.setDaily_Benefit(insured.get("benefit"));
		person.setOccupation(insured.get("occupation"));
		person.setBusiness(insured.get("business"));
		person.setDesignation(insured.get("designation"));
		person.setNom_First_Name(insured.get("nom_fname"));
		person.setNom_Last_Name(insured.get("nom_lname"));
		person.setNom_Relationship_with_Proposer(insured.get("nom_rel"));
		person.setG_First_Name(insured.get("G_fname"));
		person.setG_Last_Name(insured.get("G_lname"));
		person.setG_Relationship_with_Nominee(insured.get("G_rel"));
		
	}
	public void loaddetails(String type)
	{
		if(type.equalsIgnoreCase("proposer"))
		{
		request.setUsername(driver.findElement(By.id("firstnameValid")).getText());
		request.setLastname(driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[2]/div[1]/div/div[2]/p")).getText());
		request.setGender(driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[3]/div[1]/div/div[2]/p")).getText());
		request.setIncome(driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[4]/div[1]/div/div[2]/p")).getText());
		request.setMarital_status(driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[5]/div[1]/div/div[2]/p")).getText());
		request.setOccupation(driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[6]/div[1]/div/div[2]/p")).getText());
		request.setPanNo(driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[9]/div[1]/div/div[2]/p")).getText());
		request.setDOB(driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[10]/div[1]/div/div[2]/p")).getText());
		request.setNom_fname(driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[12]/div[1]/div/div[2]/p")).getText());
		request.setNom_lname(driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[13]/div[1]/div/div[2]/p")).getText());
		request.setNom_dob(driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[14]/div[1]/div/div[2]/p")).getText());
		request.setNom_rel(driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[15]/div[1]/div/div[2]/p")).getText());
		request.setG_fname(driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[17]/div[1]/div/div[2]/p")).getText());
		request.setG_lname(driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[18]/div[1]/div/div[2]/p")).getText());
		request.setG_age(driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[19]/div[1]/div/div[2]/p")).getText());
		request.setG_rel(driver.findElement(By.xpath(".//*[@id='proposalform-single']/li[20]/div[1]/div/div[2]/p")).getText());
		System.out.println(request.getDOB());
		}
		else
		{
			request.setAddress1(driver.findElement(By.xpath(".//*[@id='contactform-single']/li[1]/div[1]/div/div[2]/p")).getText());
			request.setAddress2(driver.findElement(By.xpath(".//*[@id='contactform-single']/li[2]/div[1]/div/div[2]/p")).getText());
			request.setAddress3(driver.findElement(By.xpath(".//*[@id='contactform-single']/li[3]/div[1]/div/div[2]/p")).getText());
			request.setCity(driver.findElement(By.id("cityValid")).getText());
			request.setState(driver.findElement(By.id("stateValid")).getText());
			request.setPincode(driver.findElement(By.xpath(".//*[@id='contactform-single']/li[6]/div[1]/div/div[2]/p")).getText());
			request.setMobile(driver.findElement(By.id("mobilenoValid")).getText());
			request.setStd(driver.findElement(By.id("stdvalid")).getText());
			request.setPhone(driver.findElement(By.id("stdphonevalid")).getText());
			System.out.println(request.getPincode());
		}
	}
	public void fill_plan_type()
	{
		String plantype=com.getRandomPlanType();
		driver.findElement(By.className(plantype)).click();
		String benefit=com.getBenefit(plantype);
		driver.findElement(By.xpath(".//*[@id='planyears']/ul/li["+benefit+"]/span")).click();
		String daily_benefit=driver.findElement(By.xpath(".//*[@id='planyears']/ul/li["+benefit+"]/span")).getText();
		request.setPlantype(plantype);
		request.setDaily_benfit(daily_benefit,benefit);
		
	}
	public void fill_noOfInsured(CustomDate eldest_dob)
	{
		int no=com.generateRandomNumber(1, 7);
		driver.findElement(By.xpath("//*[@id='familycombMain']/div/div/div/div/div[3]/div/div/div/ul/li["+no+"]")).click();
		request.setNoOfpersons(no);
		driver.findElement(By.id("eldestdate")).click();
		driver.findElement(By.xpath(".//*[@id='familycombMain']/div/div/div/div/div[4]/p[1]/span")).click();
		new Select(driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/div/div/select[1]"))).selectByVisibleText(eldest_dob.getDateTimeStamp("MMM"));
		new Select(driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/div/div/select[2]"))).selectByValue(eldest_dob.getDateTimeStamp("yyyy"));
        WebElement date=driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/table"));
        date.findElement(By.linkText(String.valueOf(Math.round(Integer.parseInt(eldest_dob.getDateTimeStamp("dd")))))).click();
        request.setEldestDOB(eldest_dob.getDateTimeStamp("dd/MM/yyyy"));
       
	}
}
	

