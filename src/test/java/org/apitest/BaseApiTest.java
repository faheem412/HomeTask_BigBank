package org.apitest;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import org.bigbank.api.CalculateApi;
import org.bigbank.util.ReadConfig;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseApiTest {

    protected Playwright playwright;
    protected APIRequestContext apiContext;
    protected CalculateApi calculateApi;

    private static final String BASE_URI = ReadConfig.getApiBaseURI();
    @BeforeClass
    public void setupApi() {
        playwright = Playwright.create();
        apiContext = playwright.request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL(BASE_URI)
                        .setExtraHTTPHeaders(java.util.Map.of(
                                "Content-Type", "application/json",
                                "Accept", "application/json"
                        ))
        );

        calculateApi = new CalculateApi(apiContext);
    }

    @AfterClass
    public void tearDownApi() {
        if (apiContext != null) {
            apiContext.dispose();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
