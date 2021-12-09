package com.datastax.dmbe.astra.investment.frontend.service;

import java.util.Arrays;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * https://marketstack.com/
 */
@Service
public class MarketStackService {

    @Value("${marketstack.apikey}")
    private String apiKey;

    private final String baseUri = "http://api.marketstack.com/v1/";
    private final String eodEndpoint = baseUri + "/eod?access_key=%s&symbols=%s";

    // return price for a symbol
    public double price(String symbol) {
        String uri = String.format(eodEndpoint, apiKey, symbol);

        DocumentContext jsonContext = JsonPath.parse(restCall(uri));
        double price = jsonContext.read("$.data[0].close");

        return price;
    }

    /// Helper methods

    private String restCall(String uri) {
        
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET,entity,String.class);


        return result.getBody();
    }

}
