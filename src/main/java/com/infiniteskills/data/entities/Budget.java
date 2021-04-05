package com.infiniteskills.data.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Budget {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long budgetId;
	
	private String name;
	
	private BigDecimal goalAmount;
	
	private String period;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "budget_transaction", joinColumns = @JoinColumn(name="budget_id"), 
				inverseJoinColumns = @JoinColumn(name="transaction_id"))	
	private List<Transaction> transactions = new ArrayList<>();

}
