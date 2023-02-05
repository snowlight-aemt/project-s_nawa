package me.snowlight.s_nawa.orders.controller.api.orders.id;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.snowlight.s_nawa.orders.model.OrderRepository;
import me.snowlight.s_nawa.orders.model.OrderStatus;
import me.snowlight.s_nawa.orders.model.Orders;
import me.snowlight.s_nawa.orders.model.PlaceOrder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("주문 등록")
    @ParameterizedTest
    @CsvSource({
            "1, 1, 1, 100000",
            "2, 2, 2, 200000",
            "3, 3, 3, 300000",
            "4, 4, 4, 400000",
    })
    void place_order(long orderId, long shopId, long itemId, int price) throws Exception {
        String placeOrderJson = objectMapper.writeValueAsString(new PlaceOrder(shopId, itemId, price));

        mvc.perform(post("/api/orders/" + orderId + "/place-order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(placeOrderJson))
                .andExpect(status().isOk());

        Orders order = orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        Assertions.assertThat(order.getShopId()).isEqualTo(shopId);
        Assertions.assertThat(order.getItemId()).isEqualTo(itemId);
        Assertions.assertThat(order.getPrice()).isEqualTo(price);
        Assertions.assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);
    }


    @TestConfiguration
    public static class TestConfig {
    }
}
