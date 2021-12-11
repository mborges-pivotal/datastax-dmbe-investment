package com.datastax.dmbe.astra.investment.frontend.controller;

import javax.servlet.http.HttpServletRequest;

import com.datastax.dmbe.astra.investment.backend.controller.InvestmentApiController;
import com.datastax.dmbe.astra.investment.backend.model.Trade;
import com.datastax.dmbe.astra.investment.backend.model.Account;
import com.datastax.oss.driver.api.core.uuid.Uuids;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

/*
 * The intent is to have a completely independent Frontend component
 * 
 * - Remove Cassandra driver dependencies. E.g UUIDs utility
 * - We need our own model. E.g. Trade object
 * 
 * market api references: https://geekflare.com/best-stock-market-api/
 * 
 */
@Controller
public class InvestmentController {

    @Autowired
    private InvestmentApiController api;

    @GetMapping("/home")
    public String tradePage(Model model) {
        model.addAttribute("trade", new Trade());
        
        List<Account> accounts = api.listAccounts("mborges");
        Account a = accounts.get(0);

        model.addAttribute("userName", a.getKey().getUserName());
        model.addAttribute("account", a.getKey().getAccountNumber());

        model.addAttribute("name", a.getName());
        model.addAttribute("accounts", accounts);

        // Retrieve position for the first account
        model.addAttribute("positions", api.listPositionsByAccount(a.getKey().getAccountNumber()));
        model.addAttribute("trades", api.listTradesByAccount(a.getKey().getAccountNumber(), null, null));

        return "home";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
      return "admin";
    }

    // UI Fragments

    @GetMapping("/showPositionsPart/{account}")
    public String showPositionsPart(Model model, @PathVariable String account) {
      model.addAttribute("account", account);
      model.addAttribute("positions", api.listPositionsByAccount(account));
      return "fragments/positions";
    }

    @GetMapping("/showTradesPart/{account}")
    public String showTradesPart(Model model, @PathVariable String account) {
      model.addAttribute("account", account);
      model.addAttribute("positions", api.listPositionsByAccount(account));
      model.addAttribute("trades", api.listTradesByAccount(account, null, null));
      return "fragments/trades";
    }

    // Updates

    @PostMapping("/trade")
    public String insertTrade(HttpServletRequest request, @ModelAttribute Trade trade, Model model) {
      model.addAttribute("trade", trade);
      trade.setTradeId(Uuids.timeBased());
      api.createTrade(request, "mborges", trade.getAccount(), trade);

      return "home";
    }

    
}
