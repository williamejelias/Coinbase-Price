package com.welias.exchange.coinbase.app.websocket;

import com.welias.exchange.coinbase.app.message.MessageDecoder;
import com.welias.exchange.coinbase.app.message.MessageFactory;
import org.json.JSONObject;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class CoinbaseWebSocketClient
{
    public static final String COINBASE_WS_URI = "wss://ws-feed.pro.coinbase.com/";

    WebSocketEndpoint mWebSocketEndpoint;

    public void subscribe(String instrument)
    {
        try
        {
            mWebSocketEndpoint = new WebSocketEndpoint(new URI(CoinbaseWebSocketClient.COINBASE_WS_URI), message ->
            {
                JSONObject response = MessageDecoder.decodeMessage(message);
                System.out.println("received: " + response.get("type") + " --- " + response);
            });

            mWebSocketEndpoint.connect();

            System.out.printf("Subscribing to instrument %s %n", instrument);
            mWebSocketEndpoint.sendMessage(MessageFactory.getL2SubscribeMessage(instrument));
        }
        catch (DeploymentException | IOException e)
        {
            System.out.printf("Invalid configuration for endpoint %s. exiting...%n", COINBASE_WS_URI);
        }
        catch (URISyntaxException e)
        {
            System.out.printf("Invalid endpoint %s. exiting...%n", COINBASE_WS_URI);
        }
    }

    public void close()
    {
        mWebSocketEndpoint.close();
    }
}

