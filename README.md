# Coinbase-Price
This application retrieves live pricing via websocket for products listed on the Coinbase exchange.

The application utilises the [CoinbasePro Websocket API](https://docs.pro.coinbase.com/#websocket-feed)

To run the application:
```
java -jar Coinbase-Price.jar <instrument>
```
where <instrument> is the instrument listed on the Coinbase exchange that you wish to receive live pricing for.

The application will output the ordered list of the top 10 bids and asks for that instrument until terminated with Ctrl-C.