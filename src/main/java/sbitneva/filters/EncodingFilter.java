package sbitneva.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Encoding filter.
 */
@WebFilter(filterName = "EncodingFilter",
        urlPatterns = {"/*"}
)

public class EncodingFilter implements Filter {

    private static Logger log = Logger.getLogger(EncodingFilter.class.getName());

    @Override
    public void init(final FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(
            final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain
    ) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        filterChain.doFilter(servletRequest, servletResponse);
        log.debug("encoding filter work");
    }

    @Override
    public void destroy() {
    }
}
