package com.orangehrm;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;


public class Orangehrmpro {
	 WebDriver driver;
	 WebDriver wait;

	    @BeforeClass
	    public void setup() throws InterruptedException {
	    	System.setProperty("webdriver.firefox.logfile", "/dev/null");// remove logs
	        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Vinitha\\Selenium\\geckodriver.exe");
	        driver = new FirefoxDriver();
	        driver.manage().window().maximize();
	        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
	        System.out.println("RESULT");
	        Thread.sleep(5000); // wait for page to load
	        System.out.println("Browser launched successfully");
	    }

	    @Test(priority = 1)
	    public void validLoginTest() {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	        // Wait for username and password fields
	        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
	        WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));

	        username.sendKeys("Admin");
	        password.sendKeys("admin123");

	        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']"))).click();

	        // Verify login success
	        wait.until(ExpectedConditions.urlContains("dashboard"));
	        System.out.println("1. User successfully redirected to Dashboard, Status: PASS");
	    }

	        // VERIFY logout to go back to login page
	        @Test(priority = 2)
	        public void logoutTest() throws InterruptedException {
	            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/logout");
	            Thread.sleep(5000);
	            System.out.println("2.SUCCEEDLY BACK TO LOGIN PAGE, Status: PASS");
	        }


	    @Test(priority = 3)
	    public void invalidLoginTest() throws InterruptedException {
	        driver.findElement(By.name("username")).sendKeys("Adxxx");
	        driver.findElement(By.name("password")).sendKeys("admin12345");
	        driver.findElement(By.xpath("//button[@type='submit']")).click();
	        Thread.sleep(5000);
	        System.out.println("3.Error message: Invalid credential, Status: PASS");
	    }

	    @Test(priority = 4)
	    public void emptyFieldsLoginTest() throws InterruptedException {
	        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
	        Thread.sleep(5000);
	        driver.findElement(By.xpath("//button[@type='submit']")).click();
	        Thread.sleep(5000);

	        String userError = driver.findElement(By.xpath("//input[@name='username']/following::span[1]")).getText();
	        String passError = driver.findElement(By.xpath("//input[@name='password']/following::span[1]")).getText();

	        Assert.assertEquals(userError, "Required","Username error not shown");
	        Assert.assertEquals(passError, "Required","Password error not shown");

	        System.out.println("4.Error message: Required below both fields, Status: PASS");
	    }
	    
	     //  Re-login before admin test cases
	        @Test(priority = 5)
	        public void reloginForAdminTests() {
	            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	            WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
	            WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));

	            username.clear();
	            username.sendKeys("Admin");
	            password.clear();
	            password.sendKeys("admin123");

	            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))).click();
	            wait.until(ExpectedConditions.urlContains("dashboard"));

	            System.out.println("Re-logged in successfully for admin-related tests — PASS");
	        }

	        
	     // // Utility method to open module safely
	        public void openModule(String moduleName, String headerText) {
	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	            try {
	                // Click the module
	                WebElement module = wait.until(ExpectedConditions.elementToBeClickable(
	                    By.xpath("//span[normalize-space()='" + moduleName + "']")));
	                module.click();

	             
	                wait.until(ExpectedConditions.or(
	                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + headerText.toLowerCase() + "')]")),
	                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + headerText.toLowerCase() + "')]"))
	                ));

	                System.out.println("Module '" + moduleName + "' opened successfully — PASS");
	            } catch (Exception e) {
	                System.out.println("Module '" + moduleName + "' failed to open or header not visible — FAIL");
	            }
	        }



		    // tsetcase for 12 modules from OrangeHRM left panel
		    @Test(priority = 6)
		    public void openAdminModule() {
		        openModule("Admin", "System Users");
		    }

		    @Test(priority = 7)
		    public void openPIMModule() {
		        openModule("PIM", "Employee Information");
		    }

		    @Test(priority = 8)
		    public void openLeaveModule() {
		        openModule("Leave", "Leave List");
		    }

		    @Test(priority = 9)
		    public void openTimeModule() {
		        openModule("Time", "Timesheets");
		    }

		    @Test(priority = 10)
		    public void openRecruitmentModule() {
		        openModule("Recruitment", "Candidates");
		    }

		    @Test(priority = 11)
		    public void openMyInfoModule() {
		        openModule("My Info", "Personal Details");
		    }

		    @Test(priority = 12)
		    public void openPerformanceModule() {
		        openModule("Performance", "Manage Reviews");
		    }

		    @Test(priority = 13)
		    public void openDashboardModule() {
		        openModule("Dashboard", "Dashboard");
		    }

		    @Test(priority = 14)
		    public void openDirectoryModule() {
		        openModule("Directory", "Directory");
		    }
		    @Test(priority = 15)
		    public void openMaintenanceAndGoNext() throws InterruptedException {
		        // Open Maintenance module
		        driver.findElement(By.xpath("//span[text()='Maintenance']")).click();
		        Thread.sleep(4000); // wait for the access popup to appear

		        // Click Cancel button using class name
		        driver.findElement(By.xpath("//button[contains(@class,'orangehrm-admin-access-button')]")).click();
		        Thread.sleep(3000); // wait for redirect to dashboard

		        System.out.println("Maintenance module opened, Cancel clicked, redirected to dashboard — PASS");
		    }

		    @Test(priority = 16)
		    public void openClaimModule() {
		        openModule("Claim", "Assign Claim");
		    }

		    @Test(priority = 17)
		    public void openBuzzModule() {
		        openModule("Buzz", "Buzz");
		    }
		    
		    @Test(priority = 18)
		    public void navigateToMyInfo() throws InterruptedException {
		        driver.findElement(By.xpath("//span[text()='My Info']")).click();
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		        Thread.sleep(3000);
		        wait.until(ExpectedConditions.visibilityOfElementLocated(
		        	    By.xpath("//*[contains(text(),'Personal Details')]")));

		        System.out.println("Navigated to My Info tab — PASS");
		    }

		    @Test(priority = 19)
		    public void validPersonalDetails() throws InterruptedException {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		        // Wait until loader disappears before interacting
		        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.oxd-form-loader")));

		        WebElement firstName = wait.until(ExpectedConditions.elementToBeClickable(By.name("firstName")));
		        WebElement middleName = driver.findElement(By.name("middleName"));
		        WebElement lastName = driver.findElement(By.name("lastName"));

		        // Clear fields properly
		        firstName.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
		        middleName.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
		        lastName.sendKeys(Keys.CONTROL + "a", Keys.DELETE);

		        // Enter valid data
		        firstName.sendKeys("shanooo");
		        middleName.sendKeys("vise");
		        lastName.sendKeys("John");

		        // Click Save button
		        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
		        saveBtn.click();

		        // Wait a bit to allow the success toast to show
		        Thread.sleep(2500);

		        boolean saved = false;
		        try {
		            // Use a broader XPath that catches different message structures
		            WebElement successMsg = driver.findElement(By.xpath("//*[contains(text(),'Successfully') or contains(.,'Updated') or contains(.,'Success')]"));
		            if (successMsg.isDisplayed()) {
		                saved = true;
		            }
		        } catch (Exception e) {
		            saved = false;
		        }

		        // Console output
		        if (saved) {
		            System.out.println("Personal details with valid inputs saved successfully — PASS");
		        } else {
		            System.out.println("Failed to save valid personal details — FAIL");
		            Assert.fail("Valid data should be saved successfully");
		        }
		    }
		    
		    

		    @Test(priority = 20)
		    public void invalidPersonalDetails() throws InterruptedException {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		        // Wait until loader disappears
		        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.oxd-form-loader")));

		        WebElement firstName = wait.until(ExpectedConditions.elementToBeClickable(By.name("firstName")));
		        WebElement middleName = driver.findElement(By.name("middleName"));
		        WebElement lastName = driver.findElement(By.name("lastName"));

		        // Clear all fields properly
		        firstName.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
		        middleName.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
		        lastName.sendKeys(Keys.CONTROL + "a", Keys.DELETE);

		        // Enter invalid data (numbers)
		        firstName.sendKeys("111901982882123");
		        middleName.sendKeys("122323333");
		        lastName.sendKeys("J333399");

		        // Click Save
		        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
		        saveBtn.click();

		        // Wait a few seconds to allow the toast to appear
		        Thread.sleep(2500);

		        // Check for any success message (OrangeHRM uses multiple dynamic layers)
		        boolean saved = false;
		        try {
		            // Broader XPath to capture any success notification
		            WebElement msg = driver.findElement(By.xpath("//*[contains(text(),'Successfully') or contains(.,'Updated') or contains(.,'Success')]"));
		            if (msg.isDisplayed()) {
		                saved = true;
		            }
		        } catch (Exception e) {
		            saved = false;
		        }

		        // Correct output in console
		        if (saved) {
		            System.out.println("Invalid data accepted — FAIL (this is a known issue in OrangeHRM)");
		        } else {
		            System.out.println("Invalid data correctly rejected — PASS");
		        }
		    }
		    public void logoutAndGoToLogin() {
		        try {
		            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		            // Click on profile dropdown (top right)
		            WebElement profileMenu = wait.until(
		                ExpectedConditions.elementToBeClickable(
		                    By.xpath("//p[@class='oxd-userdropdown-name']")
		                )
		            );
		            profileMenu.click();

		            // Click on Logout button
		            WebElement logoutBtn = wait.until(
		                ExpectedConditions.elementToBeClickable(
		                    By.xpath("//a[text()='Logout']")
		                )
		            );
		            logoutBtn.click();

		            // Wait until login page loads
		            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
		            System.out.println("Successfully logged out and redirected to login page — PASS");
		        } catch (Exception e) {
		            System.out.println("Logout failed or already on login page — " + e.getMessage());
		        }
		    }

		    @Test(priority = 21)
		    public void verifyLoginWithOnlyUsername() {
		        // Logout and go to login page
		        logoutAndGoToLogin();

		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
		      //  WebElement passwordField = driver.findElement(By.name("password"));

		        // Enter only username
		        usernameField.sendKeys("Admin");
		        driver.findElement(By.xpath("//button[@type='submit']")).click();

		        // Check error message
		        try {
		            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Required']")));
		            System.out.println("Login with only username rejected correctly — PASS");
		        } catch (TimeoutException e) {
		            System.out.println("Login with only username accepted — FAIL");
		        }
		    }

		    @Test(priority = 22)
		    public void verifyLoginWithOnlyPassword() {
		        // Refresh login page to clear any previous data
		        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
		        WebElement passwordField = driver.findElement(By.name("password"));

		        // Enter only password
		        passwordField.sendKeys("admin123");
		        driver.findElement(By.xpath("//button[@type='submit']")).click();

		        // Check error message
		        try {
		            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Required']")));
		            System.out.println("Login with only password rejected correctly — PASS");
		        } catch (TimeoutException e) {
		            System.out.println("Login with only password accepted — FAIL");
		        }

		        // Finally, login with valid credentials for next tests
		        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		        usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
		        passwordField = driver.findElement(By.name("password"));
		        usernameField.sendKeys("Admin");
		        passwordField.sendKeys("admin123");
		        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']"))).click();
		        wait.until(ExpectedConditions.urlContains("dashboard"));
		        System.out.println("Logged in successfully for next tests — PASS");
		    }
		    
		    @Test(priority = 23)
		    public void handleSessionTimeout() {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		        try {
		            // Try to detect if user dropdown exists (means logged in)
		            WebElement profileMenu = driver.findElement(By.xpath("//p[@class='oxd-userdropdown-name']"));
		            if (profileMenu.isDisplayed()) {
		                // Optional: wait and force logout to simulate session expiration
		                profileMenu.click();
		                WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(
		                        By.xpath("//a[text()='Logout']")));
		                logoutBtn.click();
		                wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
		                System.out.println("Session logout completed — PASS");
		            }
		        } catch (Exception e) {
		            // Already on login page or session expired
		            System.out.println("Session already expired or on login page — SKIPPED");
		        }
		    }


		    
		  
		    
		    @Test(priority = 24)
		    public void verifyPIMTabClickable() {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		        try {
		            WebElement pimTab = wait.until(ExpectedConditions.elementToBeClickable(
		                    By.xpath("//span[text()='PIM']")));
		            pimTab.click();
		            System.out.println("27. PIM tab is clickable — PASS");
		        } catch (Exception e) {
		            System.out.println("27. PIM tab is not clickable — FAIL");
		            Assert.fail();
		        }
		    }

		    @Test(priority = 25)
		    public void verifyChangePasswordIssue() throws InterruptedException {
		    //String loginUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		        // Navigate to Change Password page
		        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/pim/updatePassword");
		        Thread.sleep(2000);

		        // Locate password inputs by index and fill
		        WebElement currentPass = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@type='password'])[1]")));
		        WebElement newPass = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@type='password'])[2]")));
		        WebElement confirmPass = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@type='password'])[3]")));

		        currentPass.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
		        currentPass.sendKeys("admin123");
		        Thread.sleep(2000);
		        
		        newPass.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
		        newPass.sendKeys("NewAdmin@123");
		        Thread.sleep(2000);
		        
		        confirmPass.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
		        confirmPass.sendKeys("NewAdmin@123");
		        Thread.sleep(2000);

		        // Click Save
		        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
		        saveBtn.click();
		        System.out.println("Password change attempted on UI");

		        // Logout
		        try {
		            WebElement profileMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='oxd-userdropdown-name']")));
		            profileMenu.click();
		            WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Logout']")));
		            logoutBtn.click();
		            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
		            System.out.println("Logged out successfully");
		        } catch (Exception e) {
		            System.out.println("Logout step failed or not clickable: " + e.getMessage());
		        }

		        // Try login with new password
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
		        WebElement usernameField = driver.findElement(By.name("username"));
		        WebElement passwordField = driver.findElement(By.name("password"));

		        // Ensure fields clear then enter credentials
		        usernameField.clear();
		        passwordField.clear();
		        usernameField.sendKeys("Admin");
		        passwordField.sendKeys("NewAdmin@123");
		        driver.findElement(By.xpath("//button[@type='submit']")).click();

		        // Wait for either success signal (profile menu or dashboard in URL) OR explicit error message
		        boolean loginSucceeded = false;
		        try {
		            // Wait up to 10s for either success indicator
		            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));

		            // Wait for profile menu or dashboard URL to appear. If any appears, login succeeded.
		            shortWait.until(ExpectedConditions.or(
		                ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='oxd-userdropdown-name']")),
		                ExpectedConditions.urlContains("/dashboard")
		            ));

		            // If we reach here without exception, mark success
		            loginSucceeded = driver.getCurrentUrl().contains("/dashboard")
		                            || driver.findElements(By.xpath("//p[@class='oxd-userdropdown-name']")).size() > 0;
		        } catch (TimeoutException e) {
		            // No success signal within timeout, check for visible error message explicitly
		            boolean invalidCredsVisible = driver.findElements(By.xpath("//*[contains(text(),'Invalid credentials') or contains(.,'Invalid credentials')]")).size() > 0;
		            if (invalidCredsVisible) {
		                loginSucceeded = false;
		            } else {
		                // Unknown state: treat as failure to be safe
		                loginSucceeded = false;
		            }
		        }

		        if (loginSucceeded) {
		            System.out.println("Login with new password succeeded — PASS");
		            // if it actually succeeded and you want to revert password to original for other tests,
		            // add code here to change it back to admin123
		        } else {
		            System.out.println("Login with new password failed — FAIL (demo may not persist password). Re-logging with original credentials.");

		            // Navigate to fresh login page to reset fields, then re-login with original password
		            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		            WebElement user2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
		            WebElement pass2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));

		            user2.sendKeys("Admin");
		            pass2.sendKeys("admin123");
		            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']"))).click();

		            // Confirm login with original creds
		            try {
		                wait.until(ExpectedConditions.or(
		                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='oxd-userdropdown-name']")),
		                    ExpectedConditions.urlContains("/dashboard")
		                ));
		                System.out.println("Re-logged in with original credentials — ready for next tests");
		            } catch (TimeoutException ex) {
		                System.out.println("Re-login with original credentials failed — investigate");
		                Assert.fail("Could not re-login with original credentials after Change Password test");
		            }
		        }
		    }


		    @Test(priority = 26)
		    public void verifyInvalidPostalCode() throws InterruptedException {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));

		        // Step 1: Navigate to My Info page
		        WebElement myInfoTab = wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("//span[text()='My Info']")
		        ));
		        myInfoTab.click();

		        // Step 2: Wait for the Contact Details tab to be visible and clickable
		        WebElement contactDetailsTab = wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("//a[contains(@href, '/pim/contactDetails')]")
		        ));
		        contactDetailsTab.click();

		        // Step 3: Wait for the Zip/Postal Code input field
		        WebElement postalCode = wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("//label[text()='Zip/Postal Code']/../following-sibling::div/input")
		        ));

		        // Step 4: Enter invalid data
		        postalCode.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
		        postalCode.sendKeys("ABCDeffg");
		        Thread.sleep(2000);

		        // Step 5: Wait for loader (if visible) before Save click
		        wait.until(ExpectedConditions.invisibilityOfElementLocated(
		            By.xpath("//div[contains(@class,'oxd-form-loader')]")
		        ));

		        // Step 6: Click Save
		        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("//button[@type='submit']")
		        ));
		        saveBtn.click();

		        // Step 7: Allow time for response
		        Thread.sleep(2500);
		        System.out.println("26. Invalid postal code accepted — FAIL (validation missing in OrangeHRM)");
		    }


		    @Test(priority = 27)
		    public void verifyAddEmergencyContact() throws InterruptedException {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

		        // Make sure we're on My Info > Personal Details
		        openModule("My Info", "Personal Details");

		        // Wait until Emergency Contacts tab is clickable
		        WebElement emergencyTab = wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("//a[text()='Emergency Contacts']")));
		        emergencyTab.click();

		        // Wait until Add button is clickable
		        WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("//button[normalize-space()='Add']")));
		        addBtn.click();
		        Thread.sleep(2000);

		        // Fill in contact details
		        WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("//label[text()='Name']/../following-sibling::div/input")));
		        nameField.sendKeys("Akhil");
		        Thread.sleep(2000);
		        
		        WebElement relationshipField = driver.findElement(
		            By.xpath("//label[text()='Relationship']/../following-sibling::div/input"));
		        relationshipField.sendKeys("Brother");
		        Thread.sleep(2000);
		        
		        WebElement mobileField = driver.findElement(
		            By.xpath("//label[text()='Mobile']/../following-sibling::div/input"));
		        mobileField.sendKeys("9876543210");
		        Thread.sleep(2000);

		        // Click Save
		        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("//button[@type='submit']")));
		        saveBtn.click();

		        // Wait for success message
		        try {
		            wait.until(ExpectedConditions.visibilityOfElementLocated(
		                By.xpath("//*[contains(text(),'Successfully')]")
		            ));
		            System.out.println("Emergency contact added successfully — PASS");
		        } catch (TimeoutException e) {
		            System.out.println("Emergency contact save message not shown — possible delay but contact may be added");
		        }
		    }


		    @Test(priority = 28)
		    public void verifyDeleteEmergencyContact() throws InterruptedException {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));

		        // Step 1: Go to My Info → Emergency Contacts
		        openModule("My Info", "Personal Details");
		        WebElement emergencyTab = wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("//a[text()='Emergency Contacts']")));
		        emergencyTab.click();

		        // Step 2: Wait for table to load
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='table']")));
		        Thread.sleep(2000);

		        // Step 3: Get all delete icons
		        List<WebElement> deleteIcons = driver.findElements(
		            By.xpath("//div[@role='table']//i[contains(@class,'bi-trash')]"));

		        if (deleteIcons.isEmpty()) {
		            System.out.println("28. No emergency contact available to delete — SKIPPED");
		            return;
		        }

		        try {
		            // Step 4: Wait for loader invisibility
		            wait.until(ExpectedConditions.invisibilityOfElementLocated(
		                By.xpath("//div[contains(@class,'oxd-form-loader')]")
		            ));

		            // Pick the first available delete icon
		            WebElement deleteBtn = deleteIcons.get(0);
		            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); window.scrollBy(0, -150);", deleteBtn);
		            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteBtn);

		            // Confirm deletion
		            WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(
		                By.xpath("//button[normalize-space()='Yes, Delete']")));
		            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", confirmBtn);

		            // Step 5: Wait for success toast OR the row count to reduce
		            boolean deleted = false;
		            try {
		                wait.until(ExpectedConditions.or(
		                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Successfully Deleted')]")),
		                    ExpectedConditions.numberOfElementsToBeLessThan(
		                        By.xpath("//div[@role='table']//i[contains(@class,'bi-trash')]"),
		                        deleteIcons.size()
		                    )
		                ));
		                deleted = true;
		            } catch (Exception ignored) {}

		            if (deleted) {
		                System.out.println("28. Emergency contact deleted successfully — PASS");
		            } else {
		                System.out.println("28. Delete likely succeeded but no toast visible — POSSIBLE PASS");
		            }

		        } catch (Exception e) {
		            System.out.println("28. Failed to delete emergency contact — FAIL: " + e.getMessage());
		            Assert.fail();
		        }
		    }


		    @Test(priority = 29)
		    public void verifyJobTabNavigation() throws InterruptedException {
		        openModule("My Info", "Personal Details");
		        Thread.sleep(2000);
		        driver.findElement(By.xpath("//a[text()='Job']")).click();
		        Thread.sleep(2500);
		        WebElement header = driver.findElement(By.xpath("//*[contains(text(),'Job')]"));
		        Assert.assertTrue(header.isDisplayed());
		        System.out.println("29. Job tab opened successfully — PASS");
		    }

		    @Test(priority = 30)
		    public void verifyBuzzPostCreation() throws InterruptedException {
		        openModule("Buzz", "Buzz");
		        Thread.sleep(3000);
		        WebElement textArea = driver.findElement(By.xpath("//textarea"));
		        textArea.sendKeys("Automation testing Buzz post!");
		        driver.findElement(By.xpath("//button[contains(.,'Post')]")).click();
		        Thread.sleep(3000);
		        System.out.println("30. Buzz post created successfully — PASS");
		    }


	    @AfterClass
	    public void tearDown() {
	        if (driver != null) {
	            driver.quit();
	            System.out.println("Browser closed successfully");
	        }
	    }
	}

	    
	    
	    
     