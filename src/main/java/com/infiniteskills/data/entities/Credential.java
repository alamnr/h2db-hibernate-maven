package com.infiniteskills.data.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Credential {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long credentialId;
	
	private String userName;
	
	private String password;
	
	//@OneToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "user_id")
	@OneToOne(mappedBy = "credential")
	private User user;

}
