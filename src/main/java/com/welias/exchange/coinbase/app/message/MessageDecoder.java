package com.welias.exchange.coinbase.app.message;

import org.json.JSONObject;
import org.json.JSONTokener;

public class MessageDecoder
{
    public static JSONObject decodeMessage(String message)
    {
        return (JSONObject) new JSONTokener(message).nextValue();
    }
}
