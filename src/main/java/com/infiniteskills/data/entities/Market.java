package com.infiniteskills.data.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Market {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long marketId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumns({@JoinColumn(name = "currencyName" , referencedColumnName = "name")
					,@JoinColumn(name = "countryName", referencedColumnName = "countryName")})
	private Currency currency;
	
	private String marketName;

}
