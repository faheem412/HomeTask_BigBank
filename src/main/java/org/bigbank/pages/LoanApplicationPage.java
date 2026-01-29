package org.bigbank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import org.bigbank.util.ReadConfig;


public class LoanApplicationPage {

    private static final String URL = ReadConfig.geturl();
    private Page page;

    private final Locator txt_loanAmount;
    private final Locator txt_loanPeriod;
    private final Locator btn_continue;
    private final Locator lbl_monthlyPayment;
    private final Locator lbl_headerLoanAmount;

    public LoanApplicationPage(Page page) {
        this.page = page;
        this.txt_loanAmount = page.locator("input[id='header-calculator-amount']");
        this.txt_loanPeriod = page.locator("input[id='header-calculator-period']");
        this.btn_continue = page.locator("button[data-testid='bb-button']").getByText("JÄTKA");
        this.lbl_monthlyPayment = page.getByTestId("bb-labeled-value__value");
        this.lbl_headerLoanAmount = page.getByTestId("bb-edit-amount__amount");
    }

    public LoanApplicationPage navigate() {
        Allure.step("Open application");
        page.navigate(URL);
        return this;
    }

    public LoanApplicationPage enterLoanAmount(String loanAmount) {
        if (txt_loanAmount.isVisible()) {
            txt_loanAmount.clear();
            txt_loanAmount.fill(String.valueOf(loanAmount));
            page.keyboard().press("Tab");
            page.waitForTimeout(1000);
        }
        Allure.step(
                String.format("Loan amount entered is [%s]", loanAmount));
        return this;
    }

    public LoanApplicationPage enterLoanPeriod(String loanPeriod) {
        if (txt_loanPeriod.isVisible()) {
            txt_loanPeriod.clear();
            txt_loanPeriod.fill(String.valueOf(loanPeriod));
            page.keyboard().press("Tab");
            page.waitForTimeout(1000);
        }
        Allure.step(
                String.format("Loan Period entered is [%s]", loanPeriod));
        return this;
    }

    public void clickContinue() {
        btn_continue.click();
        page.waitForTimeout(500);
        Allure.step("Clicked on JÄTKA");

    }

    public String verifyMonthlyPayment() {
        return lbl_monthlyPayment.innerText();
    }

    public int getHeaderLoanAmount() {
        String val = lbl_headerLoanAmount.innerText().replaceAll("\\D", "");
        return Integer.parseInt(val);
    }

    public Double getLoanAmount(){
        return Double.parseDouble(txt_loanAmount.inputValue().replace(",",""));
    }

    public Double getLoanPeriod(){
        return Double.parseDouble(txt_loanPeriod.inputValue());
    }

}
