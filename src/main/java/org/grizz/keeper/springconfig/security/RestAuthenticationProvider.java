package org.grizz.keeper.springconfig.security;

import lombok.extern.slf4j.Slf4j;
import org.grizz.keeper.model.User;
import org.grizz.keeper.service.UserService;
import org.grizz.keeper.service.exception.user.UserAuthenticationException;
import org.grizz.keeper.utils.HashingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Grizz on 2015-07-26.
 */
@Slf4j
@Component
public class RestAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = (String) authentication.getCredentials();

        User user = userService.getByLogin(login);
        checkUsername(user);
        checkPassword(user, password);
        List<GrantedAuthority> authorities = getAuthorities(user);

        log.info("Logging in: {}, {}", login, Arrays.toString(authorities.toArray()));

        return new UsernamePasswordAuthenticationToken(login, password, authorities);
    }

    private void checkUsername(User user) throws AuthenticationException {
        if (user == null) throw new UserAuthenticationException("Bad login or password");
    }

    private void checkPassword(User user, String password) {
        if (!HashingUtils.check(password, user.getPasswordHash()))
            throw new UserAuthenticationException("Bad login or password");
    }

    private List<GrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass)
                && aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
