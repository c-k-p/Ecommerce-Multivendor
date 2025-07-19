package com.snecha.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.snecha.domain.USER_ROLE;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	private String email;

	private String fullName;

	private String mobile;

	private USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;

	@OneToMany
	@JoinColumn(name="user_id")
	private Set<Address> addresses = new HashSet<Address>();

	@ManyToMany
	@JsonIgnore
	private Set<Coupon> usedCoupons = new HashSet<Coupon>();

}
