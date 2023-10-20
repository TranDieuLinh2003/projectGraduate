package com.example.filmBooking.interceptor;

import com.example.filmBooking.model.Customer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String requestURI = request.getRequestURI();

        // kiểm tra xác thực:
        boolean isAuthenticated = checkAuthentication(session);
        if (!isAuthenticated) {
            // Chuyển hướng đến trang đăng nhập nếu chưa xác thực
            response.sendRedirect("/login");
            return false;
        }


        // Kiểm tra phân quyền cho user
        if (requestURI.startsWith("/filmbooking/trangchu")) {
            boolean isUser = checkUserRole(session);
            if (!isUser) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN); // Trả về mã lỗi 403 nếu không phải user
                return false;
            }
        }

        // Tiếp tục xử lý request nếu đã xác thực và có quyền truy cập
        return true;
    }

    private boolean checkAuthentication(HttpSession session) {
        // trả về true khi tồn tại account
        return session.getAttribute("account") != null;
    }


    private boolean checkUserRole(HttpSession session) {
        // Kiểm tra vai trò user (ví dụ: kiểm tra thông tin người dùng trong session, database, ...)
        // Trả về true nếu có vai trò user, ngược lại trả về false
        Customer customer = (Customer) session.getAttribute("account");
        return customer != null;
    }

}

