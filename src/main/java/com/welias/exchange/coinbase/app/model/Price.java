package com.welias.exchange.coinbase.app.model;

import java.math.BigDecimal;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Objects.equals(mRate, price.mRate) && Objects.equals(mSize, price.mSize);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(mRate, mSize);
    }

    @Override
    public String toString()
    {
        return "Price{" +
                "mRate=" + mRate +
                ", mSize=" + mSize +
                '}';
    }
}
