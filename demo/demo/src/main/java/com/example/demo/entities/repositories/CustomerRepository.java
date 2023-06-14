package com.example.demo.entities.repositories;

import com.example.demo.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "Select * from customer where customer_id = :id", nativeQuery = true)
    Customer findByCustomerId(int id);
}
