package com.infiniteskills.data.entities;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Embeddable
public class Address {
	
	public String addressLine1;
	public String addressLine2;
	public String city;
	public String state;
	public String zipCode;

	public Address() {
	}
}