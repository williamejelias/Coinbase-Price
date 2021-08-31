package com.welias.exchange.coinbase.app.message;

import org.json.JSONArray;
import org.json.JSONObject;

public class CoinbaseMessageFactory
{
    // TODO consider using GSON library to handle automatic encoding and decoding of internal/external API objects
    public static String getL2SubscribeMessage(String... instruments)
    {
        JSONObject subscribeMessage = new JSONObject();
        subscribeMessage.put("type", "subscribe");

        JSONArray products = buildProductsList(instruments);
        subscribeMessage.put("product_ids", products);
        subscribeMessage.put("channels", buildChannelsList(products));

        return subscribeMessage.toString();
    }

    private static JSONArray buildChannelsList(JSONArray products)
    {
        JSONArray channels = new JSONArray();
        channels.put("level2");
        channels.put("heartbeat");

        JSONObject channel = new JSONObject();
        channel.put("name", "ticker");
        channel.put("product_ids", products);
        channels.put(channel);
        return channels;
    }

    private static JSONArray buildProductsList(String... instruments)
    {
        JSONArray products = new JSONArray();
        for (String instrument : instruments)
        {
            products.put(instrument);
        }
        return products;
    }
}
