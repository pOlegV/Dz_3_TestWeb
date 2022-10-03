import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

public class WebAppTest {
    private WebDriver driver;

    @BeforeAll
    static void setAppAll(){
        WebDriverManager.chromedriver().setup();
//        System.setProperty("webdriver.chrome.driver", "driver/win/chromedriver.exe");
    }

    @BeforeEach
    void setApp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @Test
    public void test(){
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петров Юрий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79874561232");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[role=button]")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void testNameHyphen() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петров-Боткин Юрий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79874561232");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[role=button]")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        assertEquals(expected, actual);
    }
    @Test
    public void testNameEnglish() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Petrov Uriy");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79874561232");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[role=button]")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void testNameNumber() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петров1 Юрий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79874561232");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[role=button]")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void testPhoneNumberNoPlus() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петров Юрий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("79874561232");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[role=button]")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }
    @Test
    public void testPhoneNumberTen() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петров Юрий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+9874561232");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[role=button]")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }
    @Test
    public void testPhoneNumberTwelve() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петров Юрий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+879874561232");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[role=button]")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void testCheckBox() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петров Юрий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79874561232");
        driver.findElement(By.cssSelector("[role=button]")).click();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid .checkbox__text")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void testNameNull() {
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79874561232");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[role=button]")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void testPhoneNull() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петров Юрий");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[role=button]")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void testNamePhoneNull() {
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[role=button]")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void testCheckBoxAndNamePhoneNull() {
        driver.findElement(By.cssSelector("[role=button]")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @AfterEach
    void closeWeb(){
        driver.close();
        driver = null;
    }
}
