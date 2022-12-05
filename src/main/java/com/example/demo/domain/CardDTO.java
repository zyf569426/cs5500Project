package com.example.demo.domain;

public class CardDTO {
	private String card_number;
	private Double amt;
	public CardDTO(String card_number, double amt) {
		this.card_number = card_number;
		this.amt = amt;
	}
}