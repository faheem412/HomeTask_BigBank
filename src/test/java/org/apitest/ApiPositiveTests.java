package org.apitest;

import com.microsoft.playwright.APIResponse;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.bigbank.util.ReadConfig;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * API Positive Tests - Verifies calculate endpoint returns correct monthly payment and APRC
 */
@Epic("Bigbank API")
@Feature("Calculate Endpoint - Positive Tests")
public class ApiPositiveTests extends BaseApiTest {

    private static final double interestRate = ReadConfig.getInterestRate();

    private static final double AMOUNT = Double.parseDouble(ReadConfig.getAmount());
    private static final int PERIOD = Integer.parseInt(ReadConfig.getPeriod());

    // TC-API-POS-001: Standard calculation should return valid monthly payment and APRC
    @Test(description = "Ensure API returns correct calculation for normal input")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyAPIWorksForNormalInput() {
        APIResponse response = calculateApi.calculate(AMOUNT, PERIOD);
        Assert.assertEquals(response.status(), 200);

        double payment = calculateApi.getMonthlyPayment(response);
        double aprcValue = calculateApi.getAPRC(response);


        Allure.step("Verify monthly payment value", () -> {
            Assert.assertTrue(payment > 0);
            Allure.step("Monthly payment value is :" + payment);
        });

        Allure.step("Verify APRC value", () -> {
            Assert.assertTrue(aprcValue > interestRate);
            Allure.step("APRC value is :" + aprcValue);
        });
    }

    // TC-API-POS-002: Same input should always return same output
    @Test(description = "Verify calculation is consistent for same input values")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyCalculationConsistencyForSameInput() {
        APIResponse response1 = calculateApi.calculate(AMOUNT, PERIOD);
        APIResponse response2 = calculateApi.calculate(AMOUNT, PERIOD);

        double payment1 = calculateApi.getMonthlyPayment(response1);
        double payment2 = calculateApi.getMonthlyPayment(response2);

        double aprc1 = calculateApi.getAPRC(response1);
        double aprc2 = calculateApi.getAPRC(response2);

        Allure.step("Verify monthly payment consistency", () -> {
            Assert.assertEquals(payment1, payment2);
        });

        Allure.step("Verify APRC consistency", () -> {
            Assert.assertEquals(aprc1, aprc2);
        });
    }

    // TC-API-POS-003: Higher amount should result in higher monthly payment (monotonicity)
    @Test(description = "Verify higher amount increases monthly payment")
    @Severity(SeverityLevel.NORMAL)
    public void verifyMonotonicityForAmount() {

        APIResponse resp1 = calculateApi.calculate(AMOUNT, PERIOD);
        double payment = calculateApi.getMonthlyPayment(resp1);


        APIResponse resp2 = calculateApi.calculate(AMOUNT + 10000, PERIOD);
        double updatedPayment = calculateApi.getMonthlyPayment(resp2);

        // Higher amount → higher monthly payment
        Allure.step("Verify higher amount = higher payment", () -> {
            Assert.assertTrue(updatedPayment > payment,
                    "Monthly payment should be higher if amount increases");
        });
    }

    // TC-API-POS-004: Longer period should result in lower monthly payment
    @Test(description = "Verify longer period decreases monthly payment")
    @Severity(SeverityLevel.NORMAL)
    public void verifyMonotonicityForPeriod() {
        // Test with short period
        APIResponse resp1 = calculateApi.calculate(AMOUNT, PERIOD);
        double payment = calculateApi.getMonthlyPayment(resp1);

        APIResponse resp2 = calculateApi.calculate(AMOUNT, PERIOD + 30);
        double updatedPayment = calculateApi.getMonthlyPayment(resp2);

        Allure.step("Verify longer period = lower payment", () -> {
            Assert.assertTrue(updatedPayment < payment,
                    "Monthly loan period should be less");
        });
    }

}