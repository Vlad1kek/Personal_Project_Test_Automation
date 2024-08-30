package page.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.time.Duration;
import java.awt.datatransfer.Clipboard;

abstract class BaseModel {
    private final WebDriver driver;
    private Actions action;
    private WebDriverWait wait;

    public BaseModel(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));
        }

        return wait;
    }

    protected WebDriver getDriver() {
        return driver;
    }

    protected Actions getAction() {
        if (action == null) {
            action = new Actions(driver);
        }

        return action;
    }

    public static void clearClipboard() {
        // Получаем доступ к системному буферу обмена
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();

        // Создаем пустую строку и устанавливаем ее в буфер обмена
        StringSelection emptySelection = new StringSelection("");
        clipboard.setContents(emptySelection, null);
    }

    // Метод для получения текста из буфера обмена
     public String getClipboardText() throws UnsupportedFlavorException, IOException {
        clearClipboard();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        java.awt.datatransfer.Clipboard clipboard = toolkit.getSystemClipboard();
        return (String) clipboard.getData(DataFlavor.stringFlavor);
    }
}