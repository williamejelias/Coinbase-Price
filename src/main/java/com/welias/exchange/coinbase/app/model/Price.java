package com.welias.exchange.coinbase.app.model;

import java.math.BigDecimal;

public class Price
{
    private final BigDecimal mPrice;
    private final BigDecimal mSize;

    public Price(
            BigDecimal pricePar,
            BigDecimal sizePar)
    {
        mPrice = pricePar;
        mSize = sizePar;
    }

    public BigDecimal getPrice()
    {
        return mPrice;
    }

    public BigDecimal getSize()
    {
        return mSize;
    }
}
