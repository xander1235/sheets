package com.triffy.sheet.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @NotBlank(message = "name can not be blank")
    private String name;

    @NotBlank(message = "mobile can not be blank")
    private String mobile;
}
