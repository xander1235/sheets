package com.triffy.sheet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @NotBlank(message = "name can not be blank")
    private String name;

    @NotBlank(message = "mobile can not be blank")
    private String mobile;
}
