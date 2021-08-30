package com.welias.exchange.coinbase.app;

import com.welias.exchange.coinbase.app.model.PriceBook;

import java.math.BigDecimal;
import java.util.List;

public class UITools
{
    public static final int NUMBER_OF_LEVELS = 10;

    public static void render(PriceBook priceBook)
    {
        List<BigDecimal> asks = priceBook.getTopAsks(NUMBER_OF_LEVELS);
        List<BigDecimal> bids = priceBook.getTopBids(NUMBER_OF_LEVELS);
        int depth = Math.max(bids.size(), asks.size());

        // TODO perhaps move towards a curses style implementation
        System.out.printf("%3s%12s%12s %n", "Row", "Bid", "Ask");
        for (int i = 0; i < depth; i++)
        {
            System.out.printf("%3s%12.4f%12.4f %n", i, bids.get(i), asks.get(i));
        }
    }
}
