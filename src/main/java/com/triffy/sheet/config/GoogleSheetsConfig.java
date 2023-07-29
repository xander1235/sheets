package com.triffy.sheet.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.triffy.sheet.exceptions.CustomGenericException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

//@Configuration
@Slf4j
public class GoogleSheetsConfig {

    @Value("${application.name}")
    private String applicationName;

    private static Sheets sheets;

    private GoogleSheetsConfig() throws IOException, GeneralSecurityException {
        List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);

        log.info("environment variables: {}", System.getenv());
        String credentialsContent = System.getenv("SERVICE_SECRET");
        applicationName = applicationName != null && !applicationName.isEmpty() ? applicationName : System.getenv("APPLICATION_NAME");
        if (credentialsContent == null || credentialsContent.isEmpty()) {
            log.error("google sheets credentials empty");
            throw new CustomGenericException("No credentials found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // Initializing the service:
        GoogleCredential googleCredentials;
        try(InputStream inputSteam = new ByteArrayInputStream(credentialsContent.getBytes())) {
            googleCredentials = GoogleCredential.fromStream(inputSteam).createScoped(SCOPES);
        }

        sheets = new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                googleCredentials
        ).setApplicationName(applicationName)
                .build();
    }

    public static Sheets getGoogleSheetsService() throws IOException, GeneralSecurityException {
        if (sheets == null) {
            synchronized (Sheets.class) {
                if (sheets == null) {
                    new GoogleSheetsConfig();
                }
            }
        }
        return sheets;
    }
}
