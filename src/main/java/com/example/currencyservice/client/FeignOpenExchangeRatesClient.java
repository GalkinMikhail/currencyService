package com.example.currencyservice.client;

import com.example.currencyservice.model.Rates;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@FeignClient(name = "course", url = "${rates.api.url}")
public interface FeignOpenExchangeRatesClient {

    @GetMapping("/latest.json")
    Rates getLatestRates(@RequestParam("app_id") String appId, @RequestParam("currency") String currency);

    @GetMapping("/historical/{date}.json")
    Rates getHistoricalRates(@PathVariable String date, @RequestParam("app_id") String appId, @RequestParam("currency") String currency);

    @GetMapping("/currencies.json")
    Map<String, String> getCurrencies();

}
