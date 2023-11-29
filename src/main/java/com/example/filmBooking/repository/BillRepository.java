package com.example.filmBooking.repository;

import com.example.filmBooking.model.Bill;
import com.example.filmBooking.model.Schedule;
import com.example.filmBooking.model.dto.BillDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.filmBooking.model.dto.DtoBill;
import com.example.filmBooking.model.dto.DtoBillList;

import java.time.LocalDate;
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

    @Query("SELECT COUNT(b) FROM Bill b WHERE b.status = 0 AND b.customer.id = :customerId")
    String countSoldTicket(String customerId);

    @Query(value = "select sum(total_money) from bill b join" +
            "(SELECT DISTINCT bill_id, c.id, c.name FROM bill_ticket bt " +
            "JOIN ticket t ON t.id = bt.ticket_id" +
            " JOIN schedule s ON t.schedule_id = s.id" +
            " JOIN room r ON s.room_id = r.id" +
            " JOIN cinema c ON c.id = r.cinema_id) d on d.bill_id = b.id" +
            " WHERE " +
            " d.id=?1 and" +
            "    b.date_create >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
            "GROUP BY DATE(b.date_create), d.id = ?1" +
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
            "              b.trading_code,\n" +
            "               m.name,  m.image,\n" +
            "               c.name , \n" +
            "               s.start_at , \n" +
            "                GROUP_CONCAT(DISTINCT se.code) as seat_codes,\n" +
            "    COUNT(DISTINCT se.code) as seat_code_count,\n" +
            "      GROUP_CONCAT(DISTINCT CONCAT_WS('  ', f.name, CONCAT('(', bf.quantity, ')'), FORMAT(bf.total_money, 0, 'vi_VN')),'') AS formatted_values,\n" +
            "                b.date_create,\n" +
            "                bt.total_money,\n" +
            "\t\t\t\n" +
            "                r.name\n" +
            "            FROM \n" +
            "                projectLinh.bill b \n" +
            "            JOIN\n" +
            "                projectLinh.customer cu on cu.id = b.customer_id\n" +
            "            JOIN \n" +
            "                projectLinh.bill_ticket bt ON b.id = bt.bill_id \n" +
            "            JOIN \n" +
            "                projectLinh.ticket t ON bt.ticket_id = t.id \n" +
            "            JOIN \n" +
            "                projectLinh.seat se ON se.id = t.seat_id \n" +
            "            JOIN \n" +
            "               projectLinh.schedule s ON t.schedule_id = s.id\n" +
            "\t\tJOIN \n" +
            "               projectLinh.movie m ON s.movie_id = m.id \n" +
            "            JOIN \n" +
            "                projectLinh.room r ON s.room_id = r.id \n" +
            "            JOIN \n" +
            "                projectLinh.cinema c ON r.cinema_id = c.id \n" +
            "            LEFT JOIN \n" +
            "               projectLinh.bill_food bf ON b.id = bf.bill_id \n" +
            "            LEFT JOIN \n" +
            "                projectLinh.food f ON bf.food_id = f.id \n" +
            "            WHERE\n" +
            "                cu.id = :customerId\n" +
            "                AND b.status = 1\n" +
            "            GROUP BY \n" +
            "                b.trading_code, m.name, m.image, c.name, s.start_at,  b.date_create, bt.total_money,  r.name\n" +
            "            ORDER BY \n" +
            "                s.start_at DESC;");

    @Query(value = bill, nativeQuery = true)
    List<Object[]> findBillDetailsByCustomer(@Param("customerId") String customerId);

    String billCho = ("SELECT \n" +
            "              b.trading_code,\n" +
            "               m.name,  m.image,\n" +
            "               c.name , \n" +
            "               s.start_at , \n" +
            "                GROUP_CONCAT(DISTINCT se.code) as seat_codes,\n" +
            "    COUNT(DISTINCT se.code) as seat_code_count,\n" +
            "        GROUP_CONCAT(DISTINCT CONCAT_WS('  ', f.name, CONCAT('(', bf.quantity, ')'), FORMAT(bf.total_money, 0, 'vi_VN'))) ,\n" +
            "                b.date_create,\n" +
            "                bt.total_money,\n" +
            "                r.name,\n" +
            "                b.status\n" +
            "            FROM \n" +
            "                projectLinh.bill b \n" +
            "            JOIN\n" +
            "                projectLinh.customer cu on cu.id = b.customer_id\n" +
            "            JOIN \n" +
            "                projectLinh.bill_ticket bt ON b.id = bt.bill_id \n" +
            "            JOIN \n" +
            "                projectLinh.ticket t ON bt.ticket_id = t.id \n" +
            "            JOIN \n" +
            "                projectLinh.seat se ON se.id = t.seat_id \n" +
            "            JOIN \n" +
            "               projectLinh.schedule s ON t.schedule_id = s.id\n" +
            "\t\tJOIN \n" +
            "               projectLinh.movie m ON s.movie_id = m.id \n" +
            "            JOIN \n" +
            "                projectLinh.room r ON s.room_id = r.id \n" +
            "            JOIN \n" +
            "                projectLinh.cinema c ON r.cinema_id = c.id \n" +
            "            LEFT JOIN \n" +
            "               projectLinh.bill_food bf ON b.id = bf.bill_id \n" +
            "            LEFT JOIN \n" +
            "                projectLinh.food f ON bf.food_id = f.id \n" +
            "            WHERE\n" +
            "                cu.id = :customerId\n" +
            "                AND b.status = 0\n" +
            "            GROUP BY \n" +
            "                b.trading_code, m.name, m.image, c.name, s.start_at,  b.date_create, bt.total_money,  r.name,b.status\n" +
            "            ORDER BY \n" +
            "                s.start_at DESC;");

    @Query(value = billCho, nativeQuery = true)
    List<Object[]> findBillDetailsByCustomerCho(@Param("customerId") String customerId);

    String billDetail = ("SELECT \n" +
            "              b.trading_code,\n" +
            "               m.name,  m.image,\n" +
            "               c.name , \n" +
            "               s.start_at , \n" +
            "                GROUP_CONCAT(DISTINCT se.code) as seat_codes,\n" +
            "    COUNT(DISTINCT se.code) as seat_code_count,\n" +
            "      GROUP_CONCAT(DISTINCT CONCAT_WS('  ', f.name, CONCAT('(', bf.quantity, ')'), FORMAT(bf.total_money, 0, 'vi_VN')),'') AS formatted_values,\n" +
            "                b.date_create,\n" +
            "                bt.total_money,\n" +
            "                r.name,b.total_money, " +
            "            cu.name, cu.phone_number, b.status\n" +
            "            FROM \n" +
            "                projectLinh.bill b \n" +
            "            JOIN\n" +
            "                projectLinh.customer cu on cu.id = b.customer_id\n" +
            "            JOIN \n" +
            "                projectLinh.bill_ticket bt ON b.id = bt.bill_id \n" +
            "            JOIN \n" +
            "                projectLinh.ticket t ON bt.ticket_id = t.id \n" +
            "            JOIN \n" +
            "                projectLinh.seat se ON se.id = t.seat_id \n" +
            "            JOIN \n" +
            "               projectLinh.schedule s ON t.schedule_id = s.id\n" +
            "\t\tJOIN \n" +
            "               projectLinh.movie m ON s.movie_id = m.id \n" +
            "            JOIN \n" +
            "                projectLinh.room r ON s.room_id = r.id \n" +
            "            JOIN \n" +
            "                projectLinh.cinema c ON r.cinema_id = c.id \n" +
            "            LEFT JOIN \n" +
            "               projectLinh.bill_food bf ON b.id = bf.bill_id \n" +
            "            LEFT JOIN \n" +
            "                projectLinh.food f ON bf.food_id = f.id \n" +
            "            WHERE\n" +
            "                b.id = :idBill\n" +
            "            GROUP BY \n" +
            "                b.trading_code, m.name, m.image, c.name, s.start_at,  b.date_create, bt.total_money,  r.name,b.total_money, cu.name, cu.phone_number, b.status\n" +
            "            ORDER BY \n" +
            "                s.start_at DESC;");

    @Query(value = billDetail, nativeQuery = true)
    List<Object[]> findBillDetailId(@Param("idBill") String idBill);


    @Query("FROM Bill s WHERE " +
            " ((?1 is NULL) OR (s.tradingCode like ?1)) " +
            "AND ((?2 is NULL) OR (date(s.dateCreate) = Date(?2))) " +
            " AND ((?3 is NULL) OR (s.status like ?3))"+
            "ORDER BY s.dateCreate ASC"
    )
    List<Bill> findBillsByTradingCodeAndDate(String tradingCode, LocalDate dateCreate, Integer status);

    @Query("FROM Bill s WHERE " +
            " s.status like 0" +
            "AND ((?1 is NULL) OR (s.tradingCode like ?1)) " +
            "AND ((?2 is NULL) OR (date(s.dateCreate) = Date(?2))) " +
            "ORDER BY s.dateCreate ASC"
    )
    List<Bill> findBillsByTradingCodeAndDateCho(String tradingCode, LocalDate dateCreate);
}
