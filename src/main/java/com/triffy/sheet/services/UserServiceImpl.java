package com.triffy.sheet.services;


import com.google.api.services.sheets.v4.model.*;
import com.triffy.sheet.config.GoogleSheetsConfig;
import com.triffy.sheet.exceptions.BadRequestException;
import com.triffy.sheet.model.User;
import com.triffy.sheet.services.base.UserService;
import com.triffy.sheet.validators.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import com.google.api.services.sheets.v4.Sheets;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Value("${spreadsheet.id}")
    private String spreadsheetId;

    @Override
    public void addUser(User user) throws GeneralSecurityException, IOException {
        if (!Validator.isValidMobileNumber(user.getMobile())) {
            throw new BadRequestException("Invalid mobile number");
        }
        List<Object> list = Arrays.asList(user.getName(), user.getMobile());
        log.info("user: {}", user);
        appendToLastRow("Sheet1", list);
    }

    private void appendToLastRow(String sheetName, List<Object> values) throws IOException, GeneralSecurityException {
        String range = sheetName + "!A:A"; // Assuming you want to check the last row of the first column

        Sheets sheetsService = GoogleSheetsConfig.getGoogleSheetsService();
        ValueRange response = sheetsService.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();

        List<List<Object>> allValues = response.getValues();
        int lastRow = allValues != null ? allValues.size() + 1 : 1;

        List<List<Object>> newValues = Collections.singletonList(values);
        ValueRange body = new ValueRange().setValues(newValues);

        AppendValuesResponse result = sheetsService.spreadsheets().values()
                .append(spreadsheetId, sheetName, body)
                .setValueInputOption("RAW")
                .setInsertDataOption("INSERT_ROWS")
                .setIncludeValuesInResponse(true)
                .execute();

        System.out.printf("Data appended to row %d.", lastRow);
    }


}
