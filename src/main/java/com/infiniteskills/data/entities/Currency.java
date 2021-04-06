package com.infiniteskills.data.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.infiniteskills.data.entities.Ids.CurrencyId;

import lombok.Data;

@Data
@Entity
@IdClass(CurrencyId.class)
public class Currency {
	
	@Id
	private String name;
	
	@Id
	private String countryName;
	
	private String symbol;

}
