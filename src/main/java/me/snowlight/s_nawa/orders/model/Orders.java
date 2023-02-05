package me.snowlight.s_nawa.orders.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Orders {
    @Id
    private Long id;

    @Column(unique = true)
    private Long shopId;
    private Long userId;
    private Long itemId;
    private int price;
    private OrderStatus status;

    public Orders(Long id, PlaceOrder placeOrder) {
        this.id = id;
        this.shopId = placeOrder.getShopId();
        this.itemId = placeOrder.getItemId();
        this.price = placeOrder.getPrice();
        this.status = OrderStatus.PENDING;
    }
}
