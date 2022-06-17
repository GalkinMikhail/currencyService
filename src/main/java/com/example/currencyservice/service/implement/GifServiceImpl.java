package com.example.currencyservice.service.implement;


import com.example.currencyservice.client.GifFeignClient;
import com.example.currencyservice.service.interfaces.GifService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GifServiceImpl implements GifService {
    private final GifFeignClient gifFeignClient;
    @Value("${giphy.api.key}")
    private String apiKey;


    @Override
    public ResponseEntity<Map> getGif(String tag) {
        if (tag != null) {
            ResponseEntity<Map> result = gifFeignClient.getRandomGif(this.apiKey, tag);
            result.getBody().put("result",tag);
            return result;
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
