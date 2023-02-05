package me.snowlight.s_nawa.orders.controller.api.orders.id;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.snowlight.s_nawa.orders.model.OrderRepository;
import me.snowlight.s_nawa.orders.model.OrderStatus;
import me.snowlight.s_nawa.orders.model.Orders;
import me.snowlight.s_nawa.orders.model.PlaceOrder;
import org.aspectj.weaver.ast.Or;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GetTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("주문 시작")
    @Test
    void start_order() throws Exception {
        long orderId = 1L;
        long shopId = 1L;
        long itemId = 1L;
        int price = 100000;
        String placeOrderJson = objectMapper.writeValueAsString(new PlaceOrder(shopId, itemId, price));

        mvc.perform(post("/api/orders/" + orderId + "/place-order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(placeOrderJson))
                .andExpect(status().isOk());

        mvc.perform(get("/api/orders/" + orderId))
                .andExpect(jsonPath("$.id").value(orderId))
                .andExpect(jsonPath("$.itemId").value(itemId))
                .andExpect(jsonPath("$.shopId").value(shopId))
                .andExpect(jsonPath("$.price").value(price))
                .andExpect(jsonPath("$.status").value(OrderStatus.PENDING.name()))
                .andExpect(status().isOk());
    }
}
