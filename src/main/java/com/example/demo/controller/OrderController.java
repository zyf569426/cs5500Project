package com.example.demo.controller;

import com.example.demo.domain.UserOrder;
import com.example.demo.service.OrderService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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

	@GetMapping(path = "{id}")
	public Optional<UserOrder> get(@PathVariable("id") Long id) {
		return orderService.get(id);
	}

	@PostMapping
	public void add(@RequestBody UserOrder userOrder) {
		orderService.add(userOrder);
	}

//	@DeleteMapping(path = "{id}")
//	public void delete(@PathVariable("id") Long id) {
//		orderService.delete(id);
//	}

	@PutMapping(path = "{id}")
	public void refund(@PathVariable("id") Long id, @RequestParam(required = true) Float amount) {
		orderService.refund(id, amount);
	}
}


