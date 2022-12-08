package com.example.demo.controller;

import com.example.demo.domain.UserOrder;
import com.example.demo.service.OrderService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/order")
public class OrderController {

	private final OrderService orderService;

	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping
	public List<UserOrder> getAll() {
		return orderService.getAll();
	}

	@GetMapping(path = "/status/{status}")
	public List<UserOrder> listOrderByStatus(@PathVariable("status") String status) {
		return orderService.listOrderByStatus(status);
	}

	@GetMapping(path = "/condition")
	public List<UserOrder> listOrderByCondition
		(@RequestParam(required = false) String status,
		@RequestParam(required = false) Long userId){
		return orderService.listOrderByCondition(status, userId);
	}

	@GetMapping(path = "{id}")
	public Optional<UserOrder> get(@PathVariable("id") Long id) {
		return orderService.get(id);
	}

	@PostMapping
	public void add(@RequestBody UserOrder userOrder) {
		orderService.add(userOrder);
	}

	@PutMapping(path = "{id}")
	public void refund(@PathVariable("id") Long id, @RequestParam(required = true) Float amount) {
		orderService.refund(id, amount);
	}
}


