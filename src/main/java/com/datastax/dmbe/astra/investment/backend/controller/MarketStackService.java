package com.datastax.dmbe.astra.investment.backend.controller;


import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * https://marketstack.com/
 */
@Service
public class MarketStackService implements MarketService {

    @Value("${marketstack.apikey}")
    private String apiKey;

    private final String baseUri = "http://api.marketstack.com/v1/";
    private final String eodEndpoint = baseUri + "/eod?access_key=%s&symbols=%s";

    // return price for a symbol
    public double price(String symbol) {
        String uri = String.format(eodEndpoint, apiKey, symbol);

        DocumentContext jsonContext = JsonPath.parse(restCall(uri, null, null));
        double price = jsonContext.read("$.data[0].close");

        return price;
    }

}
