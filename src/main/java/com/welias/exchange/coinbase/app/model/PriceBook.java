package com.welias.exchange.coinbase.app.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
     * Update order book for a given list of (rate, size) pairs.
     * Note: CoinbasePro API - 0 size means remove price.
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceBook priceBook = (PriceBook) o;
        return Objects.equals(mSizeToBid, priceBook.mSizeToBid) && Objects.equals(mSizeToAsk, priceBook.mSizeToAsk);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(mSizeToBid, mSizeToAsk);
    }

    @Override
    public String toString()
    {
        return "PriceBook{" +
                "mSizeToBid=" + mSizeToBid +
                ", mSizeToAsk=" + mSizeToAsk +
                '}';
    }
}
