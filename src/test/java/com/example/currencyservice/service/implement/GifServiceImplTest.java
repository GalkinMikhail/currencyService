package com.example.currencyservice.service.implement;


import com.example.currencyservice.client.GifFeignClient;
import com.example.currencyservice.service.implement.GifServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GifServiceImplTest {

    @Value("${gif.rich}")
    private String rich;

    @Autowired
    private GifServiceImpl gifService;
    @MockBean
    private GifFeignClient gifFeignClient;

    @Test
    public void getGif(){
        ResponseEntity<Map> testEntity = new ResponseEntity<>(new HashMap(), HttpStatus.OK);
        Mockito.when(gifFeignClient.getRandomGif(anyString(), anyString()))
                .thenReturn(testEntity);
        ResponseEntity<Map> result = gifService.getGif(rich);
        assertEquals("rich", result.getBody().get("result"));
    }
}
