package com.snecha.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snecha.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>  {

}
