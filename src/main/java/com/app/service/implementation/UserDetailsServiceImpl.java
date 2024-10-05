package com.app.service.implementation;

import com.app.persistence.entity.UserEntity;
import com.app.persistence.repository.IUserCrudRepository;
import com.app.presentation.advice.BadCredentialsException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserCrudRepository userCrudRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = this.userCrudRepository.findUserByUsername(username).orElseThrow(() -> {
            throw new BadCredentialsException("User with username " + username + " does not exist!");
        });

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();


        userEntity.getRoleEntities()
                .stream()
                .flatMap(roleEntity -> {
                    grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_".concat(roleEntity.getRoleEnum().name())));
                    return roleEntity.getPermissionEntities().stream();
                })
                .forEach(permissionEntity -> grantedAuthorities.add(new SimpleGrantedAuthority(permissionEntity.getName())));

        return new User(
                username,
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNoExpired(),
                userEntity.isCredentialNoExpired(),
                userEntity.isAccountNoLocked(),
                grantedAuthorities
        );

    }
}
