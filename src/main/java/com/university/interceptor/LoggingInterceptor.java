public class LoggingInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String uri = request.getRequestURI();
        System.out.println("Accessed: " + uri + " at " + new Date());
        return true;
    }
}
