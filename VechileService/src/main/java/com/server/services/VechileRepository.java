package com.server.services;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VechileRepository extends 
    JpaRepository<Vechile, Long>{
  //Vechile findById(String from, String to);
}