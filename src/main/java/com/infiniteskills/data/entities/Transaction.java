package com.infiniteskills.data.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long transactionId;
	
	private String transactionType;
	
	private String title;
	
	private BigDecimal amount;
	
	private BigDecimal initialBalance;
	
	private BigDecimal closingBalance;
	
	private String notes;
	
	private String lastUpdatedBy;
	
	private Date lastUpdatedDate;
	
	private String createdBy;
	
	private Date createdDate;
	
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;

}
