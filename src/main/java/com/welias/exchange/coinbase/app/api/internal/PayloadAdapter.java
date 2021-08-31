package com.welias.exchange.coinbase.app.api.internal;

import com.welias.exchange.coinbase.app.api.PayloadConstants;
import com.welias.exchange.coinbase.app.api.external.SideEnum;
import com.welias.exchange.coinbase.app.model.Price;
import com.welias.exchange.coinbase.app.model.PriceBook;
import com.welias.exchange.coinbase.app.model.PriceBookUpdate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayloadAdapter
{
    // TODO consider using GSON library to handle automatic encoding and decoding of internal/external API objects
    public static PriceBook fromSnapshot(JSONObject snapshot)
    {
        if (snapshot == null || snapshot.isEmpty())
        {
            return null;
        }

        JSONArray asks = (JSONArray) snapshot.get(PayloadConstants.ASKS);
        JSONArray bids = (JSONArray) snapshot.get(PayloadConstants.BIDS);
        return new PriceBook(PayloadAdapter.buildPriceMap(asks), PayloadAdapter.buildPriceMap(bids));
    }

    private static Map<BigDecimal, BigDecimal> buildPriceMap(JSONArray priceRows)
    {
        Map<BigDecimal, BigDecimal> priceMap = new HashMap<>();
        for (Object row : priceRows)
        {
            JSONArray price = (JSONArray) row;
            BigDecimal rate = new BigDecimal((String) (price).get(0));
            BigDecimal size = new BigDecimal((String) (price).get(1));
            priceMap.put(rate, size);
        }
        return priceMap;
    }

    public static PriceBookUpdate fromL2Update(JSONObject update)
    {
        if (update == null || update.isEmpty())
        {
            return null;
        }

        JSONArray changes = (JSONArray) update.get(PayloadConstants.CHANGES);
        List<Price> buyUpdates = new ArrayList<>();
        List<Price> sellUpdates = new ArrayList<>();
        for (Object change : changes)
        {
            JSONArray entry = (JSONArray) change;
            SideEnum side = SideEnum.fromString((String) entry.get(0));
            BigDecimal rate = new BigDecimal((String) entry.get(1));
            BigDecimal size = new BigDecimal((String) entry.get(2));
            Price orderBookElement = new Price(rate, size);
            switch (side)
            {
                case BUY:
                    buyUpdates.add(orderBookElement);
                    break;
                case SELL:
                    sellUpdates.add(orderBookElement);
                    break;
                case UNKNOWN:
                    System.err.println("Unsupported side value: " + side);

            }
        }

        return new PriceBookUpdate(buyUpdates, sellUpdates);
    }
}
