package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IUserHandler;
import com.pragma.powerup.usermicroservice.configuration.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserHandler userHandler;

    @BeforeEach
    void setUp() {

    }
    /*
        Test para la creacion de un usuario owner con validacion por notaciones correctas
     */
    @Test
    void saveUser() throws Exception{

        LocalDate date= LocalDate.parse("1998-12-27");

        UserRequestDto userRequestDto = new UserRequestDto(
              "Juan",
              "Rodriguez",
              "2056893",
              "+573045896897",
                date,
                "caminicor@gmail.com",
                "1234",
                2L
        );

        MvcResult mvcResult = mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userRequestDto)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> responseMap = objectMapper.readValue(responseJson, new TypeReference<Map<String, String>>(){});
        assertEquals(Constants.USER_CREATED_MESSAGE, responseMap.get(Constants.RESPONSE_MESSAGE_KEY));
    }

    /*
        Test para el criterio de aceptacion: Al crear una cuenta se deben  solicitar los siguientes campos obligatorios

        Implementacion de validacion mediante notacion:
        @NotBlank para variables String

        Nota: Todas aquellas variables que tengan esta notacion son campos obligatorios
     */
    @Test
    void requiredFieldNotBlank() throws Exception{

        LocalDate date= LocalDate.parse("1998-12-27");

        UserRequestDto userRequestDto = new UserRequestDto(
                "Nicolas",
                "", //Se inserta campo vacio o con espacio en blanco
                "1000968983",
                "+573569821152",
                date,
                "niko@gmail.com",
                "1234",
                2L
        );

        MvcResult mvcResult = mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userRequestDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<String> responseList = objectMapper.readValue(responseJson, new TypeReference<List<String>>() {});
        assertEquals("lastName: You must enter the lastname", responseList.get(0));

    }
    /*
        Test para el criterio de aceptacion: Al crear una cuenta se deben  solicitar los siguientes campos obligatorios

        Implementacion de validacion mediante notacion:
        @NotNull para variables diferentes a String
     */
    @Test
    void requiredFieldNotNull() throws Exception{

        LocalDate date= LocalDate.parse("1998-12-27");

        UserRequestDto userRequestDto = new UserRequestDto(
                "Nicolas",
                "Rodriguez",
                "1000968983",
                "+573569821152",
                null, //Se inserta campo nullo
                "niko@gmail.com",
                "1234",
                2L
        );

        MvcResult mvcResult = mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userRequestDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<String> responseList = objectMapper.readValue(responseJson, new TypeReference<List<String>>() {});
        assertEquals("birthDay: You must enter the date of birth", responseList.get(0));

    }
    /*
        Test para criterio de validacion: El documento de identidad debe ser unicamente numerico

        Implementacion de validacion mediante notacion:
        @Pattern
     */
    @Test
    void validateDniNumberNumeric() throws Exception{

        LocalDate date= LocalDate.parse("1998-12-27");

        UserRequestDto userRequestDto = new UserRequestDto(
                "Nicolas",
                "Rodriguez",
                "1000968983abc", //Se insertan letras
                "+573569821152",
                date,
                "niko@gmail.com",
                "1234",
                2L
        );

        MvcResult mvcResult = mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userRequestDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<String> responseList = objectMapper.readValue(responseJson, new TypeReference<List<String>>() {});
        assertEquals("dniNumber: The dniNumber must contain only numbers and cannot start at 0", responseList.get(0));

    }
    /*
        Test para criterio de validacion: Se debe verificar estructura de email valida

        Implementacion de validacion mediante notacion:
        @Email
     */
    @Test
    void validateMail() throws Exception{

        LocalDate date= LocalDate.parse("1998-12-27");

        UserRequestDto userRequestDto = new UserRequestDto(
                "Nicolas",
                "Rodriguez",
                "1000968983",
                "+573569821152",
                date,
                "nicolasrodriguez", //Se inserta email invalido
                "1234",
                2L
        );

        MvcResult mvcResult = mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userRequestDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<String> responseList = objectMapper.readValue(responseJson, new TypeReference<List<String>>() {});
        assertEquals("mail: The mail format is not valid", responseList.get(0));

    }
    /*
        Test para criterio de validacion: El telefono debe contener un maximo de 13 caracteres y
        puede contener simbolo "+"

        Implementacion de validacion mediante notacion:
        @Pattern

        Nota: como el criterio menciona la palabra "debe" entonces el telefono debe contener obligatoriamente
        unicamente 13 caracteres incluido el simbolo "+"
     */
    @Test
    void validatePhone() throws Exception{

        LocalDate date= LocalDate.parse("1998-12-27");

        UserRequestDto userRequestDto = new UserRequestDto(
                "Nicolas",
                "Rodriguez",
                "1000968983",
                "+5735698211525", //Se introduce un numero de mas
                date,
                "nicolasrodriguez@gmail.com",
                "1234",
                2L
        );

        MvcResult mvcResult = mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userRequestDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<String> responseList = objectMapper.readValue(responseJson, new TypeReference<List<String>>() {});
        assertEquals("phone: The format of the mobile number is incorrect", responseList.get(0));

    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}