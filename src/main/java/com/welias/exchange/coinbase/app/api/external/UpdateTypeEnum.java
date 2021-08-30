package com.welias.exchange.coinbase.app.api.external;

public enum UpdateTypeEnum
{
    SNAPSHOT("snapshot"),
    L2_UPDATE("l2update"),
    ERROR("error"),
    UNKNOWN("unknown");

    private final String mValue;

    UpdateTypeEnum(String value)
    {
        mValue = value;
    }

    public static UpdateTypeEnum fromString(String text)
    {
        for (UpdateTypeEnum b : UpdateTypeEnum.values())
        {
            if (b.mValue.equalsIgnoreCase(text))
            {
                return b;
            }
        }
        return UNKNOWN;
    }
}
