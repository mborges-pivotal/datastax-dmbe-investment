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

import lombok.extern.slf4j.Slf4j;

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
@Slf4j
@Controller
public class InvestmentController extends BaseController {

  @Autowired
  private InvestmentApiController api;

  @GetMapping(value = { "/", "/home" })
  public String homePage(Model model) {
    model.addAttribute("pageName", "home");
    return "home";
  }

  ////////////////////////
  // Accounts
  ////////////////////////

  @GetMapping(value = { "/accounts" })
  public String accountsPage(Model model) {
    model.addAttribute("pageName", "accounts");
    model.addAttribute("trade", new Trade());

    String userName = getUserName();
    List<Account> accounts = api.listAccounts(userName);

    if (accounts.size() <= 0) {
      model.addAttribute("pageName", "home");
      return "home";
    }

    Account a = accounts.get(0);

    model.addAttribute("userName", a.getKey().getUserName());
    model.addAttribute("account", a.getKey().getAccountNumber());

    model.addAttribute("name", a.getName());
    model.addAttribute("accounts", accounts);

    // Retrieve position for the first account
    model.addAttribute("positions", api.listPositionsByAccount(a.getKey().getAccountNumber()));
    model.addAttribute("trades", api.listTradesByAccount(a.getKey().getAccountNumber(), null, null));

    return "accounts";
  }

  ////////////////////////
  // UI Fragments
  ////////////////////////

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

  ////////////////////////
  // Trade
  ////////////////////////

  @GetMapping(value = { "/trade" })
  public String tradePage(Model model) {
    model.addAttribute("pageName", "trade");
    model.addAttribute("trade", new Trade());

    return "trade";
  }

  @PostMapping("/trade")
  public String insertTrade(HttpServletRequest request, @ModelAttribute Trade trade, Model model) {
    model.addAttribute("pageName", "accounts");
    model.addAttribute("trade", trade);
    trade.setTradeId(Uuids.timeBased());
    api.createTrade(request, getUserName(), trade.getAccount(), trade);

    log.debug("inserted {}", trade);

    return "accounts";
  }

}
