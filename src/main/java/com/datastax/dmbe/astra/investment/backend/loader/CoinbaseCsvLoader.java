package com.datastax.dmbe.astra.investment.backend.loader;

import java.io.Reader;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.datastax.dmbe.astra.investment.backend.model.Trade;
import com.datastax.dmbe.astra.investment.backend.util.DateTimeUtils;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Loads transaction history CSV report
 */
@Component
public class CoinbaseCsvLoader implements Loader {

	/** Logger for the class. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CoinbaseCsvLoader.class);

    // 2021-05-13T00:47:25Z
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
    static final DateTimeFormatter f1 = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault());
    static final DateTimeFormatter f2 = DateTimeFormatter.ISO_ZONED_DATE_TIME;

    /**
     * Timestamp,Transaction Type,Asset,Quantity Transacted,Spot Price Currency,Spot Price at Transaction,Subtotal,Total (inclusive of fees),Fees,Notes
     * 2021-05-13T00:47:25Z,Buy,BTC,0.00095464,USD,50291.21,48.01,50.00,1.99,Bought 0.00095464 BTC for $50.00 USD
     * type: Buy, Sell, Coinbase Earn, Receive
     */
    @Override
    public List<Trade> mapFile(String account, Reader reader) throws Exception {
        List<String[]> list = readAll(reader);
        List<Trade> trades = new ArrayList<>();
        for (String[] cols : list) {
            LOGGER.info("{}", Arrays.toString(cols));
            Trade t = new Trade();
            t.setAccount(account);
            t.setTradeId(DateTimeUtils.dateToTimeBasedUuid(DateTimeUtils.toZonedDateTime(f2, cols[0])));    
            t.setType(mapType(cols[1]));
            t.setSymbol(cols[2]);
            t.setShares(toBigDecimal(cols[3]));
            t.setPrice(toBigDecimal(cols[5]));
            t.setAmount(t.getShares().multiply(t.getPrice()));
            trades.add(t);
        }
        return trades;
    }

    /////////////////////////
    // Helper methods
    ////////////////////////

    private  static List<String[]> readAll(Reader reader) throws Exception {

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(true)
                .build();

        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(8)
                .withCSVParser(parser)
                .build();

        List<String[]> list = csvReader.readAll();
        reader.close();
        csvReader.close();
        return list;
    }

    private static String mapType(String value) {
        if (value.equals("Receive")) {
            return "buy";
        } else if (value.equals("Conbase Earn")) {
            return "buy";
        } else {
            return value.toLowerCase();
        }
    }

    // Possible Utilities to be pushed to the loader interface

    // 0.00095464
    private static BigDecimal toBigDecimal(String value) {
        return new BigDecimal(value);
    }

}
