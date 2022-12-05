package com.example.demo.controller;

import com.example.demo.domain.Card;
import com.example.demo.service.CardService;
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
@RequestMapping(path = "card")
public class CardController {

	private final CardService cardService;

	@Autowired
	public CardController(CardService cardService) {
		this.cardService = cardService;
	}

	@GetMapping
	public List<Card> getAll() {
		return cardService.getAll();
	}

	@GetMapping(path = "{id}")
	public Optional<Card> get(@PathVariable("id") Long id) {
		return cardService.get(id);
	}

	@PostMapping
	public void add(@RequestBody Card card) {
		cardService.add(card);
	}

	@DeleteMapping(path = "{id}")
	public void delete(@PathVariable("id") Long id) {
		cardService.delete(id);
	}

	@PutMapping(path = "{id}")
	public void update(
		@PathVariable("id") Long id,
		@RequestParam(required = false) String number,
		@RequestParam(required = false) String validThru,
		@RequestParam(required = false) String name,
		@RequestParam(required = false) String csv,
		@RequestParam(required = false) String billAddress,
		@RequestParam(required = false) Float balance) {
		cardService.update(id, number, validThru, name, csv, billAddress, balance);
	}

	@GetMapping(path = "check/{number}")
	public String checkCard(@PathVariable("number") String number) {
		return cardService.isCardValid(number) ? "true" : "false";
	}

	@GetMapping(path = "check/{number}/{amt}")
	public String checkCardFund(@PathVariable("number") String number, @PathVariable("amt") double amt) {
		return cardService.isFundEnough(number, amt) ? "true" : "false";
	}
}

