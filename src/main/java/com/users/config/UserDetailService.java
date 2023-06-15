package com.users.config;

import static org.springframework.security.core.userdetails.User.withUsername;

import com.users.exception.UserException;
import com.users.model.User;
import com.users.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    private static final String ROLE = "ROLE_STANDARD";

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findByEmail(email);
        User user = userOpt.orElseThrow(
                () -> new UserException("The User does not exist"));

        return withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(ROLE)
                .build();
    }

    public Optional<UserDetails> loadUserByJwtToken(final String jwtToken) {
        if (tokenProvider.isValidToken(jwtToken)) {
            return Optional.of(
                    withUsername(tokenProvider.getUsername(jwtToken))
                            .authorities(new SimpleGrantedAuthority("STANDARD_USER"))
                            .password("")
                            .build());
        }
        return Optional.empty();
    }

}
