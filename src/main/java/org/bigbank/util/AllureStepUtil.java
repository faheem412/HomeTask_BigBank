package org.bigbank.util;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;

/**
 * Allure Utility - Provides helper methods for adding steps and screenshots to Allure reports
 */
public class AllureStepUtil {

    private AllureStepUtil() {}

    //  Execute assertion with Allure step and attach screenshot to report
    public static void stepWithScreenshot(
            Page page,
            String message,
            Runnable assertion
    ) {
        Allure.step(message, () -> {
            assertion.run();
            addScreenshot(page, message);
        });
    }

    //  Capture and attach full-page screenshot to Allure report
    public static void addScreenshot(Page page, String message) {
        Allure.addAttachment(message, "image/png",
                new ByteArrayInputStream(
                        page.screenshot(new Page.ScreenshotOptions().setFullPage(true))
                ), ".png"
        );
    }
}
