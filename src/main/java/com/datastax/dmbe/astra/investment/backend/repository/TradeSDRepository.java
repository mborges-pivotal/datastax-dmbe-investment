package com.datastax.dmbe.astra.investment.backend.repository;

import java.util.List;

import com.datastax.dmbe.astra.investment.backend.model.trade.TradeSD;
import com.datastax.dmbe.astra.investment.backend.model.trade.TradeSymbolKey;

import org.springframework.data.repository.CrudRepository;

public interface TradeSDRepository extends CrudRepository<TradeSD, TradeSymbolKey> {

    List<TradeSD> findByKeyAccount(String account);

    List<TradeSD> findByKeyAccountAndKeySymbol(String account, String symbol);

    void deleteAllByKeyAccount(String account);

}
