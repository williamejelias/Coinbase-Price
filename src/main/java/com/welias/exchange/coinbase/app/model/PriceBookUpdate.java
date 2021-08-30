package com.welias.exchange.coinbase.app.model;

import java.util.List;

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
}
