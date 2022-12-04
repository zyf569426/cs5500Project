package com.example.demo.domain;




public class CardDTO {
	private String card_number;
	private Double amt;
	public CardDTO(String card_number, double amt) {
		this.card_number = card_number;
		this.amt = amt;
	}

	public String getCard_number() {
		return card_number;
	}

	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}

	public Double getAmt() {
		return amt;
	}

	public void setAmt(Double amt) {
		this.amt = amt;
	}
}