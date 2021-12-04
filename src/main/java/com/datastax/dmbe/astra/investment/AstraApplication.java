package com.datastax.dmbe.astra.investment;

import com.datastax.dmbe.astra.investment.backend.model.Account;
import com.datastax.dmbe.astra.investment.backend.model.AccountKey;
import com.datastax.dmbe.astra.investment.backend.model.Position;
import com.datastax.dmbe.astra.investment.backend.model.PositionKey;
import com.datastax.dmbe.astra.investment.backend.model.Trade;
import com.datastax.dmbe.astra.investment.backend.repository.AccountRepository;
import com.datastax.dmbe.astra.investment.backend.repository.PositionRepository;
import com.datastax.dmbe.astra.investment.backend.repository.TradeDRepository;
import com.datastax.dmbe.astra.investment.backend.repository.TradeSDRepository;
import com.datastax.dmbe.astra.investment.backend.repository.TradeTDRepository;
import com.datastax.oss.driver.api.core.uuid.Uuids;

import static com.datastax.dmbe.astra.investment.backend.model.trade.TradeUtilities.mapAsTradeD;
import static com.datastax.dmbe.astra.investment.backend.model.trade.TradeUtilities.mapAsTradeSD;
import static com.datastax.dmbe.astra.investment.backend.model.trade.TradeUtilities.mapAsTradeTD;

import java.math.BigDecimal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@SpringBootApplication
@EnableConfigurationProperties
@EnableCassandraRepositories(basePackages = { "com.datastax.dmbe.astra.investment.repository" })
public class AstraApplication implements CommandLineRunner {

	/** Logger for the class. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AstraApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AstraApplication.class, args);
	}

	@Autowired
	private AccountRepository accountRepo;

	@Autowired
	private PositionRepository positionRepo;

	@Autowired
	private TradeDRepository tradeDRepo;

	@Autowired
	private TradeSDRepository tradeSDRepo;

	@Autowired
	private TradeTDRepository tradeTDRepo;

	@Override
	public void run(String... arg0) throws Exception {

		AccountKey ak1 = new AccountKey("mborges", "002");
		Account a1 = new Account(ak1, BigDecimal.ZERO, "Marcelo Borges");

		Optional<Account> oAr1 = accountRepo.findById(ak1);
		if (oAr1.isPresent()) {
			LOGGER.info("Account key {} found", oAr1.get());
		} else {
			accountRepo.save(a1);
			LOGGER.info("Saved account {}", a1);
		}

		PositionKey pk1 = new PositionKey("001", "SNAP");
		Position p1 = new Position(pk1, BigDecimal.valueOf(50));

		Position pr1 = positionRepo.findById(pk1).get();
		if (pr1 != null) {
			LOGGER.info("Position key {} found", pr1);
		} else {
			pr1 = positionRepo.save(p1);
			LOGGER.info("Saved position {}", pr1);
		}

		// Trade ingestion needs to be synchronized across 3 tables

		Trade t1 = new Trade();
		t1.setPrice(BigDecimal.valueOf(144L));
		t1.setShares(BigDecimal.TEN);
		t1.setAmount(t1.getShares().multiply(t1.getPrice()));
		t1.setSymbol("SNAP");
		t1.setType("buy");
		t1.setAccount("001");
		t1.setTradeId(Uuids.timeBased());

		// MMB - Review the best way to ingest data here.
		tradeDRepo.save(mapAsTradeD(t1));
		tradeSDRepo.save(mapAsTradeSD(t1));
		tradeTDRepo.save(mapAsTradeTD(t1));

	}

}
