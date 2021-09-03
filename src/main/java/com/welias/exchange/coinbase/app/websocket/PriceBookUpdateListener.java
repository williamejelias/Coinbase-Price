package com.welias.exchange.coinbase.app.websocket;

import com.welias.exchange.coinbase.app.api.internal.PriceBook;

public interface PriceBookUpdateListener
{
    void handleUpdate(PriceBook priceBook);
}
