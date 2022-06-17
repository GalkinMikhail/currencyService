package com.example.currencyservice.controller;

import com.example.currencyservice.controller.Urls.Urls;
import com.example.currencyservice.service.implement.GifServiceImpl;
import com.example.currencyservice.service.implement.RatesServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ExchangeController {
    @Autowired
    private RatesServiceImpl ratesService;
    @Autowired
    private GifServiceImpl gifService;

    @Value("${gif.rich}")
    private String rich;
    @Value("${gif.broke}")
    private String broke;
    @Value("${gif.error}")
    private String error;
    @Value("${gif.zero}")
    private String zero;

    @GetMapping(Urls.currencies.full)
    public ResponseEntity<List<String>> getCurs(){
        List<String> currencies = this.ratesService.getCurrencies();

        if (currencies == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(currencies, HttpStatus.OK);
    }

    @GetMapping(Urls.gif.target.full)
    public ResponseEntity<Map> getGif(@PathVariable String currency) {
        int gifKey = -200;
        String gifTag;
        if (currency != null) {
            gifKey = this.ratesService.getKey(currency);
        }
        if (gifKey == 1){
            gifTag = this.rich;
        }
        else if (gifKey == -1){
            gifTag = this.broke;
        }
        else if (gifKey == 404)
            gifTag = this.error;
        else
            gifTag = this.zero;
        return gifService.getGif(gifTag);
    }

}
