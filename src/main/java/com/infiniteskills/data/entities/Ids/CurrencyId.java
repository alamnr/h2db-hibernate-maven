package com.infiniteskills.data.entities.Ids;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyId implements Serializable{
	
	private String name;
	
	private String countryName;

}
