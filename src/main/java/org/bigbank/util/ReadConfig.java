package org.bigbank.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadConfig {

    private ReadConfig(){}

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ReadConfig.class.getClassLoader()
                .getResourceAsStream("Config.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                throw new RuntimeException("Config.properties not found in classpath");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Config.properties", e);
        }
    }

    public static String geturl() {
        return properties.getProperty("appURL");
    }

    public static String getApiBaseURI() {
        return properties.getProperty("baseURI");
    }

    public static String getApiEndpoint() {
        return properties.getProperty("endpoint");
    }

    public static boolean isHeadless() {
        String exec = properties.getProperty("headless");
        return Boolean.parseBoolean(exec);
    }

    public static String getAmount() {
        return properties.getProperty("amount");
    }

    public static String getPeriod() {
        return properties.getProperty("period");
    }

    public static String getDefaultAmount() {
        return properties.getProperty("default.amount");
    }

    public static String getDefaultPeriod() {
        return properties.getProperty("default.period");
    }

    public static String getMinAmount() {
        return properties.getProperty("amount.min");
    }

    public static String getMinPeriod() {
        return properties.getProperty("period.min");
    }

    public static String getMaxAmount() {
        return properties.getProperty("amount.max");
    }

    public static String getMaxPeriod() {
        return properties.getProperty("period.max");
    }


    public static String getCurrency() {
        return properties.getProperty("api.currency");
    }

    public static String getProductType() {
        return properties.getProperty("api.productType");
    }

    public static double getAdministrationFee() {
        return Double.parseDouble(properties.getProperty("api.administrationFee"));
    }

    public static int getConclusionFee() {
        return Integer.parseInt(properties.getProperty("api.conclusionFee"));
    }

    public static int getMonthlyPaymentDay() {
        return Integer.parseInt(properties.getProperty("api.monthlyPaymentDay"));
    }

    public static double getInterestRate() {
        return Double.parseDouble(properties.getProperty("api.interestRate"));
    }
}
