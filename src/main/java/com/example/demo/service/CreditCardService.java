package com.example.demo.service;

import com.example.demo.domain.CardDTO;
import com.example.demo.domain.CreditCard;
import com.example.demo.domain.Product;
import com.example.demo.domain.SimpleCardDTO;
import com.example.demo.domain.User;
import com.example.demo.repository.CreditCardRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CreditCardService {

	private final String CHECK_CARD_URL = "https://c3jkkrjnzlvl5lxof74vldwug40pxsqo.lambda-url.us-west-2.on.aws/";
	private final String CHECK_CARD_FUND_URL = "https://223didiouo3hh4krxhm4n4gv7y0pfzxk.lambda-url.us-west-2.on.aws";

	private CreditCardRepository creditCardRepository;

	@Autowired
	public CreditCardService(CreditCardRepository creditCardRepository) {
		this.creditCardRepository = creditCardRepository;
	}

	public List<CreditCard> getAll() {
		return creditCardRepository.findAll();
	}

	public Optional<CreditCard> get(Long id) {
		return creditCardRepository.findById(id);
	}

	public void add(CreditCard creditCard) {
		creditCardRepository.save(creditCard);
	}

	public void delete(Long id) {
		boolean exists = creditCardRepository.existsById(id);
		if (!exists) {
			throw new IllegalStateException("Credit card with id " + id + " does not exists");
		}
		creditCardRepository.deleteById(id);
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
		CreditCard creditCard = creditCardRepository.findById(id)
			.orElseThrow( () -> new IllegalStateException(
				"CreditCard with id " + id + " does not exist"
			));
		if (number != null) {
			creditCard.setNumber(number);
		}
		if (validThru != null) {
			creditCard.setValidThru(validThru);
		}
		if (name != null) {
			creditCard.setName(name);
		}
		if (csv != null) {
			creditCard.setCsv(csv);
		}
		if (billAddress != null) {
			creditCard.setBillAddress(billAddress);
		}
		if (balance != null) {
			creditCard.setBalance(balance);
		}
		creditCardRepository.save(creditCard);
	}

	public void updateBalance(Long id, Float totalPrice) {
		CreditCard creditCard = creditCardRepository.findById(id)
			.orElseThrow( () -> new IllegalStateException(
				"CreditCard with id " + id + " does not exist"
			));
		Float newBalance = creditCard.getBalance()-totalPrice;
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
