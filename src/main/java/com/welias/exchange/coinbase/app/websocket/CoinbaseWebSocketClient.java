package com.welias.exchange.coinbase.app.websocket;

import com.welias.exchange.coinbase.app.UITools;
import com.welias.exchange.coinbase.app.api.external.UpdateTypeEnum;
import com.welias.exchange.coinbase.app.api.internal.PayloadAdapter;
import com.welias.exchange.coinbase.app.message.CoinbaseMessageFactory;
import com.welias.exchange.coinbase.app.model.PriceBook;
import com.welias.exchange.coinbase.app.model.PriceBookUpdate;
import org.json.JSONObject;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class CoinbaseWebSocketClient
{
    public static final String COINBASE_WS_URI = "wss://ws-feed.pro.coinbase.com/";

    WebSocketEndpoint mWebSocketEndpoint;
    PriceBook mPriceBook;

    public void subscribe(String instrument)
    {
        try
        {
            mWebSocketEndpoint = new WebSocketEndpoint(new URI(CoinbaseWebSocketClient.COINBASE_WS_URI), this::handleResponse);

            mWebSocketEndpoint.connect();

            System.out.printf("Subscribing to instrument %s %n", instrument);
            mWebSocketEndpoint.sendMessage(CoinbaseMessageFactory.getL2SubscribeMessage(instrument));
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

    private void handleResponse(JSONObject response)
    {
        String typeValue = (String) response.get("type");
        if (typeValue != null)
        {
            UpdateTypeEnum updateType = UpdateTypeEnum.fromString(typeValue);
            switch (updateType)
            {
                case SNAPSHOT:
                    handleSnapshot(response);
                    break;
                case L2_UPDATE:
                    handleL2Update(response);
                    break;
                case ERROR:
                    handleError(response);
            }
        }
    }

    private void handleSnapshot(JSONObject response)
    {
        mPriceBook = PayloadAdapter.fromSnapshot(response);
        UITools.render(mPriceBook);
    }

    private void handleL2Update(JSONObject response)
    {
        PriceBookUpdate orderBookUpdate = PayloadAdapter.fromL2Update(response);
        if (mPriceBook != null) {
            mPriceBook.applyUpdate(orderBookUpdate);
            UITools.render(mPriceBook);
        }
    }

    private void handleError(JSONObject response)
    {
        System.out.println(response.get("message") + ". " + response.get("reason") + ".");
        System.exit(0);
    }

    public void close()
    {
        mWebSocketEndpoint.close();
    }
}

