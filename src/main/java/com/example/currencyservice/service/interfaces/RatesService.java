package com.example.currencyservice.service.interfaces;

import com.example.currencyservice.model.Rates;

import java.util.List;

public interface RatesService {

    List<String> getCurrencies();

    void getLatestCurrencyRates(String currency);

    void getYesterdayCurrencyRates(String currency);

    Double compare(Rates rates, String currency);

    int getKey(String currency);
}
