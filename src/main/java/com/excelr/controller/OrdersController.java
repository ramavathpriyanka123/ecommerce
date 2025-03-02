
package com.excelr.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.excelr.model.Orders;
import com.excelr.service.OrderService;
import com.excelr.service.CartService;
import com.razorpay.RazorpayException;

import ch.qos.logback.core.model.Model;

@Controller
public class OrdersController {
    
    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService; 
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable("orderId") int orderId) {
        try {
            Orders order = orderService.getOrderById(orderId);
            if (order != null) {
                return ResponseEntity.ok(order);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Order with ID " + orderId + " not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An error occurred while fetching the order.");
        }
    }

    @PostMapping(value = "/createOrder", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Orders orders) throws RazorpayException {
        Orders razorpayOrder = orderService.createOrder(orders);

        // Prepare response map with additional orderId
        Map<String, Object> response = new HashMap<>();
        response.put("orderId", razorpayOrder.getOrderId());
        response.put("order", razorpayOrder);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/paymentCallback")
    public ResponseEntity<String> paymentCallback(@RequestParam Map<String, String> response) {
        try {
            String razorpayOrderId = response.get("razorpay_order_id");
            String userId = response.get("user_id");

            orderService.updateStatus(response);
            if (userId != null) {
                cartService.clearCart(Long.parseLong(userId));
            }
            return ResponseEntity.ok("Payment successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing payment callback");
        }
    }


}
