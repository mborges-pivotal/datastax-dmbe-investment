package com.datastax.dmbe.astra.investment.backend.model.trade;

import java.util.ArrayList;
import java.util.List;

import com.datastax.dmbe.astra.investment.backend.model.Trade;

/**
 * TradeUtilities - This is mostly to help map a Trade object to the various
 * tables to manage trade access information
 */
public class TradeUtilities {

    // trades_by_a_d - Account, Date (tradeId=timeuuid)
    public static TradeD mapAsTradeD(Trade t) {
        TradeKey key = new TradeKey(t.getAccount(), t.getTradeId());
        TradeD trade = new TradeD();
        trade.setKey(key);
        trade.setAmount(t.getAmount());
        trade.setPrice(t.getPrice());
        trade.setShares(t.getShares());
        trade.setSymbol(t.getSymbol());
        trade.setType(t.getType());
        return trade;
    }

    public static Trade mapAsTrade(TradeD t) {
        Trade trade = new Trade();
        trade.setAccount(t.getKey().getAccount());
        trade.setAmount(t.getAmount());
        trade.setPrice(t.getPrice());
        trade.setShares(t.getShares());
        trade.setSymbol(t.getSymbol());
        trade.setType(t.getType());
        trade.setTradeId(t.getKey().getTradeId());

        return trade;
    }

    public static List<Trade> mapAsTradeD(List<TradeD> trades) {
        List<Trade> resultTrades = new ArrayList<>();
        for(TradeD t: trades) {
            resultTrades.add(mapAsTrade(t));
        }
        return resultTrades;
    }

    // SYMBOL AND DATE

    // trades_by_a_sd - Account, Symbol and Date (tradeId=timeuuid)
    public static TradeSD mapAsTradeSD(Trade t) {
        TradeSymbolKey key = new TradeSymbolKey(t.getAccount(), t.getSymbol(), t.getTradeId());
        TradeSD trade = new TradeSD();
        trade.setKey(key);
        trade.setAmount(t.getAmount());
        trade.setPrice(t.getPrice());
        trade.setShares(t.getShares());
        trade.setType(t.getType());
        return trade;
    }    

    public static Trade mapAsTrade(TradeSD t) {
        Trade trade = new Trade();
        trade.setAccount(t.getKey().getAccount());
        trade.setAmount(t.getAmount());
        trade.setPrice(t.getPrice());
        trade.setShares(t.getShares());
        trade.setSymbol(t.getKey().getSymbol());
        trade.setType(t.getType());
        trade.setTradeId(t.getKey().getTradeId());
        return trade;
    }

    public static List<Trade> mapAsTradeSD(List<TradeSD> trades) {
        List<Trade> resultTrades = new ArrayList<>();
        for(TradeSD t: trades) {
            resultTrades.add(mapAsTrade(t));
        }
        return resultTrades;
    }

    // TYPE AND DATE

    // trades_by_a_td - Account, Type and Date (tradeId=timeuuid)
    public static TradeTD mapAsTradeTD(Trade t) {
        TradeTypeKey key = new TradeTypeKey(t.getAccount(), t.getType(), t.getTradeId());
        TradeTD trade = new TradeTD();
        trade.setKey(key);
        trade.setAmount(t.getAmount());
        trade.setPrice(t.getPrice());
        trade.setShares(t.getShares());
        trade.setSymbol(t.getSymbol());
        return trade;
    }    

    public static Trade mapAsTrade(TradeTD t) {
        Trade trade = new Trade();
        trade.setAccount(t.getKey().getAccount());
        trade.setAmount(t.getAmount());
        trade.setPrice(t.getPrice());
        trade.setShares(t.getShares());
        trade.setType(t.getKey().getType());
        trade.setSymbol(t.getSymbol());
        trade.setTradeId(t.getKey().getTradeId());
        return trade;
    }

    public static List<Trade> mapAsTradeTD(List<TradeTD> trades) {
        List<Trade> resultTrades = new ArrayList<>();
        for(TradeTD t: trades) {
            resultTrades.add(mapAsTrade(t));
        }
        return resultTrades;
    }



}
