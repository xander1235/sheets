package com.triffy.sheet.validators;

public class Validator {

    public static boolean isValidMobileNumber(String mobileNumber) {
        // Check if the mobile number is not null and contains only digits
        if (mobileNumber != null && mobileNumber.matches("\\d+")) {
            // Remove any leading '+' or '0' characters
            String cleanedNumber = mobileNumber.replaceFirst("^[+0]+", "");

            // Assuming a valid mobile number should have 10 or 11 digits
            // Modify this condition according to the specific rules for your country's mobile numbers
            return cleanedNumber.length() >= 10 && cleanedNumber.length() <= 11;
        }
        return false;
    }

}
