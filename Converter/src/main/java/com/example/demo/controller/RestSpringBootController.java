package com.example.demo.controller;

import com.example.demo.apiStructure.ExchangeRateResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
public class RestSpringBootController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping(value = "/converter")
    public ExchangeRateResponse getCurrency() {
        String url = "https://api.nbp.pl/api/exchangerates/rates/a/usd";
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(url, ExchangeRateResponse.class);
    }
}
