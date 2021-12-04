-- Investment Portfolio Data Modeling
-- https://www.datastax.com/learn/data-modeling-by-example/investment-data-model

CREATE TABLE IF NOT EXISTS accounts_by_user (
  username text,
  account_number text,
  cash_balance decimal,
  name text STATIC,
  PRIMARY KEY ((username), account_number)
);

CREATE TABLE IF NOT EXISTS positions_by_account (
  account text,
  symbol text,
  quantity decimal,
  PRIMARY KEY ((account), symbol)
);

-- Table trades_by_a_d supports two data access patterns Q3.1 and Q3.2, which are used to 
-- retrieve either all trades for a given account or only trades in a particular date range 
-- for a given account. 

/* 
Q3.1
SELECT account, 
       TODATE(DATEOF(trade_id)) AS date, 
       trade_id, type, symbol,
       shares, price, amount 
FROM trades_by_a_d
WHERE account = 'joe001';

Q3.2
SELECT account, 
       TODATE(DATEOF(trade_id)) AS date, 
       trade_id, type, symbol,
       shares, price, amount 
FROM trades_by_a_d
WHERE account = 'joe001'
  AND trade_id > maxTimeuuid('2020-09-07')
  AND trade_id < minTimeuuid('2020-09-12');
*/
CREATE TABLE trades_by_a_d (
  account TEXT,
  trade_id TIMEUUID,
  type TEXT,
  symbol TEXT,
  shares DECIMAL,
  price DECIMAL,
  amount DECIMAL,
  PRIMARY KEY ((account),trade_id)
) WITH CLUSTERING ORDER BY (trade_id DESC);

/* Q3.3
SELECT account, 
       TODATE(DATEOF(trade_id)) AS date, 
       trade_id, type, symbol,
       shares, price, amount 
FROM trades_by_a_td
WHERE account = 'joe001'
  AND type = 'buy'
  AND trade_id > maxTimeuuid('2020-09-07')
  AND trade_id < minTimeuuid('2020-09-12');
*/
CREATE TABLE trades_by_a_td (
  account TEXT,
  trade_id TIMEUUID,
  type TEXT,
  symbol TEXT,
  shares DECIMAL,
  price DECIMAL,
  amount DECIMAL,
  PRIMARY KEY ((account),type,trade_id)
) WITH CLUSTERING ORDER BY (type ASC, trade_id DESC);

/* Q3.4
SELECT account, 
       TODATE(DATEOF(trade_id)) AS date, 
       trade_id, type, symbol,
       shares, price, amount 
FROM trades_by_a_std
WHERE account = 'joe001'
  AND symbol = 'AAPL'
  AND type = 'buy'
  AND trade_id > maxTimeuuid('2020-09-07')
  AND trade_id < minTimeuuid('2020-09-12');

Q3.5
SELECT account, 
       TODATE(DATEOF(trade_id)) AS date, 
       trade_id, type, symbol,
       shares, price, amount 
FROM trades_by_a_sd
WHERE account = 'joe001'
  AND symbol = 'AAPL'
  AND trade_id > maxTimeuuid('2020-09-07')
  AND trade_id < minTimeuuid('2020-09-12');
*/
CREATE TABLE trades_by_a_sd (
  account TEXT,
  trade_id TIMEUUID,
  type TEXT,
  symbol TEXT,
  shares DECIMAL,
  price DECIMAL,
  amount DECIMAL,
  PRIMARY KEY ((account),symbol,trade_id)
) WITH CLUSTERING ORDER BY (symbol ASC, trade_id DESC);