package com.triffy.sheet.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Configuration
@Slf4j
public class GoogleAuthorizationConfig {

    @Value("${application.name}")
    private String applicationName;
    @Value("${credentials.content}")
    private String credentialsContent;


    @Bean
    public Sheets getGoogleSheetsService() throws IOException, GeneralSecurityException {
        List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);

        // Initializing the service:
        GoogleCredential googleCredentials;
        try(InputStream inputSteam = new ByteArrayInputStream(credentialsContent.getBytes())) {
            googleCredentials = GoogleCredential.fromStream(inputSteam).createScoped(SCOPES);
        }

        return new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                googleCredentials
        ).setApplicationName(applicationName)
                .build();
    }
}
