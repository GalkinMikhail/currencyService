package com.example.currencyservice.controller;

import com.example.currencyservice.service.implement.GifServiceImpl;
import com.example.currencyservice.service.implement.RatesServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ExchangeController.class)
class ExchangeControllerTest {

    @Value("${gif.rich}")
    private String richTag;
    @Value("${gif.broke}")
    private String brokeTag;
    @Value("${gif.zero}")
    private String zero;
    @Value("${gif.error}")
    private String errorTag;


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatesServiceImpl ratesService;
    @MockBean
    private GifServiceImpl gifService;


    @Test
    void getCurs() throws Exception {
        List<String> responseList = new ArrayList<>();
        responseList.add("TEST");
        Mockito.when(ratesService.getCurrencies())
                .thenReturn(responseList);
        mockMvc.perform(get("/api/currencies")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("TEST"));
    }

    @Test
    public void getGifRich() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("result", this.richTag);
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(map, HttpStatus.OK);
        Mockito.when(ratesService.getKey(anyString()))
                .thenReturn(1);
        Mockito.when(gifService.getGif(this.richTag))
                .thenReturn(responseEntity);
        mockMvc.perform(get("/api/gif/TESTCODE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.result").value(this.richTag));
    }

    @Test
    public void getGifBroke() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("result", this.brokeTag);
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(map, HttpStatus.OK);
        Mockito.when(ratesService.getKey(anyString()))
                .thenReturn(-1);
        Mockito.when(gifService.getGif(this.brokeTag))
                .thenReturn(responseEntity);
        mockMvc.perform(get("/api/gif/TESTCODE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.result").value(this.brokeTag));
    }

    @Test
    public void getGifZero() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("result", this.zero);
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(map, HttpStatus.OK);
        Mockito.when(ratesService.getKey(anyString()))
                .thenReturn(0);
        Mockito.when(gifService.getGif(this.zero))
                .thenReturn(responseEntity);
        mockMvc.perform(get("/api/gif/TESTCODE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.result").value(this.zero));
    }

    @Test
    public void getGifError() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("result", this.errorTag);
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(map, HttpStatus.OK);
        Mockito.when(ratesService.getKey(anyString()))
                .thenReturn(404);
        Mockito.when(gifService.getGif(this.errorTag))
                .thenReturn(responseEntity);
        mockMvc.perform(get("/api/gif/TESTCODE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.result").value(this.errorTag));
    }
}