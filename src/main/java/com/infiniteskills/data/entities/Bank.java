package com.infiniteskills.data.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

@Entity
public class Bank {
	
	@Id
	@GeneratedValue
	private Long bankId;
	
	private String name;
	
	@Embedded
	private Address address;
	
	private boolean international;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdatedDate;
	
	private String updatedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	private String createdBy;
	
	@ElementCollection
	//@CollectionTable(name = "bank_contacts", joinColumns = @JoinColumn(name="bankId"))
	//@Column(name = "contact-name")
	private Map<String,String> contacts = new HashMap<>();

}
