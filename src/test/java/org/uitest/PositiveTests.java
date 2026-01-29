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

@Epic("Bigbank Loan Calculator")
@Feature("Calculator Modal - Positive Tests")
public class PositiveTests extends BaseTest {

    private static final String AMOUNT = ReadConfig.getAmount();
    private static final String PERIOD =ReadConfig.getPeriod();

    private LoanApplicationPage loanApplicationPage;

    // TC-POS-001: Changing both amount and period should trigger monthly payment recalculation
    @Test(priority = 1, description = "TC:01-Verify Monthly Payment amount changes on Loan Amount and Loan Period changes")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyMonthlyPaymentOnLoanAmountAndPeriodChange() {
        loanApplicationPage = new LoanApplicationPage(page);
        loanApplicationPage.navigate();
        String amount = loanApplicationPage.verifyMonthlyPayment();
        loanApplicationPage.enterLoanAmount(AMOUNT)
                .enterLoanPeriod(PERIOD);
        String updatedMonthlyPayment = loanApplicationPage.verifyMonthlyPayment();

        AllureStepUtil.stepWithScreenshot(
                page, String.format(
                        "Monthly payment amount is changed from [%s] to [%s]", amount, updatedMonthlyPayment),
                () -> Assert.assertNotEquals(amount, updatedMonthlyPayment,
                        "Monthly payment should change when loan details change"
                )
        );
    }

    // TC-POS-002: Changing only amount should trigger monthly payment recalculation
    @Test(priority = 2, description = "TC:02-Verify Monthly Payment amount changes on Loan Amount changes")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyMonthlyPaymentChangeOnLoanAmountChange() {
        loanApplicationPage = new LoanApplicationPage(page);
        loanApplicationPage.navigate();
        String amount = loanApplicationPage.verifyMonthlyPayment();
        loanApplicationPage.enterLoanAmount(AMOUNT);
        String updatedMonthlyPayment = loanApplicationPage.verifyMonthlyPayment();

        AllureStepUtil.stepWithScreenshot(
                page, String.format(
                        "Monthly payment amount is changed from [%s] to [%s]", amount, updatedMonthlyPayment),
                () -> Assert.assertNotEquals(amount, updatedMonthlyPayment,
                        "Monthly payment should change when loan amount change"
                )
        );
    }

    // TC-POS-003: Changing only period should trigger monthly payment recalculation
    @Test(priority = 3, description = "TC:03-Verify Monthly Payment amount changes on Loan Period changes")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyMonthlyPaymentChangeOnLoanPeriodChange() {
        loanApplicationPage = new LoanApplicationPage(page);
        loanApplicationPage.navigate();
        String amount = loanApplicationPage.verifyMonthlyPayment();
        loanApplicationPage.enterLoanPeriod(PERIOD);
        String updatedMonthlyPayment = loanApplicationPage.verifyMonthlyPayment();

        AllureStepUtil.stepWithScreenshot(
                page, String.format(
                        "Monthly payment amount is changed from [%s] to [%s]", amount, updatedMonthlyPayment),
                () -> Assert.assertNotEquals(amount, updatedMonthlyPayment,
                        "Monthly payment should change when loan period change"
                )
        );
    }

    // TC-POS-004: Header should display updated amount after clicking JÄTKA (persistence test)
    @Test(priority = 4, description = "TC:04-Verify header Loan Amount shows updated Loan Amount after clicking JÄTKA")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyHeaderAmountAfterClickingContinue() {
        loanApplicationPage = new LoanApplicationPage(page);
        loanApplicationPage.navigate().enterLoanAmount(AMOUNT)
                .enterLoanPeriod(PERIOD)
                .clickContinue();

        AllureStepUtil.stepWithScreenshot(
                page, "Header Loan Amount is updated after clicking JÄTKA",
                () -> Assert.assertEquals(loanApplicationPage.getHeaderLoanAmount(), Integer.parseInt(AMOUNT),
                        "Header Loan Amount should be displayed"
                )
        );
    }

    // TC-POS-005: Header should NOT update if JÄTKA is not clicked (non-persistence test)
    @Test(priority = 5, description = "TC:05-Verify header Loan Amount doesn't update without clicking JÄTKA")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyHeaderIgnoreUnsavedChanges() {
        loanApplicationPage = new LoanApplicationPage(page);
        loanApplicationPage.navigate().enterLoanAmount(AMOUNT);

        AllureStepUtil.stepWithScreenshot(
                page, "Header Loan Amount is not updated without clicking JÄTKA",
                () -> Assert.assertNotEquals(loanApplicationPage.getHeaderLoanAmount(), 25000,
                        "Header Loan Amount should not be changed"
                )
        );
    }

    // TC-POS-006: Header should match modal value when JÄTKA is clicked without changes
    @Test(priority = 6, description = "TC:06-Verify header Loan Amount value same on clicking JÄTKA")
    @Severity(SeverityLevel.MINOR)
    public void verifyHeaderValueSameAsLoanAmountOnClickContinue() {
        loanApplicationPage = new LoanApplicationPage(page);
        loanApplicationPage.navigate();
        double loanAmt = loanApplicationPage.getLoanAmount();
        loanApplicationPage.clickContinue();

        AllureStepUtil.stepWithScreenshot(
                page, String.format("Header Loan Amount is same after clicking JÄTKA, value is %s",loanAmt),
                () -> Assert.assertEquals(loanAmt, loanApplicationPage.getHeaderLoanAmount(),
                        "Header Loan Amount should be same"
                )
        );
    }
}