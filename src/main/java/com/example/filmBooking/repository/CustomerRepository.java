package com.example.filmBooking.repository;

import com.example.filmBooking.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Query("Select custom From Customer custom Where custom.email = :email")
    Customer findEmail(@Param("email") String email);

    // danh sách khách hàng được sử dụng 1 khuyến mãi
    @Query(value = "SELECT c.* FROM customer c " +
            "INNER JOIN rank_customer r ON c.rank_customer_id = r.id " +
            "INNER JOIN promotion p ON p.rank_customer_id = r.id " +
            "WHERE p.id = ?1 ",nativeQuery = true)
    List<Customer> findByPromotion (String idPromotion);

}
