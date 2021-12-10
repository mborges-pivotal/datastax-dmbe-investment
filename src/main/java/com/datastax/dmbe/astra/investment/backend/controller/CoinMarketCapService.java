package com.datastax.dmbe.astra.investment.backend.controller;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * https://coinmarketcap.com/
 */
@Service
@Slf4j
public class CoinMarketCapService implements MarketService {

    @Value("${coinmarketcap.apikey}")
    private String apiKey;
    private String apiKeyHeader = "X-CMC_PRO_API_KEY";  // OR CMC_PRO_API_KEY string parameter

    private final String baseUri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest";
    private final String eodEndpoint = baseUri + "?symbol=%s";

    // return price for a symbol
    public double price(String symbol) {
        String uri = String.format(eodEndpoint, symbol);

        DocumentContext jsonContext = JsonPath.parse(restCall(uri, apiKeyHeader, apiKey));
        log.info("{}", jsonContext.jsonString());
        double price = jsonContext.read(String.format("$.data.%s.quote.USD.price",symbol));

        return price;
    }
    
}
