package org.uitest;

import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import org.bigbank.util.ReadConfig;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.ByteArrayInputStream;

/**
 * Base class for UI tests - handles browser lifecycle and screenshot capture on failure
 */
public class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeMethod
    public void setup() {
        // Initialize Playwright and launch browser
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setChannel("chrome")
                        .setHeadless(ReadConfig.isHeadless())
        );
        context = browser.newContext();
        page = context.newPage();

        Allure.step("Browser initialized");
    }

    @AfterMethod
    protected void tearDown(ITestResult result) {
        // Take screenshot only if test failed
        if (ITestResult.FAILURE == result.getStatus()) {
            String testName = result.getName();
            Allure.addAttachment(testName + " - Failure Screenshot",
                    new ByteArrayInputStream(
                            page.screenshot(new Page.ScreenshotOptions().setFullPage(true))
                    ));
        }

        // Close browser and cleanup
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }

        Allure.step("Browser closed");
    }
}
