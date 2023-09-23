package com.example.filmBooking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

    @Column(name = "code")
    private String code;

    @NotEmpty(message = "Họ tên không được để trống!!")
    @Min(value = 3 , message = "Tên đăng ký tối thiểu trên 3 ký tự!! ")
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "rank_id")
    private Rank rank;

    @NotNull
    @Size(max = 10, min = 10, message = "Số điện thoại di động phải có 10 chữ số")
    @Pattern(regexp = "/((0)+([0-9]{9})\\b)/g", message = "Số điện thoại di động không hợp lệ!!")
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ!!")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "Mật khẩu >= 8 ký tự")
    @Column(name = "password")
    private String password;

    @Column(name = "point")
    private Integer point;
}
