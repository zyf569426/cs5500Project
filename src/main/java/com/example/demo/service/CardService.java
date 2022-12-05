package com.example.demo.service;

import com.example.demo.domain.CardDTO;
import com.example.demo.domain.Card;
import com.example.demo.domain.SimpleCardDTO;
import com.example.demo.repository.CardRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CardService {

	private final String CHECK_CARD_URL = "https://c3jkkrjnzlvl5lxof74vldwug40pxsqo.lambda-url.us-west-2.on.aws/";
	private final String CHECK_CARD_FUND_URL = "https://223didiouo3hh4krxhm4n4gv7y0pfzxk.lambda-url.us-west-2.on.aws";

	private CardRepository cardRepository;

	@Autowired
	public CardService(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}

	public List<Card> getAll() {
		return cardRepository.findAll();
	}

	public Optional<Card> get(Long id) {
		return cardRepository.findById(id);
	}

	public void add(Card card) {
		cardRepository.save(card);
	}

	public void delete(Long id) {
		boolean exists = cardRepository.existsById(id);
		if (!exists) {
			throw new IllegalStateException("Credit card with id " + id + " does not exists");
		}
		cardRepository.deleteById(id);
	}

	@Transactional(rollbackOn = Exception.class)
	public void update(
		Long id,
		String number,
		String validThru,
		String name,
		String csv,
		String billAddress,
		Float balance) {
		Card card = cardRepository.findById(id)
			.orElseThrow( () -> new IllegalStateException(
				"CreditCard with id " + id + " does not exist"
			));
		if (number != null) {
			card.setNumber(number);
		}
		if (validThru != null) {
			card.setValidThru(validThru);
		}
		if (name != null) {
			card.setName(name);
		}
		if (csv != null) {
			card.setCsv(csv);
		}
		if (billAddress != null) {
			card.setBillAddress(billAddress);
		}
		if (balance != null) {
			card.setBalance(balance);
		}
		cardRepository.save(card);
	}

	public void updateBalance(Long id, Float totalPrice) {
		Card card = cardRepository.findById(id)
			.orElseThrow( () -> new IllegalStateException(
				"CreditCard with id " + id + " does not exist"
			));
		Float newBalance = card.getBalance()-totalPrice;
		if (newBalance < 0) {
			throw new IllegalStateException("Not enough balance");
		}
		update(id, null, null, null, null,null, newBalance);
	}

	public boolean isCardValid(String number) {
		RestTemplate restTemplate = new RestTemplate();
		SimpleCardDTO card = new SimpleCardDTO(number);
		String response = restTemplate.postForObject(CHECK_CARD_URL, card, String.class);
		System.out.println(response);
		return response != null && response.contains("true");
	}

	public boolean isFundEnough(String number, double amt) {
		RestTemplate restTemplate = new RestTemplate();
		CardDTO card = new CardDTO(number, amt);
		String response = restTemplate.postForObject(CHECK_CARD_FUND_URL, card, String.class);
		System.out.println(response);
		return response != null && response.contains("true");
	}
}
