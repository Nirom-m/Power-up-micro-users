package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IUserHandler;
import com.pragma.powerup.usermicroservice.domain.exceptions.UnderAgeException;
import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.services.UserServices;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserUseCaseTest {

    @BeforeEach
    void setUp() {
    }
    /*
        Test para la creacion de un usuario owner con validacion de que sea mayor de edad
     */
    @Test
    void saveUser() {
        IUserPersistencePort userPersistencePort = mock(IUserPersistencePort.class);
        LocalDate date= LocalDate.parse("1998-12-27");
        Role role= new Role(
                1L,
                "ROLE_OWNER",
                "ROLE_OWNER"
        );
        User user = new User(
                1L,
                "Juan",
                "Rodriguez",
                "2056893",
                "+573045896897",
                date,
                "caminicor@gmail.com",
                "1234",
                role
        );
        UserUseCase userUseCase = new UserUseCase(userPersistencePort);

        assertDoesNotThrow(()-> userUseCase.saveUser(user));
        // Verificar que el método saveUser() del mock de IUserPersistencePort se llamó una vez con el usuario correcto
        verify(userPersistencePort, times(1)).saveUser(user);

    }
    /*
        Test para criterio de validacion: El usuario debe ser mayor de edad (Usuario owner)

        Implementacion de validacion mediante excepcion:
        UnderAgeException.class
     */
    @Test
    void validateAge() {
        IUserPersistencePort userPersistencePort = mock(IUserPersistencePort.class);
        LocalDate date= LocalDate.parse("2015-12-27");
        Role role= new Role(
                1L,
                "ROLE_OWNER",
                "ROLE_OWNER"
        );
        User user = new User(
                1L,
                "Juan",
                "Rodriguez",
                "2056893",
                "+573045896897",
                date,
                "caminicor@gmail.com",
                "1234",
                role
        );
        UserUseCase userUseCase = new UserUseCase(userPersistencePort);

        assertThrows(UnderAgeException.class, () -> {
            userUseCase.saveUser(user);
        });

    }

}