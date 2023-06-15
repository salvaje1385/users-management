package com.users.config;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

public class JwtTokenFilter extends GenericFilterBean {

    private static final String BEARER = "Bearer";

    private UserDetailService userDetailService;

    public JwtTokenFilter(final UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        final String authorizationHeader =
                ((HttpServletRequest)request).getHeader("Authorization");
        getBearerToken(authorizationHeader)
                .ifPresent(token-> {
                    userDetailService.loadUserByJwtToken(token).ifPresent(userDetails -> {
                        SecurityContextHolder.getContext().setAuthentication(
                                new PreAuthenticatedAuthenticationToken(
                                        userDetails, "", userDetails.getAuthorities()));
            });
        });
        chain.doFilter(request, response);
    }

    private Optional<String> getBearerToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            return Optional.of(authorizationHeader.replace(BEARER, "").trim());
        }
        return Optional.empty();
    }

}
