package com.welias.exchange.coinbase.app;

import com.welias.exchange.coinbase.app.ui.TerminalPriceBookRenderer;
import com.welias.exchange.coinbase.app.websocket.CoinbaseWebSocketClient;

import java.util.concurrent.CountDownLatch;

public class PriceBookFeed
{
    public static void main(String[] args)
    {
        CountDownLatch doneSignal = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread(doneSignal::countDown));

        CoinbaseWebSocketClient webSocketClient = new CoinbaseWebSocketClient();
        TerminalPriceBookRenderer terminalPriceBookRenderer = new TerminalPriceBookRenderer();
        webSocketClient.subscribe(args[0]);
        webSocketClient.registerListener(terminalPriceBookRenderer);

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
