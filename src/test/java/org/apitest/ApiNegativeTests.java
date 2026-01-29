package org.apitest;

import com.microsoft.playwright.APIResponse;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.bigbank.util.ReadConfig;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * API Negative Tests - Verifies API rejects invalid inputs and returns appropriate error codes
 */
@Epic("Bigbank API")
@Feature("Calculate Endpoint - Negative Tests")
public class ApiNegativeTests extends BaseApiTest {

    private static final int ZERO_AMOUNT = 0;
    private static final int NEGATIVE_NUMBER = -100;
    private static final double DECIMAL_NUMBER = 60.5;

    private static final double AMOUNT = Double.parseDouble(ReadConfig.getAmount());
    private static final int PERIOD = Integer.parseInt(ReadConfig.getPeriod());
    private static final int MIN_AMOUNT = Integer.parseInt(ReadConfig.getMinAmount());
    private static final int MIN_PERIOD = Integer.parseInt(ReadConfig.getMinPeriod());
    private static final String ALPHANUM = "345abc";

    // TC-NEG-001: Negative amount should return validation error
    @Test(description = "Verify API returns validation error for negative loan amount")
    public void verifyCalculationWithNegativeAmount() {
        Map<String, Object> body = calculateApi.buildBaseRequest();
        body.put("amount", NEGATIVE_NUMBER);
        body.put("maturity", PERIOD);

        APIResponse response = calculateApi.calculate(body);
        Allure.step("Verify API responds with validation error", () -> {
            Assert.assertEquals(response.status(), 500,
                    "Minimum loan amount should be" + MIN_AMOUNT);
        });
    }

    // TC-NEG-002: Negative period should return validation error
    @Test(description = "Verify API returns validation error for negative loan period")
    public void verifyCalculationWithNegativePeriod() {
        Map<String, Object> body = calculateApi.buildBaseRequest();
        body.put("amount", AMOUNT);
        body.put("maturity", NEGATIVE_NUMBER);

        APIResponse response = calculateApi.calculate(body);

        Assert.assertEquals(response.status(), 500,
                "Minimum loan period should be" + MIN_PERIOD);
    }

    // TC-NEG-003: Zero amount should return validation error
    @Test(description = "Verify API rejects zero loan amount")
    public void verifyCalculationWithZeroLoanAmount() {
        Map<String, Object> body = calculateApi.buildBaseRequest();
        body.put("amount", ZERO_AMOUNT);
        body.put("maturity", PERIOD);

        APIResponse response = calculateApi.calculate(body);

        Assert.assertEquals(response.status(), 500,
                "Minimum loan amount should be" + MIN_AMOUNT);
    }

    // TC-NEG-004: Zero period should return validation error
    @Test(description = "Verify API rejects zero loan period")
    public void verifyCalculationWithZeroPeriod() {
        Map<String, Object> body = calculateApi.buildBaseRequest();
        body.put("amount", AMOUNT);
        body.put("maturity", ZERO_AMOUNT);

        APIResponse response = calculateApi.calculate(body);

        Assert.assertEquals(response.status(), 500,
                "Minimum loan period should be" + MIN_PERIOD);
    }

    // TC-NEG-005: Decimal maturity should return validation error
    @Test(description = "Verify API behavior for decimal loan period")
    public void verifyCalculationWithDecimalLoanPeriod() {
        Map<String, Object> body = calculateApi.buildBaseRequest();
        body.put("amount", AMOUNT);
        body.put("maturity", DECIMAL_NUMBER);

        APIResponse response = calculateApi.calculate(body);

        Assert.assertEquals(response.status(), 400,
                "Decimal value for loan period is not allowed");
    }

    // TC-NEG-006: Empty amount should return validation error
    @Test(description = "Verify API rejects when amount is empty")
    public void verifyCalculationWithAmountIsEmpty() {
        Map<String, Object> body = calculateApi.buildBaseRequest();
        body.put("amount", "");
        body.put("maturity", PERIOD);

        APIResponse response = calculateApi.calculate(body);

        Assert.assertEquals(response.status(), 400, "Amount should be minimum " + MIN_AMOUNT);
    }

    // TC-NEG-007: Empty period should return validation error
    @Test(description = "Verify API rejects when period is empty")
    public void verifyCalculationWithPeriodIsEmpty() {
        Map<String, Object> body = calculateApi.buildBaseRequest();
        body.put("amount", AMOUNT);
        body.put("maturity", "");

        APIResponse response = calculateApi.calculate(body);

        Assert.assertEquals(response.status(), 400, "Period should be minimum  " + MIN_PERIOD);
    }

    // TC-NEG-008: Alphanumeric amount should return validation error
    @Test(description = "Verify API rejects when alphanumeric is entered for loan amount")
    public void verifyCalculationWithAlphanumericAmount() {
        Map<String, Object> body = calculateApi.buildBaseRequest();
        body.put("amount", ALPHANUM);
        body.put("maturity", PERIOD);

        APIResponse response = calculateApi.calculate(body);

        Assert.assertEquals(response.status(), 400, "API should reject alphanumeric value for loan amount");
    }

    // TC-NEG-009: Alphanumeric period should return validation error
    @Test(description = "Verify API rejects when alphanumeric is entered for loan period")
    public void verifyCalculationWithAlphanumericPeriod() {
        Map<String, Object> body = calculateApi.buildBaseRequest();
        body.put("amount", AMOUNT);
        body.put("maturity", ALPHANUM);

        APIResponse response = calculateApi.calculate(body);

        Assert.assertEquals(response.status(), 400, "API should reject alphanumeric value for loan period");
    }

}

