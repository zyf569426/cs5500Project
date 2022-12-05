package com.example.demo.repository;

import com.example.demo.domain.Product;
import com.example.demo.domain.UserOrder;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<UserOrder,Long> {
	@Query("select o from UserOrder o where o.status = ?1")
	List<UserOrder> findOrderByStatus(String status);


	@Query("select sum(totalPrice) from UserOrder where userId = ?1")
	Float findTotalBalanceByUserId(Long userId);
}
