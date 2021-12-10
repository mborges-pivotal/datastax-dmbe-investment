package com.datastax.dmbe.astra.investment.backend.controller;

import static com.datastax.dmbe.astra.investment.backend.model.trade.TradeUtilities.mapAsTradeD;
import static com.datastax.dmbe.astra.investment.backend.model.trade.TradeUtilities.mapAsTradeSD;
import static com.datastax.dmbe.astra.investment.backend.model.trade.TradeUtilities.mapAsTradeTD;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.datastax.dmbe.astra.investment.backend.model.Account;
import com.datastax.dmbe.astra.investment.backend.model.AccountKey;
import com.datastax.dmbe.astra.investment.backend.model.Position;
import com.datastax.dmbe.astra.investment.backend.model.PositionKey;
import com.datastax.dmbe.astra.investment.backend.model.Trade;
import com.datastax.dmbe.astra.investment.backend.model.trade.TradeD;
import com.datastax.dmbe.astra.investment.backend.model.trade.TradeSD;
import com.datastax.dmbe.astra.investment.backend.model.trade.TradeTD;
import com.datastax.dmbe.astra.investment.backend.repository.AccountRepository;
import com.datastax.dmbe.astra.investment.backend.repository.PositionRepository;
import com.datastax.dmbe.astra.investment.backend.repository.TradeDRepository;
import com.datastax.dmbe.astra.investment.backend.repository.TradeSDRepository;
import com.datastax.dmbe.astra.investment.backend.repository.TradeTDRepository;
import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class InvestmentApiController {

    private final AccountRepository accountRepo;
    private final PositionRepository positionRepo;
    private final TradeDRepository tradeDRepo;
    private final TradeSDRepository tradeSDRepo;
    private final TradeTDRepository tradeTDRepo;

    private final MarketStackService marketService;
    private final CoinMarketCapService coinService;

    @RequestMapping(value = "/accounts/{username}", method = RequestMethod.GET)
    public List<Account> listAccounts(@PathVariable("username") String userName) {
        return accountRepo.findByKeyUserName(userName);
    }

    @RequestMapping(value = "/positions/{account}", method = RequestMethod.GET)
    public List<Position> listPositionsByAccount(@PathVariable String account) {
        return positionRepo.findByKeyAccount(account);
    }

    @GetMapping("/stock/price/{symbol}")
    public Double stockPrice(@PathVariable String symbol) {
        return marketService.price(symbol);
    }

    @GetMapping("/crypto/price/{symbol}")
    public Double cryptoPrice(@PathVariable String symbol) {
        return coinService.price(symbol);
    }

    ////////////////////////////////
    // Trades
    ////////////////////////////////

    @RequestMapping(value = "/trades/{account}", method = RequestMethod.GET)
    public List<Trade> listTradesByAccount(@PathVariable String account, @RequestParam(required = false) String symbol,
            @RequestParam(required = false) String type) {

        // passed symbol
        if (symbol != null) {
            List<TradeSD> trades = tradeSDRepo.findByKeyAccountAndKeySymbol(account, symbol);
            return mapAsTradeSD(trades);
        }

        // passed type
        if (type != null) {
            List<TradeTD> trades = tradeTDRepo.findByKeyAccountAndKeyType(account, type);
            return mapAsTradeTD(trades);
        }

        // Just account
        List<TradeD> trades = tradeDRepo.findByKeyAccount(account);
        return mapAsTradeD(trades);

    }

    // CREATE A TRADE

    @RequestMapping(value = "/trades/{username}/{account}", method = RequestMethod.PUT)
    public ResponseEntity<Trade> createTrade(HttpServletRequest req, @PathVariable("username") String userName,
            @PathVariable String account, @RequestBody Trade trade) {

        createTrade(userName, trade);

        return ResponseEntity.accepted().body(trade);
    }

    @DeleteMapping("/accounts/{username}")
    public ResponseEntity<Void> deleteAllAccounts(@PathVariable("username") String userName) {

        List<Account> accounts = accountRepo.findByKeyUserName(userName);

        if (accounts.size() <= 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // MMB: Should we run this in a batch
        for (Account account : accounts) {
            deleteAccount(account);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //////////////////////////////////
    // Helper Methods
    //////////////////////////////////

    void deleteAccount(Account account) {
        log.info("Deleting {}", account);

        positionRepo.deleteAllByKeyAccount(account.getKey().getUserName());
        tradeDRepo.deleteAllByKeyAccount(account.getKey().getUserName());
        tradeSDRepo.deleteAllByKeyAccount(account.getKey().getUserName());
        tradeTDRepo.deleteAllByKeyAccount(account.getKey().getUserName());

        accountRepo.deleteById(account.getKey());
    }

    void createTrade(String userName, Trade trade) {

        String tradeType = trade.getType();

        // update account balance
        AccountKey ak = new AccountKey(userName, trade.getAccount());
        Account account = accountRepo.findById(ak).orElseThrow(() -> new Error("Account not found " + ak));
        
        if (tradeType.equals("buy")) {
            account.setCashBalance(account.getCashBalance().add(trade.getAmount()));
        } else {
            account.setCashBalance(account.getCashBalance().subtract(trade.getAmount()));
        }        
        accountRepo.save(account);

        // update position quantity
        PositionKey pk = new PositionKey(trade.getAccount(), trade.getSymbol());
        Position position = positionRepo.findById(pk).orElse(null);
        if (position == null) {
            position = new Position();
            position.setKey(pk);
            position.setQuantity(BigDecimal.ZERO);
        }
        
        position.setQuantity(position.getQuantity().add(trade.getShares()));
        positionRepo.save(position);

        // inserting a trade
        tradeDRepo.save(mapAsTradeD(trade));
        tradeSDRepo.save(mapAsTradeSD(trade));
        tradeTDRepo.save(mapAsTradeTD(trade));

    }

}
