package com.datastax.dmbe.astra.investment.backend.controller;

import static com.datastax.dmbe.astra.investment.backend.model.trade.TradeUtilities.mapAsTradeD;
import static com.datastax.dmbe.astra.investment.backend.model.trade.TradeUtilities.mapAsTradeSD;
import static com.datastax.dmbe.astra.investment.backend.model.trade.TradeUtilities.mapAsTradeTD;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.datastax.dmbe.astra.investment.backend.model.Account;
import com.datastax.dmbe.astra.investment.backend.model.Position;
import com.datastax.dmbe.astra.investment.backend.model.Trade;
import com.datastax.dmbe.astra.investment.backend.model.trade.TradeD;
import com.datastax.dmbe.astra.investment.backend.model.trade.TradeSD;
import com.datastax.dmbe.astra.investment.backend.model.trade.TradeTD;
import com.datastax.dmbe.astra.investment.backend.repository.AccountRepository;
import com.datastax.dmbe.astra.investment.backend.repository.PositionRepository;
import com.datastax.dmbe.astra.investment.backend.repository.TradeDRepository;
import com.datastax.dmbe.astra.investment.backend.repository.TradeSDRepository;
import com.datastax.dmbe.astra.investment.backend.repository.TradeTDRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    // @Autowired
    // public InvestmentApiController(AccountRepository accountRepo, PositionRepository positionRepo,
    //         TradeDRepository tradeDRepo, TradeSDRepository tradeSDRepo, TradeTDRepository tradeTDRepo) {
    //             this.accountRepo = accountRepo;
    //             this.positionRepo = positionRepo;
    //             this.tradeDRepo = tradeDRepo;
    //             this.tradeSDRepo = tradeSDRepo;
    //             this.tradeTDRepo = tradeTDRepo;
    // }

    @RequestMapping(value = "/accounts/{username}", method = RequestMethod.GET)
    public List<Account> listAccounts(@PathVariable("username") String userName) {
        return accountRepo.findByKeyUserName(userName);
    }

    @RequestMapping(value = "/positions/{account}", method = RequestMethod.GET)
    public List<Position> listPositionsByAccount(@PathVariable String account) {
        return positionRepo.findByKeyAccount(account);
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

    @RequestMapping(value = "/trades/{account}", method = RequestMethod.PUT)
    public ResponseEntity<Trade> insertTrade(HttpServletRequest req, @RequestBody Trade trade) {

        // MMB - Review the best way to ingest data here.
        tradeDRepo.save(mapAsTradeD(trade));
        tradeSDRepo.save(mapAsTradeSD(trade));
        tradeTDRepo.save(mapAsTradeTD(trade));

        return ResponseEntity.accepted().body(trade);
    }

    @DeleteMapping("/accounts/{username}")
    public ResponseEntity<Void> deleteAllAccounts(@PathVariable("username") String userName) {

        List<Account> accounts = accountRepo.findByKeyUserName(userName);

        if(accounts.size() <= 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // MMB: Should we run this in a batch
        for(Account account: accounts) {

            log.info("Deleting {}", account);
            
            positionRepo.deleteAllByKeyAccount(account.getKey().getUserName());    
            tradeDRepo.deleteAllByKeyAccount(account.getKey().getUserName());    
            tradeSDRepo.deleteAllByKeyAccount(account.getKey().getUserName());    
            tradeTDRepo.deleteAllByKeyAccount(account.getKey().getUserName());    
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }    

}
