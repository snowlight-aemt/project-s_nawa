package me.snowlight.s_nawa.orders.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.snowlight.s_nawa.orders.model.OrderRepository;
import me.snowlight.s_nawa.orders.model.Orders;
import me.snowlight.s_nawa.orders.model.PlaceOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class OrdersController {
    private final OrderRepository orderRepository;

    @PostMapping("/api/orders/{id}/place-order")
    public ResponseEntity placeOrder(@RequestBody PlaceOrder placeOrder, @PathVariable("id") Long id) {
        orderRepository.save(new Orders(id, placeOrder));

        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/orders/{id}")
    public ResponseEntity<Orders> order(@PathVariable Long id) {
        return ResponseEntity.ok(orderRepository.findById(id).orElseThrow(IllegalArgumentException::new));
    }
}
