package com.welias.exchange.coinbase.app.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class PriceBook
{
    private final SortedMap<BigDecimal, BigDecimal> mSizeToBid;
    private final SortedMap<BigDecimal, BigDecimal> mSizeToAsk;

    public PriceBook(
            Map<BigDecimal, BigDecimal> askMap,
            Map<BigDecimal, BigDecimal> bidMap)
    {
        mSizeToAsk = new TreeMap<>(askMap);
        mSizeToBid = new TreeMap<>(Collections.reverseOrder());
        mSizeToBid.putAll(bidMap);
    }

    /**
     * Update order book given a list of (price, size) combos.
     * Note: As per CoinbasePro API - a size 0 means removal.
     */
    public void applyUpdate(PriceBookUpdate priceBookUpdate)
    {
        List<Price> bids = priceBookUpdate.getBids();
        for (Price bid : bids)
        {
            if (bid.getSize().signum() == 0)
            {
                mSizeToBid.remove(bid.getRate());
            }
            else
            {
                mSizeToBid.put(bid.getRate(), bid.getSize());
            }
        }

        List<Price> asks = priceBookUpdate.getAsks();
        for (Price ask : asks)
        {
            if (ask.getSize().signum() == 0)
            {
                mSizeToAsk.remove(ask.getRate());
            }
            else
            {
                mSizeToAsk.put(ask.getRate(), ask.getSize());
            }
        }
    }

    public List<BigDecimal> getTopAsks(int numberOfLevels)
    {
        List<BigDecimal> result = new ArrayList<>();
        Iterator<BigDecimal> iterator = mSizeToAsk.keySet().iterator();
        int count = 0;
        while (iterator.hasNext())
        {
            result.add(iterator.next());
            count++;
            if (count >= numberOfLevels)
            {
                break;
            }
        }
        return result;
    }

    public List<BigDecimal> getTopBids(int numberOfLevels)
    {
        List<BigDecimal> result = new ArrayList<>();
        Iterator<BigDecimal> iterator = mSizeToBid.keySet().iterator();
        int count = 0;
        while (iterator.hasNext())
        {
            result.add(iterator.next());
            count++;
            if (count >= numberOfLevels)
            {
                break;
            }
        }
        return result;
    }
}
