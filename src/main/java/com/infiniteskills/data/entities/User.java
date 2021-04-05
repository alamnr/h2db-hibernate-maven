package com.infiniteskills.data.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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
	
	/*
	 * @Embedded
	 * 
	 * @AttributeOverrides({@AttributeOverride(name = "addressLine1", column
	 * = @Column(name="user_addressLine1"))}) private Address address ;
	 */
	
	@ElementCollection
	
	private List<Address> addresses = new ArrayList<>();
	
	//@OneToOne(mappedBy = "user")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "credential_id")
	private  Credential credential;
	
	@EqualsAndHashCode.Exclude
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "users")
	private Set<Account> accounts = new HashSet<>();	
	
	

}
