package com.welias.exchange.coinbase.app.model;

import java.math.BigDecimal;

public class Price
{
    private final BigDecimal mRate;
    private final BigDecimal mSize;

    public Price(
            BigDecimal rate,
            BigDecimal size)
    {
        mRate = rate;
        mSize = size;
    }

    public BigDecimal getRate()
    {
        return mRate;
    }

    public BigDecimal getSize()
    {
        return mSize;
    }
}
