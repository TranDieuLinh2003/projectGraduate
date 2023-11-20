package com.example.filmBooking.repository;

import com.example.filmBooking.model.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.math.BigDecimal;


public interface BillRepository extends JpaRepository<Bill, String> {
    @Query(value = "SELECT * FROM projectLinh.bill b where b.status = 0;", nativeQuery = true)
    Page<Bill> billStatusZero(Pageable pageable);

    @Query(value = "SELECT * FROM projectLinh.bill b where b.status = 1;", nativeQuery = true)
    Page<Bill> billStatusOne(Pageable pageable);

//    Page<Bill> findByDateCreateBetweenAndDateCreate
    Page<Bill> findByDateCreateBetween(Date startDate, Date endDate, Pageable pageable);

    @Query("SELECT COUNT(b) FROM Bill b WHERE b.status = 0")
    String countSoldTicketsWithStatusZero();
    
    @Query(value = "select sum(total_money) from bill b join" +
            "(SELECT DISTINCT bill_id, c.id, c.name FROM bill_ticket bt " +
            "JOIN ticket t ON t.id = bt.ticket_id" +
            " JOIN schedule s ON t.schedule_id = s.id" +
            " JOIN room r ON s.room_id = r.id" +
            " JOIN cinema c ON c.id = r.cinema_id) d on d.bill_id = b.id" +
            " WHERE " +
            " d.id=?1 and"+
            "    b.date_create >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) "+
            "GROUP BY DATE(b.date_create), d.id = ?1"+
            " ORDER BY DATE(b.date_create) DESC "
            , nativeQuery = true)
    List<BigDecimal> revenueInTheLast7Days(String cinemaId);

    @Query(value = " SELECT m.name, COUNT(bt.id) AS bill_ticket_count " +
            " FROM bill_ticket bt" +
            " JOIN  bill b ON bt.bill_id = b.id" +
            " JOIN ticket t ON bt.ticket_id = t.id" +
            " JOIN schedule s ON t.schedule_id = s.id" +
            " JOIN movie m ON s.movie_id = m.id" +
            " WHERE MONTH(b.date_create) = MONTH(CURDATE()) " +
            "    AND YEAR(b.date_create) = YEAR(CURDATE())" +
            " GROUP BY m" +
            " ORDER BY bill_ticket_count DESC" +
            " LIMIT 5;", nativeQuery = true)
    List<Object[]> listTop5Movie();

    String bill = ("SELECT \n" +
            "b.trading_code , \n" +
            "m.name  ,\n" +
            "c.name , \n" +
            "s.start_at  , \n" +
            "se.code , \n" +
            "f.name , \n" +
            "b.date_create  \n" +
            "FROM projectLinh.bill b \n" +
            "JOIN projectLinh.bill_ticket  bt ON b.id = bt.bill_id \n" +
            "JOIN projectLinh.ticket  t ON bt.ticket_id = t.id \n" +
            "JOIN projectLinh.seat  se ON se.id = t.seat_id\n" +
            "JOIN projectLinh.schedule  s ON t.schedule_id = s.id \n" +
            "JOIN projectLinh.movie  m ON s.movie_id = m.id \n" +
            "JOIN projectLinh.room  r ON s.room_id = r.id \n" +
            "JOIN projectLinh.cinema  c ON r.cinema_id = c.id \n" +
            "LEFT JOIN projectLinh.bill_food  bf ON b.id = bf.bill_id \n" +
            "LEFT JOIN projectLinh.food  f ON bf.food_id = f.id  \n" +
            "WHERE b.customer_id = :customerId  and b.status =1 ORDER BY  s.start_at  DESC");
    @Query(value = bill, nativeQuery = true)
    List<Object[]> findBillDetailsByCustomer(@Param("customerId") String customerId);
}
