package book.manager.util;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hw
 * @date 2022-10-22
 * @dec 描述
 */
@WebFilter(filterName = "EncodingFilter", urlPatterns = {"/*"})
public class EncodingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ModifyParametersWrapper wrapper = new ModifyParametersWrapper(request);
        filterChain.doFilter(wrapper, response);
    }
}
