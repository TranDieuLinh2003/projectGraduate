package com.example.filmBooking.repository;

import com.example.filmBooking.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PromotionRepository extends JpaRepository<Promotion, String> {
    String voucherCustomerValue = "select p.* from promotion p " +
            "join rank_customer r on p.rank_customer_id= r.id " +
            "join customer c on c.rank_customer_id= r.id " +
            "where c.id= ?1";

    @Query(value = voucherCustomerValue, nativeQuery = true)
    List<Promotion> listVoucherCustomer(String customerId);

}
