package com.infiniteskills.data.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long accountId;
	
	@ManyToOne
	@JoinColumn(name = "bank_id")
	private Bank bank;
	
	@ManyToOne
	@JoinColumn(name = "account_type_id")
	private AccountType accountType;
	
	@OneToMany(mappedBy = "account")
	//@OneToMany(cascade = CascadeType.ALL)
	//@JoinColumn(name = "account_id" , nullable = false)
	private List<Transaction> transactions= new ArrayList<>();
	
	private String accountName;
	
	private BigDecimal initialBalance;
	
	private BigDecimal currentBalance;
	
	private Date openDate;
	
	private Date closeDate;
	
	private String lastUpdatedBy;
	
	private Date lastUpdateDate;
	
	private String createdBy;
	
	private Date ceatedDate;
	
	
	

}
