package com.welias.exchange.coinbase.app;

import com.welias.exchange.coinbase.app.websocket.CoinbaseWebSocketClient;

import java.util.concurrent.CountDownLatch;

public class CoinbasePriceBookFeed
{
    public static void main(String[] args)
    {
        CountDownLatch doneSignal = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread(doneSignal::countDown));

        CoinbaseWebSocketClient webSocketClient = new CoinbaseWebSocketClient();
        webSocketClient.subscribe(args[0]);

        try
        {
            doneSignal.await();
            System.out.println("Exiting...");
            webSocketClient.close();
        }
        catch (InterruptedException ignored)
        {
        }
    }
}
