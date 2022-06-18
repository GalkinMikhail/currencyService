package com.example.currencyservice.service.implement;

import com.example.currencyservice.client.FeignOpenExchangeRatesClient;
import com.example.currencyservice.model.Rates;
import com.example.currencyservice.service.interfaces.RatesService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Service
@Getter
@Setter
public class RatesServiceImpl implements RatesService {
    private Rates current;
    private Rates previous;
    @Value("${rates.api_key}")
    private String app_id;
    @Value("${base.currency}")
    private String base;


    private final FeignOpenExchangeRatesClient feignOpenExchangeRatesClient;


    @Override
    public List<String> getCurrencies() {
        Map<String,String> temp = ResponseEntity.ok(feignOpenExchangeRatesClient.getCurrencies()).getBody();
        if (temp != null) {
            return new ArrayList<>(temp.keySet());
        }
        else
            return new ArrayList<>();
    }

    @Override
    public void getLatestCurrencyRates(String currency) {
        this.current = ResponseEntity.ok(feignOpenExchangeRatesClient.getLatestRates(this.app_id, currency)).getBody();
    }

    @Override
    public void getYesterdayCurrencyRates(String currency) {
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        cal.add(Calendar.DATE, -1);
        String yesterdayDate = dateFormat.format(cal.getTime());


        this.previous = ResponseEntity.ok(feignOpenExchangeRatesClient.getHistoricalRates(yesterdayDate, this.app_id, currency)).getBody();
    }

    @Override
    public Double compare(Rates rates, String currency) {
        Double result = null;
        Double toCheckRate = null;
        Double baseRate = null;
        Double defaultBaseRate = null;
        Map<String, Double> map;
        if (rates != null && rates.getRates() != null) {
            map = rates.getRates();
            toCheckRate = map.get(currency);
            baseRate = map.get(this.base);
            defaultBaseRate = map.get(rates.getBase());
        }
        if (toCheckRate != null && baseRate != null && defaultBaseRate != null) {
            result = new BigDecimal((defaultBaseRate / baseRate) * toCheckRate)
                    .setScale(4, RoundingMode.UP)
                    .doubleValue();
        }
        return result;
    }

    @Override
    public int getKey(String currency) {
        this.getLatestCurrencyRates(currency);
        this.getYesterdayCurrencyRates(currency);
        Double prevCoefficient = this.compare(this.previous, currency);
        Double currentCoefficient = this.compare(this.current, currency);
        return prevCoefficient != null && currentCoefficient != null
                ? Double.compare(currentCoefficient, prevCoefficient)
                : -200;
    }
}
