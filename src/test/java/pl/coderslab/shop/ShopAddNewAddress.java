package pl.coderslab.shop;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ShopAddNewAddress {
    private WebDriver driver;

    @Given("I am on a main page and I have an address in Addresses")
    public void iAmOnAMainPage() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("disable-cache");
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://mystore-testlab.coderslab.pl/");
    }

    @When("I login to already created account")
    public void iLoginToAlreadyCreatedAccount() {
        driver.get("https://mystore-testlab.coderslab.pl/index.php?controller=authentication");
        Login loginPage = new Login(driver);
        loginPage.loginToSystem("mail.mail@mail.com", "Password123!");
    }

    @And("I go to the address page")
    public void iGoToTheAddressPage() {
        driver.get("https://mystore-testlab.coderslab.pl/index.php?controller=addresses");
    }

    public boolean addressIsVisible() {
        List<WebElement> addresses = driver.findElements(By.className("col-lg-4 col-md-6 col-sm-6"));
        return addresses.size() > 0;
    }

    @And("I can see there is an address")
    public void iCanSeeThereIsAnAddresses() {
        Assertions.assertTrue(addressIsVisible(), "There should be an address");
    }

    @And("I add new address")
    public void iAddNewAddress() {
        driver.findElement(By.partialLinkText("Create new address")).click();
    }

    @And("^I enter new alias (.+) address (.+) city (.+) state (.+) zip code (.+) country (.+) phone (.+)$")
    public void iEnterNewAliasAliasAddressAddressCityCityStateStateZipCodePostalCodeCountryCountryPhonePhone(
            String alias, String address, String city, String state, String postalCode, String country, String phone) {
        driver.findElement(By.id("field-alias")).sendKeys(alias);
        driver.findElement(By.id("field-address1")).sendKeys(address);
        driver.findElement(By.id("field-city")).sendKeys(city);
        driver.findElement(By.id("field-postcode")).sendKeys(postalCode);
        WebElement countryDropdownList = driver.findElement(By.id("field-id_country"));
        Select countryDropDownList = new Select(countryDropdownList);
        countryDropDownList.selectByVisibleText(country);
        driver.findElement(By.id("field-phone")).sendKeys(phone);
    }

    @And("I save the new address")
    public void iSaveTheNewAddress() {
        driver.findElement(By.xpath("//button[normalize-space()='Save']")).click();
    }

    @Then("^I verify created address alias (.+) address (.+)  city (.+) state (.+) zip code (.+) country (.+) phone (.+)$")
    public void iVerifyCreatedAddressAliasAddressCityStateZipPostalCodeCountryPhone(String alias, String address,
            String city, String state, String postalCode, String country, String phone) {
        WebElement addresses = driver
                .findElement(By.xpath("/html/body/main/section/div/div/section/section/div[2]/article/div[1]"));
        String vAddress = addresses.getText();
        Assertions.assertTrue(vAddress.contains(alias));
        Assertions.assertTrue(vAddress.contains(address));
        Assertions.assertTrue(vAddress.contains(city));
        Assertions.assertTrue(vAddress.contains(postalCode));
        Assertions.assertTrue(vAddress.contains(country));
        Assertions.assertTrue(vAddress.contains(phone));
    }

    @And("I remove the address")
    public void iRemoveTheAddress() {
        driver.findElement(By.xpath("/html/body/main/section/div/div/section/section/div[2]/article/div[2]/a[2]/span"))
                .click();
    }

    @And("I can see the address was removed")
    public void iCanSeeTheAddressWasRemoved() {
        String vAddressRemoved = driver.findElement(By.id("notifications")).getText();
        Assertions.assertTrue(vAddressRemoved.contains("Address successfully deleted!"));
    }

    @And("I close the browser")
    public void iCloseTheBrowser() {
        driver.quit();
    }
}
