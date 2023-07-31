package love.heer.demo.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

// 按两次 Shift 打开“随处搜索”对话框并输入 `show whitespaces`，
// 然后按 Enter 键。现在，您可以在代码中看到空格字符。
public class Main {

    public static void main(String[] args) throws InterruptedException {

        // 1. 使用驱动实例开启会话
        WebDriver driver = new ChromeDriver();
        // 2. 在浏览器中执行操作
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");
        // 3. 请求浏览器信息
        String title = driver.getTitle();
        System.out.println(title);
        // 4. 建立等待策略
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        // 5. 发送命令查找元素
        WebElement textBox = driver.findElement(By.name("my-text"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));
        // 6. 操作元素
        textBox.sendKeys("Selenium");

        Thread.sleep(2000L);
        submitButton.click();
        // 7. 获取元素信息
        WebElement message = driver.findElement(By.id("message"));
        String value = message.getText();
        System.out.println(value);
        // 8. 退出
        driver.quit();
    }
}
