package com.example.demo.service;

import com.example.demo.domain.Product;
import com.example.demo.domain.UserOrder;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.util.StringUtil;
import com.example.demo.validator.CardValidator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;

	@Autowired
	public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
	}

	public List<UserOrder> getAll() {
		return orderRepository.findAll();
	}

	public List<UserOrder> listOrderByStatus(String status) {
		return orderRepository.findOrderByStatus(status);
	}

	public List<UserOrder> listOrderByCondition(String status, Long userId) {
		if (status == null) {
			return orderRepository.findOrderByUserId(userId);
		} else if (userId == null) {
			return orderRepository.findOrderByStatus(status);
		} else {
			return orderRepository.findOrderByStatusAndUserId(status, userId);
		}
	}

	public Optional<UserOrder> get(Long id) {
		return orderRepository.findById(id);
	}

	public Float getTotalBalanceByUserId(Long userId) {
		return orderRepository.findTotalBalanceByUserId(userId);
	}

	public void add(UserOrder userOrder) {
		Optional<Product> exists = productRepository.findProductByName(userOrder.getProductName());
		if (exists.isEmpty()) {
			throw new IllegalStateException(
				"Product name not exist: " + userOrder.getProductName());
		}
		Product product = exists.get();
		userOrder.setPrice(product.getPrice());
		userOrder.setTotalPrice(product.getPrice() * userOrder.getQuantity());

		if (!CardValidator.isCardValid(userOrder.getCardNumber())) {
			throw new IllegalStateException("Card number is invalid: " + userOrder.getCardNumber());
		}

		if (userOrder.getCardType().equals(StringUtil.DEBIT_CARD_TYPE)) {
			if (!CardValidator.isFundEnough(userOrder.getCardNumber(), userOrder.getTotalPrice())) {
				throw new IllegalStateException(
					"Insufficient fund in debit card: " + userOrder.getCardNumber());
			}
		}
		userOrder.setStatus(StringUtil.ORDER_COMPLETED_STATUS);
		orderRepository.save(userOrder);
	}

	public void refund(Long id, Float amount) {
		UserOrder userOrder = orderRepository.findById(id)
			.orElseThrow(() -> new IllegalStateException(
				"Order with id " + id + " does not exist"
			));
		if (userOrder.getStatus().equals(StringUtil.ORDER_FULL_REFUND_STATUS)) {
			throw new IllegalStateException("Order with id " + id + " cannot be refund");
		}

		if (amount == null || amount <= 0 || amount > userOrder.getTotalPrice()) {
			throw new IllegalStateException("Refund amount must > 0 && <= totalPrice");
		}

		if (amount.equals(userOrder.getTotalPrice())) {
			userOrder.setStatus(StringUtil.ORDER_FULL_REFUND_STATUS);
		} else {
			userOrder.setStatus(StringUtil.ORDER_PARTIAL_REFUND_STATUS);
		}
		userOrder.setTotalPrice(userOrder.getTotalPrice() - amount);
		orderRepository.save(userOrder);
	}

}
