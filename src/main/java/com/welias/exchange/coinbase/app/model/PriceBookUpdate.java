package com.welias.exchange.coinbase.app.model;

import java.util.List;
import java.util.Objects;

public class PriceBookUpdate
{
    private final List<Price> mBids;
    private final List<Price> mAsks;

    public PriceBookUpdate(
            List<Price> bids,
            List<Price> asks)
    {
        mBids = bids;
        mAsks = asks;
    }

    public List<Price> getBids()
    {
        return mBids;
    }

    public List<Price> getAsks()
    {
        return mAsks;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceBookUpdate that = (PriceBookUpdate) o;
        return Objects.equals(mBids, that.mBids) && Objects.equals(mAsks, that.mAsks);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(mBids, mAsks);
    }

    @Override
    public String toString()
    {
        return "PriceBookUpdate{" +
                "mBids=" + mBids +
                ", mAsks=" + mAsks +
                '}';
    }
}
