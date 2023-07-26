package com.triffy.sheet.services.base;

import com.triffy.sheet.model.User;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface UserService {

    void addUser(User user) throws GeneralSecurityException, IOException;
}
