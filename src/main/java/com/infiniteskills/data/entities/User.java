package com.infiniteskills.data.entities;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
	
	@Id
	@GeneratedValue
	private Long userId;
	
	private String  firstName;
	
	private String lastName;
	
	@Column(nullable = false)
	private Date birthDate;
	
	private String emailAddress;
	
	private Date lastUpdatedDate;
	
	private String lastUpdatedBy;
	
	@Column(updatable =false)
	private Date createdDate;
	
	@Column(updatable =false)
	private String createdBy;
	
	@Transient
	private boolean valid;
	
	//@Formula("lower(datediff(curdate(),birth_date)/365)")
	private int age;
	
	@Embedded
	@AttributeOverrides({@AttributeOverride(name = "addressLine1", column = @Column(name="user_addressLine1"))})
	private Address address ;
	
	
	

}
