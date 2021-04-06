package com.infiniteskills.data.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long accountId;
	
	@ManyToOne
	@JoinColumn(name = "bank_id")
	private Bank bank;
	
	@Enumerated(EnumType.STRING)
	private AccountType accountType;
	
	
	@OneToMany(mappedBy = "account")
	//@OneToMany(cascade = CascadeType.ALL)
	//@JoinColumn(name = "account_id" , nullable = false)
	private List<Transaction> transactions= new ArrayList<>();
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_account", joinColumns = @JoinColumn(name="account_id"),
				inverseJoinColumns = @JoinColumn(name="user_id"))
	private Set<User> users = new HashSet<>();
	
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
