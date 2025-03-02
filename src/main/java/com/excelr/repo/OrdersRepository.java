package com.excelr.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.excelr.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    Orders findByRazorpayOrderId(String razorpayId);
    @Query("SELECT o FROM Orders o WHERE o.orderId = :orderId")
    Orders findOrderById(@Param("orderId") int orderId);

}
