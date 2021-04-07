package com.infiniteskills.data.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Stock extends Investment {	
	
	
	private BigDecimal sharePrice;
	
	private int quantity;

}
