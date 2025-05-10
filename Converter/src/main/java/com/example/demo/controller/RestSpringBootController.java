package com.example.demo.controller;

import com.example.demo.apiStructure.ExchangeRateResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/convert")
    public double convertCurrency(
            @RequestParam double amount,
            @RequestParam String from,
            @RequestParam String to
    ) {
        String url = "https://api.nbp.pl/api/exchangerates/tables/A/?format=json";
        RestTemplate restTemplate = new RestTemplate();
        ExchangeRateResponse[] response = restTemplate.getForObject(url, ExchangeRateResponse[].class);

        if (response == null || response.length == 0) {
            throw new RuntimeException("Błąd przy pobieraniu danych z NBP");
        }

        List<ExchangeRateResponse.Rate> rates = response[0].getRates();

        // PLN ma kurs 1
        double fromRate = from.equalsIgnoreCase("PLN") ? 1.0 :
                rates.stream()
                        .filter(rate -> rate.getCode().equalsIgnoreCase(from))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Nie znaleziono waluty źródłowej"))
                        .getMid();

        double toRate = to.equalsIgnoreCase("PLN") ? 1.0 :
                rates.stream()
                        .filter(rate -> rate.getCode().equalsIgnoreCase(to))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Nie znaleziono waluty docelowej"))
                        .getMid();

        return amount * fromRate / toRate;
    }

}
