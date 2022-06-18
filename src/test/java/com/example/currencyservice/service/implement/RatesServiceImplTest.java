package com.example.currencyservice.service.implement;

import com.example.currencyservice.client.FeignOpenExchangeRatesClient;
import com.example.currencyservice.model.Rates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RatesServiceImplTest {

    @Value("${base.currency}")
    private String base;

    private Rates today;
    private Rates yesterday;

    @Autowired
    private RatesServiceImpl ratesService;

    @MockBean
    private FeignOpenExchangeRatesClient feignOpenExchangeRatesClient;


    @BeforeEach
    public void init() {
        int time = 1609459199;
        this.today = new Rates();
        this.today.setTimestamp(time);
        this.today.setBase("cur_base");
        Map<String, Double> currentRatesMap = new HashMap<>();
        currentRatesMap.put("cur1", 0.1);
        currentRatesMap.put("cur2", 0.5);
        currentRatesMap.put("cur3", 1.0);
        currentRatesMap.put(this.base, 57.103);
        currentRatesMap.put("cur_base", 1.0);
        this.today.setRates(currentRatesMap);

        time = 1609372799;
        this.yesterday = new Rates();
        this.yesterday.setTimestamp(time);
        this.yesterday.setBase("cur_base");
        Map<String, Double> prevRatesMap = new HashMap<>();
        prevRatesMap.put("cur1", 0.1);
        prevRatesMap.put("cur2", 1.0);
        prevRatesMap.put("cur3", 0.5);
        prevRatesMap.put(this.base, 57.103);
        prevRatesMap.put("cur_base", 1.0);
        this.yesterday.setRates(prevRatesMap);
    }


    @Test
    public void compareRatesResultPositive() {
        Mockito.when(feignOpenExchangeRatesClient.getLatestRates(anyString(), anyString())).thenReturn(this.today);
        Mockito.when(feignOpenExchangeRatesClient.getHistoricalRates(anyString(), anyString(), anyString())).thenReturn(this.yesterday);
        int result = ratesService.getKey("cur3");
        assertEquals(1, result);
    }

    @Test
    public void compareRatesResultNegative() {
        Mockito.when(feignOpenExchangeRatesClient.getLatestRates(anyString(), anyString())).thenReturn(this.today);
        Mockito.when(feignOpenExchangeRatesClient.getHistoricalRates(anyString(), anyString(), anyString())).thenReturn(this.yesterday);
        int result = ratesService.getKey("cur2");
        assertEquals(-1, result);
    }

    @Test
    public void compareRatesResultZero() {
        Mockito.when(feignOpenExchangeRatesClient.getLatestRates(anyString(), anyString())).thenReturn(this.today);
        Mockito.when(feignOpenExchangeRatesClient.getHistoricalRates(anyString(), anyString(), anyString())).thenReturn(this.yesterday);
        int result = ratesService.getKey("cur1");
        assertEquals(0, result);
    }
}