package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserRequestDto;

import java.util.List;

public interface IUserHandler {
    void saveUser(UserRequestDto userRequestDto);


}
