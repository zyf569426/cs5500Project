package com.example.demo.domain;

public class SimpleCardDTO {
	private String card_number;
	public SimpleCardDTO(String card_number) {
		this.card_number = card_number;
	}

	public String getCard_number() {
		return card_number;
	}

	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}
}
