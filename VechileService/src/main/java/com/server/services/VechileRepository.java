package com.server.services;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author mkader3
 *
 *This class is database access for Vechile CRUD operations
 *also more functionalities can be added here but basic CRUD i supplied by Spring framework
 *
 *The class seems empty but its not, basic functionalities are extended from JpaRepository
 *
 */
public interface VechileRepository extends 
    JpaRepository<Vechile, Long>{
}