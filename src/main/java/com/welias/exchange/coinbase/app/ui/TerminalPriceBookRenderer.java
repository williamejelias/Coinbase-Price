package com.welias.exchange.coinbase.app.ui;

import com.welias.exchange.coinbase.app.model.PriceBook;
import com.welias.exchange.coinbase.app.websocket.PriceBookUpdateListener;

import java.math.BigDecimal;
import java.util.List;

public class TerminalPriceBookRenderer implements Renderer, PriceBookUpdateListener
{
    public static final int NUMBER_OF_LEVELS = 10;

    @Override
    public void handleUpdate(PriceBook priceBook)
    {
        render(priceBook);
    }

    @Override
    public void render(PriceBook priceBook)
    {
        List<BigDecimal> asks = priceBook.getTopAsks(TerminalPriceBookRenderer.NUMBER_OF_LEVELS);
        List<BigDecimal> bids = priceBook.getTopBids(TerminalPriceBookRenderer.NUMBER_OF_LEVELS);
        int depth = Math.max(bids.size(), asks.size());

        // TODO perhaps move towards a curses style implementation
        System.out.printf("%3s%12s%12s %n", "Row", "Bid", "Ask");
        for (int i = 0; i < depth; i++)
        {
            System.out.printf("%3s%12.4f%12.4f %n", i, bids.get(i), asks.get(i));
        }
    }
}
