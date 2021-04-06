package com.infiniteskills.data.entities;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class Investment {

	private String name;
	private String issuer;
	private Date purchaseDate;
	
}