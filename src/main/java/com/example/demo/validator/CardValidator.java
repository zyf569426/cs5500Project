package com.example.demo.validator;

import com.example.demo.domain.CardDTO;
import com.example.demo.domain.SimpleCardDTO;
import org.springframework.web.client.RestTemplate;

public class CardValidator {

	private static final String CHECK_CARD_URL = "https://c3jkkrjnzlvl5lxof74vldwug40pxsqo.lambda-url.us-west-2.on.aws/";
	private static final String CHECK_CARD_FUND_URL = "https://223didiouo3hh4krxhm4n4gv7y0pfzxk.lambda-url.us-west-2.on.aws";

	public static boolean isCardValid(String number) {
		RestTemplate restTemplate = new RestTemplate();
		SimpleCardDTO card = new SimpleCardDTO(number);
		String response;
		try {
			response = restTemplate.postForObject(CHECK_CARD_URL, card, String.class);
		} catch (Exception e) {
			return false;
		}
		return response != null && response.contains("true");
	}

	public static boolean isFundEnough(String number, double amt) {
		RestTemplate restTemplate = new RestTemplate();
		CardDTO card = new CardDTO(number, amt);
		String response;
		try {
			response = restTemplate.postForObject(CHECK_CARD_FUND_URL, card, String.class);
		} catch (Exception e) {
			return false;
		}
		return response != null && response.contains("true");
	}
}
