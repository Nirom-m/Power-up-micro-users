package com.pragma.powerup.usermicroservice.domain.services;

import com.pragma.powerup.usermicroservice.configuration.Constants;
import com.pragma.powerup.usermicroservice.domain.exceptions.UnderAgeException;
import com.pragma.powerup.usermicroservice.domain.model.User;

import java.time.LocalDate;
import java.time.Period;

public class UserServices {

    public static void VerifyAge (LocalDate birthDay){

        LocalDate actualDate = LocalDate.now();
        int age = Period.between(birthDay, actualDate).getYears();

        if(age<18){
            throw new UnderAgeException();
        }
    }
}
