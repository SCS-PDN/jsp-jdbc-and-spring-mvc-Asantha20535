package com.example.demo;

import com.example.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String validateLogin(@RequestParam String email,
                                @RequestParam String password,
                                HttpSession session,
                                Model model) {
        try {
            String sql = "SELECT * FROM students WHERE email = ? AND password = ?";
            Student student = jdbcTemplate.queryForObject(sql, new Object[]{email, password}, (rs, rowNum) -> {
                Student s = new Student();
                s.setStudentId(rs.getInt("student_id"));
                s.setName(rs.getString("name"));
                s.setEmail(rs.getString("email"));
                return s;
            });

            session.setAttribute("studentId", student.getStudentId());
            return "redirect:/courses";

        } catch (Exception e) {
            model.addAttribute("error", "Invalid email or password.");
            return "login";
        }
    }
}

