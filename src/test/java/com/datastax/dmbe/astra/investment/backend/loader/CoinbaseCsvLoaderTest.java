package com.datastax.dmbe.astra.investment.backend.loader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.datastax.dmbe.astra.investment.backend.model.Trade;
import com.datastax.dmbe.astra.investment.backend.util.DateTimeUtils;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.core.io.ClassPathResource;

import lombok.extern.slf4j.Slf4j;

import static org.assertj.core.api.Assertions.assertThat;

// https://reflectoring.io/unit-testing-spring-boot/
@TestInstance(Lifecycle.PER_CLASS)
@Slf4j
public class CoinbaseCsvLoaderTest {

    private CoinbaseCsvLoader loader;

    @BeforeAll
    void initUseCase() {
        loader = new CoinbaseCsvLoader();
    }

    @Test
    void parseTimestamp() throws Exception {
        assertThat(DateTimeUtils.toZonedDateTime(CoinbaseCsvLoader.f1,"2014-09-02T08:05:23.653Z").toString()).isEqualTo("2014-09-02T03:05:23.653-05:00[America/Chicago]");
        assertThat(DateTimeUtils.toZonedDateTime(CoinbaseCsvLoader.f2,"2021-05-13T00:47:25Z").toString()).isEqualTo("2021-05-13T00:47:25Z");

        ZonedDateTime date = DateTimeUtils.toZonedDateTime(CoinbaseCsvLoader.f2,"2021-05-13T00:47:25Z");
        log.info("Date received is {}", date);
        UUID dateUUID = DateTimeUtils.dateToTimeBasedUuid(date);
        log.info("Date UUID is {}", dateUUID);
        ZonedDateTime dateFromUuid = DateTimeUtils.timeBasedUuidToDate(dateUUID);
        log.info("Date translated is {}", dateFromUuid);

        // assert they are the same

    }

    @Test
    void readFile() throws Exception {
        InputStream resource = new ClassPathResource("local/coinbase-1.csv").getInputStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource))) {
            List<Trade> trades = loader.mapFile("001", reader);
            log.trace(Arrays.toString(trades.toArray()));
            assertThat(trades.size()).isEqualTo(37);
        }
    }
}
