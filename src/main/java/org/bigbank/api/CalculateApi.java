package org.bigbank.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.bigbank.util.ReadConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Calculate API Service - Handles loan calculation API requests and response parsing
 */
public class CalculateApi {

    private static final String ENDPOINT = ReadConfig.getApiEndpoint();
    private final APIRequestContext apiContext;
    private final ObjectMapper mapper = new ObjectMapper();

    public CalculateApi(APIRequestContext apiContext) {
        this.apiContext = apiContext;
    }

    /**
     * Send calculate request with amount and maturity (other fields use defaults)
     * @param loanAmount Loan amount in EUR
     * @param maturity Loan period in months
     * @return API response containing monthly payment and APRC
     */
    @Step("POST /api/v1/loan/calculate with amount={amount}, maturity={maturity}")
    public APIResponse calculate(double loanAmount, int maturity) {
        Map<String, Object> body = buildRequestBody(loanAmount, maturity);

        Allure.addAttachment("Request Body", "application/json",
                body.toString(), ".json");

        APIResponse response = apiContext.post(ENDPOINT,
                RequestOptions.create().setData(body));

        Allure.addAttachment("Response Status", String.valueOf(response.status()));
        Allure.addAttachment("Response Body", "application/json",
                response.text(), ".json");

        return response;
    }

    /**
     * Send calculate request with custom payload (for negative/edge case testing)
     * @param body Custom request body with overridden fields
     * @return API response
     */
    @Step("POST /api/v1/loan/calculate with custom payload")
    public APIResponse calculate(Map<String, Object> body) {

        Allure.addAttachment("Request Body", "application/json",
                body.toString(), ".json");

        APIResponse response = apiContext.post(ENDPOINT,
                RequestOptions.create().setData(body));

        Allure.addAttachment("Response Status", String.valueOf(response.status()));
        Allure.addAttachment("Response Body", "application/json",
                response.text(), ".json");

        return response;
    }

    /**
     * Build complete request body with all required fields (defaults from config)
     * @param amount Loan amount
     * @param maturity Loan period in months
     * @return Map containing all request fields
     */
    private Map<String, Object> buildRequestBody(double amount, int maturity) {
        Map<String, Object> body = new HashMap<>();
        body.put("currency", ReadConfig.getCurrency());
        body.put("productType", ReadConfig.getProductType());
        body.put("maturity", maturity);
        body.put("administrationFee", ReadConfig.getAdministrationFee());
        body.put("conclusionFee", ReadConfig.getConclusionFee());
        body.put("amount", amount);
        body.put("monthlyPaymentDay", ReadConfig.getMonthlyPaymentDay());
        body.put("interestRate", ReadConfig.getInterestRate());
        return body;
    }

    /**
     * Build base request body with defaults (amount & maturity to be added by caller)
     * Used for negative tests where invalid values need to be injected
     * @return Map with default fields (no amount/maturity)
     */
    public Map<String, Object> buildBaseRequest() {
        Map<String, Object> body = new HashMap<>();
        body.put("currency", ReadConfig.getCurrency());
        body.put("productType", ReadConfig.getProductType());
        body.put("administrationFee", ReadConfig.getAdministrationFee());
        body.put("conclusionFee", ReadConfig.getConclusionFee());
        body.put("monthlyPaymentDay", ReadConfig.getMonthlyPaymentDay());
        body.put("interestRate", ReadConfig.getInterestRate());
        return body;
    }

    // Extract monthly payment value from API response
    @Step("Extract monthly payment from response")
    public double getMonthlyPayment(APIResponse response) {
        try {
            JsonNode json = mapper.readTree(response.text());
            return json.get("monthlyPayment").asDouble();
        } catch (Exception e) {
            Allure.addAttachment("Error", e.getMessage());
            throw new RuntimeException("Failed to parse response", e);
        }
    }

    // Extract APRC (Annual Percentage Rate of Charge) from API response
    @Step("Extract APRC value from response")
    public double getAPRC(APIResponse response) {
        try {
            JsonNode json = mapper.readTree(response.text());
            return json.get("apr").asDouble();
        } catch (Exception e) {
            Allure.addAttachment("Error", e.getMessage());
            throw new RuntimeException("Failed to parse response", e);
        }
    }
}
