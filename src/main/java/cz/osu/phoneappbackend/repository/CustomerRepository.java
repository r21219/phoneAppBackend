package cz.osu.phoneappbackend.repository;

import cz.osu.phoneappbackend.model.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUserName(String name);
    boolean existsByUserName(String name);
}