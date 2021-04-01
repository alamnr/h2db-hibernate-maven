package com.infiniteskills.data.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	private String  firstName;
	
	private String lastName;
	
	private Date birthDate;
	
	private String emailAddress;
	
	private Date lastUpdatedDate;
	
	private String lastUpdatedBy;
	
	private Date createdDate;
	
	private String createdBy;

}
