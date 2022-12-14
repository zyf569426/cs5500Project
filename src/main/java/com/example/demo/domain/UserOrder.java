package com.example.demo.domain;

import com.sun.istack.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserOrder {
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "user_id")
	@NotNull
	private Long userId;

	@Column(name = "product_name")
	@NotNull
	private String productName;

	@Column(name = "quantity")
	@NotNull
	private Integer quantity;

	@Column(name = "price")
	@NotNull
	private Float price;

	@Column(name = "total_price")
	@NotNull
	private Float totalPrice;

	@Column(name = "card_number")
	@NotNull
	private String cardNumber;

	@Column(name = "card_type")
	@NotNull
	private String cardType;


	@Column(name = "status")
	@NotNull
	private String status;

}

