package lk.ijse.gdse68.pos_system_back_end.Filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@WebFilter(urlPatterns = "/*")
public class CORSFilter extends HttpFilter {

    private static final Logger LOGGER = Logger.getLogger(CORSFilter.class.getName());
    private static final List<String> ALLOWED_ORIGINS = Arrays.asList("http://localhost:5500", "http://127.0.0.1:5503");

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String origin = req.getHeader("Origin");
        LOGGER.info("CORSFilter invoked for request: " + req.getRequestURI());
        LOGGER.info("Origin: " + origin);

        // Allow specific origins
        if (origin != null && ALLOWED_ORIGINS.contains(origin)) {
            res.setHeader("Access-Control-Allow-Origin", origin);
            res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            res.setHeader("Access-Control-Allow-Headers", "Content-Type");
            res.setHeader("Access-Control-Expose-Headers", "Content-Type");
            LOGGER.info("CORS headers set.");
        }

        // Handle preflight requests
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            res.setStatus(HttpServletResponse.SC_OK);
            LOGGER.info("Preflight request handled.");
            return;
        }

        chain.doFilter(req, res);
    }
}