package com.welias.exchange.coinbase.app.ui;

import com.welias.exchange.coinbase.app.api.internal.PriceBook;

public interface Renderer
{
    void render(PriceBook priceBook);
}
