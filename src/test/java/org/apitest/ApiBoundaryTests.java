package org.apitest;

import com.microsoft.playwright.APIResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.bigbank.util.ReadConfig;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * API Boundary Tests - Verifies API correctly handles min/max boundaries and edge cases
 */

@Epic("Bigbank API")
@Feature("Calculate Endpoint - Boundary Tests")
public class ApiBoundaryTests extends BaseApiTest {

    private static final double AMOUNT = Double.parseDouble(ReadConfig.getAmount());
    private static final int PERIOD = Integer.parseInt(ReadConfig.getPeriod());
    private static final int MAX_AMOUNT = Integer.parseInt(ReadConfig.getMaxAmount());
    private static final int MAX_PERIOD = Integer.parseInt(ReadConfig.getMaxPeriod());
    private static final int MIN_AMOUNT = Integer.parseInt(ReadConfig.getMinAmount());
    private static final int MIN_PERIOD = Integer.parseInt(ReadConfig.getMinPeriod());



    //Amount = Max + 1 (30001) should be rejected
    @Test(description = "Verify API rejects amount greater than max threshold")
    public void verifyCalculationWithGreaterThanThresholdAmount() {
        Map<String, Object> body = calculateApi.buildBaseRequest();
        body.put("amount", MAX_AMOUNT+1);
        body.put("maturity", PERIOD);

        APIResponse response = calculateApi.calculate(body);

        Assert.assertEquals(response.status(), 400,"Maximum amount should be "+MAX_AMOUNT);
    }

    //Period = Max + 1 (121) should be rejected
    @Test(description = "Verify API rejects period greater than max threshold")
    public void verifyCalculationWithGreaterThanThresholdPeriod() {
        Map<String, Object> body = calculateApi.buildBaseRequest();
        body.put("amount", AMOUNT);
        body.put("maturity", MAX_PERIOD + 1);

        APIResponse response = calculateApi.calculate(body);

        Assert.assertEquals(response.status(), 400,"Maximum period should be "+ MAX_PERIOD);
    }

    //TC-BND-003: Amount = Min - 1 (499) should be rejected
    @Test(description = "Verify API behavior for less than minimum loan amount")
    public void verifyCalculationWithLessThanMinAmount() {
        Map<String, Object> body = calculateApi.buildBaseRequest();
        body.put("amount", MIN_AMOUNT - 1);
        body.put("maturity", PERIOD);

        APIResponse response = calculateApi.calculate(body);

        Assert.assertEquals(response.status(), 400,
                "Minimum loan amount period should be " + MIN_AMOUNT);
    }

    //TC-BND-004: Period = Min - 1 (5) should be rejected
    @Test(description = "Verify API rejects less than minimum loan period")
    public void verifyCalculationWithLessThanMinPeriod() {
        Map<String, Object> body = calculateApi.buildBaseRequest();
        body.put("amount", AMOUNT);
        body.put("maturity", MIN_PERIOD - 1);

        APIResponse response = calculateApi.calculate(body);

        Assert.assertEquals(response.status(), 400,
                "Minimum loan period should be "+ MIN_PERIOD);
    }
}
