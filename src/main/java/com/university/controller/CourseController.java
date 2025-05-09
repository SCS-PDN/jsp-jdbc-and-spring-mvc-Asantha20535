@Controller
public class CourseController {

    @Autowired
    private CourseDAO courseDAO;

    @GetMapping("/courses")
    public String listCourses(Model model) {
        List<Course> courses = courseDAO.getAllCourses();
        model.addAttribute("courses", courses);
        return "courses";
    }

    @PostMapping("/register/{courseId}")
    public String registerCourse(@PathVariable int courseId, HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        if (student != null) {
            courseDAO.registerStudent(courseId, student.getStudentId());
            return "success";
        }
        return "redirect:/login";
    }
}
