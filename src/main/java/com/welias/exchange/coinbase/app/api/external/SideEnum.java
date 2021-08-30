package com.welias.exchange.coinbase.app.api.external;

public enum SideEnum
{
    BUY("buy"),
    SELL("sell"),
    UNKNOWN("unknown");

    private final String mValue;

    SideEnum(String value)
    {
        mValue = value;
    }

    public static SideEnum fromString(String text)
    {
        for (SideEnum b : SideEnum.values())
        {
            if (b.mValue.equalsIgnoreCase(text))
            {
                return b;
            }
        }
        return UNKNOWN;
    }
}
