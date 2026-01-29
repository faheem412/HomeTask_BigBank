package org.uitest;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.bigbank.util.AllureStepUtil;
import org.bigbank.pages.LoanApplicationPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * UI Negative Tests - Verifies calculator modal rejects or sanitizes invalid input values
 */
@Epic("Bigbank Loan Calculator")
@Feature("Calculator Modal - Negative Tests")
public class NegativeTests extends BaseTest {

    private static final String ZERO_AMOUNT = "0";
    private static final String NEGATIVE_NUMBER = "-100";
    private static final String DECIMAL_NUMBER = "60.5";
    private static final String ALPHANUM = "345abc";
    private static final String SPECIAL_CHAR = "123$^&*";


    private LoanApplicationPage loanApplicationPage;

    //Tests invalid inputs for amount and period fields to ensure UI rejects
    @Test(description = "Verifying Loan Amount & Period Negative cases")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyNegativeCases() {
        loanApplicationPage = new LoanApplicationPage(page);

        //TC-NEG-001: Negative amount should be rejected or auto-corrected
        loanApplicationPage.navigate().enterLoanAmount(NEGATIVE_NUMBER);
        AllureStepUtil.stepWithScreenshot(page,"Negative values are not allowed, Only numeric values are allowed",
                () -> Assert.assertEquals(loanApplicationPage.getLoanAmount(),500,
                        "Negative values are not allowed")
        );

        // TC-NEG-002: Zero amount (0) should be rejected or auto corrected to minimum
        loanApplicationPage.navigate().enterLoanAmount(ZERO_AMOUNT);
        AllureStepUtil.stepWithScreenshot(page,ZERO_AMOUNT+" is not allowed, Only positive values are allowed",
                () -> Assert.assertEquals(loanApplicationPage.getLoanAmount(),500,
                        "Minimum Loan Amount should be 500")
        );

        // TC-NEG-003: Alphanumeric amount should be stripped or rejected
        loanApplicationPage.enterLoanAmount(ALPHANUM);
        AllureStepUtil.stepWithScreenshot(page,"Alpha numeric values are not allowed, Only numeric values are allowed",
                () -> Assert.assertEquals(loanApplicationPage.getLoanAmount(),500,
                        "Alpha numeric values are not allowed")
        );

        // TC-NEG-004: Special characters in amount should be rejected
        loanApplicationPage.enterLoanAmount(SPECIAL_CHAR);
        AllureStepUtil.stepWithScreenshot(page,"Special characters are not allowed, Only numeric values are allowed",
                () -> Assert.assertEquals(loanApplicationPage.getLoanAmount(),500,
                        "Special characters are not allowed")
        );

        // TC-NEG-005: Alphanumeric period should be stripped or rejected
        loanApplicationPage.enterLoanPeriod(ALPHANUM);
        AllureStepUtil.stepWithScreenshot(page,"Alpha numeric are not allowed, Only numeric values are allowed",
                () -> Assert.assertEquals(loanApplicationPage.getLoanPeriod(),120,
                        "Alpha numeric values are not allowed")
        );

        // TC-NEG-006: Special characters in period should be rejected
        loanApplicationPage.enterLoanPeriod(SPECIAL_CHAR);
        AllureStepUtil.stepWithScreenshot(page,"Special characters are not allowed, Only numeric values are allowed",
                () -> Assert.assertEquals(loanApplicationPage.getLoanPeriod(),120,
                        "Special characters are not allowed")
        );

        // TC-NEG-007: Decimal period should be rounded or rejected
        loanApplicationPage.enterLoanPeriod(DECIMAL_NUMBER);
        AllureStepUtil.stepWithScreenshot(page,"Decimal values are not allowed, Only numeric values are allowed",
                () -> Assert.assertEquals(loanApplicationPage.getLoanPeriod(),65,
                        "Decimal values are not accepted")
        );
    }

}

