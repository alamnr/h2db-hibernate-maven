package com.infiniteskills.data.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Portfolio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long portfolioId;
	
	private String name;
	
	@OneToMany(mappedBy = "portfolio")
	private List<Investment> investments = new ArrayList<>();

}
