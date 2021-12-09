package com.datastax.dmbe.astra.investment.backend.repository;

import java.util.List;

import com.datastax.dmbe.astra.investment.backend.model.trade.TradeD;
import com.datastax.dmbe.astra.investment.backend.model.trade.TradeKey;

import org.springframework.data.repository.CrudRepository;

public interface TradeDRepository extends CrudRepository<TradeD, TradeKey> {

    List<TradeD> findByKeyAccount(String account);
    
    void deleteAllByKeyAccount(String account);


}
