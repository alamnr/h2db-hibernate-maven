package com.infiniteskills.data.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AccountType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long accountTypeId;
	
	
	private String accountType;
}
