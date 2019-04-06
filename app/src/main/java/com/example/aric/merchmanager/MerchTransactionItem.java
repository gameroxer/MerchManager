package com.example.aric.merchmanager;

import java.io.Serializable;

public class MerchTransactionItem implements Serializable {
    public MerchItem item;
    public int amount;
    public boolean hasPriceOverride;
    public float overridePrice;
}
