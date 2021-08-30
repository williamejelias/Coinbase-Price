package com.welias.exchange.coinbase.app.websocket;

import org.json.JSONObject;

public interface MessageHandler
{
    void handleMessage(JSONObject message);
}
