package com.infiniteskills.data.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "investmentType")
public abstract class Investment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long investmentId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "portfolioId")
	private Portfolio portfolio;
	
	private String name;
	private String issuer;
	private Date purchaseDate;
	
}