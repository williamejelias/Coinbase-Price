package com.welias.exchange.coinbase.app.websocket;

import com.welias.exchange.coinbase.app.model.PriceBook;

public interface PriceBookUpdateListener
{
    void handleUpdate(PriceBook priceBook);
}
