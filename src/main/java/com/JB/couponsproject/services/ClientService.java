package com.JB.couponsproject.services;

import com.JB.couponsproject.enums.UserType;
import com.JB.couponsproject.exceptions.ApplicationException;

public interface ClientService {
    boolean login(String email,String password) throws ApplicationException;
    UserType getUserType();
    String getEmail();
    long getId();
}
