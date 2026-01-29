package org.uitest;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.bigbank.pages.LoanApplicationPage;
import org.bigbank.util.AllureStepUtil;
import org.bigbank.util.ReadConfig;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test Suite: Boundary Value Analysis for Loan Modal
 * Verifies UI enforces min/max constraints for amount and maturity
 */

@Epic("Bigbank Loan Calculator")
@Feature("Calculator Modal - Boundary value Tests")
public class BoundaryValueTests extends BaseTest {

    private static final int MAX_AMOUNT = Integer.parseInt(ReadConfig.getMaxAmount());
    private static final int MAX_PERIOD = Integer.parseInt(ReadConfig.getMaxPeriod());
    private static final int MIN_AMOUNT = Integer.parseInt(ReadConfig.getMinAmount());
    private static final int MIN_PERIOD = Integer.parseInt(ReadConfig.getMinPeriod());


    private LoanApplicationPage loanApplicationPage;


    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Verify Loan Amount & Period Boundary Values")
    public void verifyLoanAmountBoundaries() {
        loanApplicationPage = new LoanApplicationPage(page);

        // Amount below minimum (499) should auto-correct to 500
        loanApplicationPage.navigate().enterLoanAmount(String.valueOf(MIN_AMOUNT - 1));
        AllureStepUtil.stepWithScreenshot(page, "User is unable to set Loan Amount 499",
                () -> Assert.assertEquals(500, loanApplicationPage.getLoanAmount(),
                        "Minimum Loan Amount should be 500")
        );

        // Exact minimum amount (500) should be accepted
        loanApplicationPage.enterLoanAmount(String.valueOf(MIN_AMOUNT));
        AllureStepUtil.stepWithScreenshot(page, "User can set Loan Amount "+MIN_AMOUNT,
                () -> Assert.assertEquals(loanApplicationPage.getLoanAmount(), 500,
                        "Loan Amount "+MIN_AMOUNT+" should be accepted")
        );

        // Amount just above minimum (501) should be accepted
        loanApplicationPage.enterLoanAmount(String.valueOf(MIN_AMOUNT + 1));
        AllureStepUtil.stepWithScreenshot(page, "User can set Loan Amount "+(MIN_AMOUNT+1),
                () -> Assert.assertEquals(loanApplicationPage.getLoanAmount(), 501,
                        "Loan Amount "+MIN_AMOUNT+1+" should be accepted")
        );

        // Amount just below maximum (29999) should be accepted
        loanApplicationPage.enterLoanAmount(String.valueOf(MAX_AMOUNT - 1));
        AllureStepUtil.stepWithScreenshot(page, "User can set Loan Amount "+(MAX_AMOUNT-1),
                () -> Assert.assertEquals(loanApplicationPage.getLoanAmount(), 29999,
                        "Loan Amount 29999 should be accepted")
        );

        // Exact maximum amount (30000) should be accepted
        loanApplicationPage.enterLoanAmount(String.valueOf(MAX_AMOUNT));
        AllureStepUtil.stepWithScreenshot(page, "User can set Loan Amount "+MAX_AMOUNT,
                () -> Assert.assertEquals(loanApplicationPage.getLoanAmount(), 30000,
                        "Loan Amount 30000 should be accepted")
        );

        // Amount above maximum (30001) should auto correct to 30000
        loanApplicationPage.enterLoanAmount(String.valueOf(MAX_AMOUNT + 1));
        AllureStepUtil.stepWithScreenshot(page, "User is unable to set Loan Amount "+(MAX_AMOUNT+1),
                () -> Assert.assertEquals(loanApplicationPage.getLoanAmount(), 30000,
                        "Maximum Loan Amount should be 30000")
        );

        // Period below minimum (5) should auto correct to 6
        loanApplicationPage.enterLoanPeriod(String.valueOf(MIN_PERIOD - 1));
        AllureStepUtil.stepWithScreenshot(page, "User is unable to set Loan Period "+(MIN_PERIOD-1),
                () -> Assert.assertEquals(loanApplicationPage.getLoanPeriod(), 6,
                        "Minimum Loan Period should be 6 months")
        );

        // Exact minimum period (6) should be accepted
        loanApplicationPage.enterLoanPeriod(String.valueOf(MIN_PERIOD));
        AllureStepUtil.stepWithScreenshot(page, "User can set Loan Period "+(MIN_PERIOD),
                () -> Assert.assertEquals(loanApplicationPage.getLoanPeriod(), 6,
                        "Loan Period 6 should be accepted")
        );

        // Period just above minimum (7) should be accepted
        loanApplicationPage.enterLoanPeriod(String.valueOf(MIN_PERIOD + 1));
        AllureStepUtil.stepWithScreenshot(page, "User can set Loan Period "+(MIN_PERIOD+1),
                () -> Assert.assertEquals(loanApplicationPage.getLoanPeriod(), 7,
                        "Loan Period 7 should be accepted")
        );

        // Period just below maximum (119) should be accepted
        loanApplicationPage.enterLoanPeriod(String.valueOf(MAX_PERIOD - 1));
        AllureStepUtil.stepWithScreenshot(page, "User can set Loan Period "+(MAX_PERIOD-1),
                () -> Assert.assertEquals(loanApplicationPage.getLoanPeriod(), 119,
                        "Loan Period 119 should be accepted")
        );

        // Exact maximum period (120) should be accepted
        loanApplicationPage.enterLoanPeriod(String.valueOf(MAX_PERIOD));
        AllureStepUtil.stepWithScreenshot(page, "User can set Loan Period "+(MAX_PERIOD),
                () -> Assert.assertEquals(loanApplicationPage.getLoanPeriod(), 120,
                        "Loan Period 120 should be accepted")
        );

        // Period above maximum (121) should auto-correct to 120
        loanApplicationPage.enterLoanPeriod(String.valueOf(MAX_PERIOD + 1));
        AllureStepUtil.stepWithScreenshot(page, "User is unable to set Loan Period "+(MAX_PERIOD+1),
                () -> Assert.assertEquals(loanApplicationPage.getLoanPeriod(), 120,
                        "Maximum Loan Period should be 120 months")
        );
    }

}
