import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Locale;

public class createOrder {

    static WebDriver driver;
    String url="https://www.hepsiburada.com/";
    String keyword="kalem";
    //Tarayıcı açılır.
    @BeforeClass
    public static void setUp()
    {
        ChromeOptions options=new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");//Sidedeki otomasyon kontrol sistemini devre dışı bırakır.
        options.addArguments("--disable-extensions");
        WebDriverManager.chromedriver().setup();
        driver=new ChromeDriver(options);
        driver.manage().window().maximize();//Pencere maksimum boyut yapılır.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));//15 saniye bekleme yapılır.
        System.out.println("Test için Chrome driver oluşturuldu.");

    }
    @Test(priority=2,description = "Üyelik bilgileri ile test")
    public void testWithLogin()
    {
        System.out.println("***Üyelik Bilgileri ile test başladı.***");
        String userEmail="******";
        String userPassword="*****.";
        String userName="******";
        driver.manage().deleteAllCookies();
        //“https://www.hepsiburada.com/” adresine gidilir.
        driver.get(url);

        System.out.println(String.format("-- %s adresine gidildi.",url));

        //“giriş yap ” butotunu üzerine gelinir."giriş yap" elementine tıklanır.
        WebElement loginButton= driver.findElement(By.id("myAccount"));
        Actions actions=new Actions(driver);
        actions.moveToElement(loginButton).perform();
        WebElement login=driver.findElement(By.xpath("//a[@id='login']"));
        login.click();

        System.out.println("-- Giriş yap aksiyonuna tıklandı.");

        //“email” adresi girilir.
        WebElement emailBox=driver.findElement(By.xpath("//input[@id='txtUserName']"));
        emailBox.sendKeys(userEmail);

        System.out.println("-- Email adresi girildi.");



        //“giriş yap butonuna tıklanır” butonuna tıklanır.
        driver.findElement(By.xpath("//button[@id='btnLogin']")).click();

        System.out.println("-- Giriş yap aksiyonuna tıklandı.");

        //"şifre"girilir.
        WebElement password =driver.findElement(By.id("txtPassword"));
        password.sendKeys(userPassword);

        System.out.println("-- Şifre girildi.");

        //“giriş yap butonuna tıklanır” butonuna tıklanır.
        driver.findElement(By.id("btnEmailSelect")).click();

        System.out.println("-- Giriş yap aksiyonuna tıklandı.");


        //kullanıcı girişi doğrulanır.
        WebElement expectedLogin=driver.findElement(By.xpath(String.format("//span[text()='%s']",userName)));
        String expectedResult=expectedLogin.getText();
        Assert.assertEquals(expectedResult,userName);//Beklenen kullanıcı ile giriş yapılan kullanıcı aynımı kontrolü yapıldı.

        System.out.println("-- Kullanıcı girişi doğrulandı.");

        addBasket();

    }

    public void addBasket()
    {
        try {
            Thread.sleep(3000);
        }catch (Exception e){}

        //ürün arama yapılır.
        WebElement searchBar=driver.findElement(By.xpath("//input[@class='desktopOldAutosuggestTheme-UyU36RyhCTcuRs_sXL9b']"));
        searchBar.sendKeys(keyword);
        driver.findElement(By.xpath("//div[text()='ARA']")).click();
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(1000));

        System.out.println(String.format("-- %s ürün araması yapıldı.",keyword));

        //ürün şeçimi yapılır ve datey sayfasına gidilir.
        WebElement product =driver.findElement(By.id("i0"));
        WebElement productLink=product.findElement(By.xpath(".//a"));
        var productUrl =productLink.getAttribute("href");//ilgili ürünün linki bulunur.
        driver.navigate().to(productUrl);

        System.out.println("-- Seçilen ürünün detayına gidildi.");


        String expectedProductName=driver.findElement(By.id("product-name")).getText();//Seçilen ürün ismi doğrulama adımı için alınır.

        //İlgili ürün sepete eklenir.
        WebElement firstBasketProduct = driver.findElement(By.id("addToCart"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", firstBasketProduct);

        System.out.println("-- Sepete ekle butonuna tıklandı.");

        try {
            Thread.sleep(5000);
        }catch (Exception e){}


        WebElement firstProductSaler =driver.findElement(By.xpath("//div[@class='checkoutui-ProductOnBasketHeader-135jZ']"));
        String firstProductSalerName =correctString(firstProductSaler.getText());//İlk satıcı ismi doğrulama için alınır.


        driver.findElement(By.xpath("//a[@class='checkoutui-Modal-2iZXl']")).click();//Popup kapatılır.

        //Farklı satıcıdan ürün sepete eklenir.
        WebElement secondBasketProduct = driver.findElement(By.xpath("(//form[@action='/ShoppingCart/AddToCart'])[2]"));
        secondBasketProduct.submit();

        System.out.println("-- Farklı satıcıdan aynı ürün eklendi.");

        try {
            Thread.sleep(5000);
        }catch (Exception e){}

        WebElement secondProductSaler =driver.findElement(By.xpath("//div[@class='checkoutui-ProductOnBasketHeader-135jZ']"));
        String secondProductSalerName =correctString(secondProductSaler.getText());//ikinci satıcı ismi doğrulama için alınır.

        //Sepete gidilir.
        driver.findElement(By.xpath("//button[text()='Sepete git']")).click();//sepete gidilir.

        System.out.println("-- Sepete git butonuna tıklandı.");


        //Sepetteki ürünler doğrulanır.

        WebElement basketInFirstProduct = driver.findElement(By.xpath("(//div[@class='product_name_3Lh3t'])[1]"));
        WebElement basketInFirstProductLink=basketInFirstProduct.findElement(By.xpath(".//a"));
        String actualFirstProductName=basketInFirstProductLink.getText();//sepetteki ürün ismi alınır.
        Assert.assertEquals(expectedProductName,actualFirstProductName);// ürün doğrulanır..

        System.out.println("-- 1. ürün doğrulandı.");

        WebElement basketInSecondProduct = driver.findElement(By.xpath("(//div[@class='product_name_3Lh3t'])[2]"));
        WebElement basketInSecondProductLink=basketInSecondProduct.findElement(By.xpath(".//a"));
        String actualSecondProductName=basketInSecondProductLink.getText();
        Assert.assertEquals(expectedProductName,actualSecondProductName);

        System.out.println("-- 2. ürün doğrulandı.");

        WebElement basketInFirstSaler = driver.findElement(By.xpath("(//span[@class='merchantLink_2Ii8s'])[2]"));
        String actualFirstSaler = correctString(basketInFirstSaler.getText());
        Assert.assertEquals(firstProductSalerName,actualFirstSaler);

        System.out.println("-- 1. satıcı doğrulandı.");

        WebElement basketInSecondSaler = driver.findElement(By.xpath("(//span[@class='merchantLink_2Ii8s'])[1]"));
        String actualSecondSaler = correctString(basketInSecondSaler.getText());
        Assert.assertEquals(secondProductSalerName,actualSecondSaler);

        System.out.println("-- 2. satıcı doğrulandı.");

        //Ürünler silinir.
        WebElement deleteFirstProduct = driver.findElement(By.xpath("(//a[@class='delete_product_3DFC0'])[1]"));
        deleteFirstProduct.click();
        System.out.println("-- 1. ürün silindi.");

        try {
            Thread.sleep(2000);
        }catch (Exception e){}

        WebElement deleteSecondProduct = driver.findElement(By.xpath("(//a[@class='delete_product_3DFC0'])[1]"));
        deleteSecondProduct.click();
        System.out.println("-- 2. ürün silindi.");
    }

    @Test(priority = 1,description = "Üyeliksiz test")
    public void testWithoutLogin()
    {
        System.out.println("***Üyeliksiz test başladı.***");
        //“https://www.hepsiburada.com/” adresine gidilir.
        driver.get(url);
        System.out.println(String.format("-- %s adresine gidildi.",url));

        addBasket();
    }

    public String correctString(String input)
    {
        return input.
                replace("Satıcı ","").
                replace("i̇̇","i").
                toUpperCase(Locale.ENGLISH).
                replace('Ş','S').
                replace('Ğ','G').
                replace('Ö','O').
                replace('Ü','U').
                replace("İ","I").replace('İ','I');


    }

    @AfterClass
    public static void down()
    {
        driver.close();//Driver kapatılır.
    }


}
