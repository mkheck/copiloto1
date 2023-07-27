package com.theheckler.copiloto1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(WeatherController.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class WeatherControllerTest {
    @Autowired
    private MockMvc mockMvc;

//    @MockBean
//    private WeatherService service;
//
    @MockBean
    private METARRepository repo;

    private METAR stlMETAR;
    private METAR ordMETAR;

    @BeforeEach
    void setUp() {
        stlMETAR = new METAR("123",
                "KSTL 261551Z 21015G24KT 10SM SCT130 BKN180 BKN250 33/22 A2989 RMK AO2 SLP107 T03280217",
                "VFR");
        ordMETAR = new METAR("456",
                "KORD 261551Z 21015G24KT 10SM SCT130 BKN180 BKN250 33/22 A2989 RMK AO2 SLP107 T03280217",
                "VFR");

        when(repo.findByRawContains("KSTL")).thenReturn(Optional.of(stlMETAR));
        when(repo.findByRawContains("KORD")).thenReturn(Optional.of(ordMETAR));
    }

    @Test
    void retrieveMetar() {
        try {
            mockMvc.perform(get("/metar/KSTL"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(new ObjectMapper().writeValueAsString(stlMETAR)))
                    .andDo(print())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}