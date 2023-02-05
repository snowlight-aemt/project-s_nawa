package me.snowlight.s_nawa.orders.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;

@Getter
public class PlaceOrder {
    private final long shopId;
    private final long itemId;
    private final int price;

    public PlaceOrder(long shopId, long itemId, int price) {
        this.shopId = shopId;
        this.itemId = itemId;
        this.price = price;
    }
}
