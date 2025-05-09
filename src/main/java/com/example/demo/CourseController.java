package com.example.demo;

import com.example.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller
public class CourseController{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/courses")
    public String showCourses(Model model) {
        String sql = "SELECT * FROM courses";
        List<Course> courses = jdbcTemplate.query(sql, (rs, rowNum) -> mapCourse(rs));
        model.addAttribute("courses", courses);
        return "courses";
    }

    @PostMapping("/register/{courseId}")
    public String registerCourse(@PathVariable("courseId") int courseId, HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("studentId");

        if (studentId == null) {
            return "redirect:/login";
        }

        
        String checkSql = "SELECT COUNT(*) FROM registrations WHERE student_id = ? AND course_id = ?";
        int count = jdbcTemplate.queryForObject(checkSql, new Object[]{studentId, courseId}, Integer.class);
        if (count == 0) {
            String sql = "INSERT INTO registrations (student_id, course_id, date) VALUES (?, ?, CURDATE())";
            jdbcTemplate.update(sql, studentId, courseId);
        }

        return "success";
    }

    private Course mapCourse(ResultSet rs) throws SQLException {
        Course course = new Course();
        course.setCourseId(rs.getInt("course_id"));
        course.setName(rs.getString("name"));
        course.setInstructor(rs.getString("instructor"));
        course.setCredits(rs.getInt("credits"));
        return course;
    }
}



