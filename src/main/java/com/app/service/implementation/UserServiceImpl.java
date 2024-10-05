package com.app.service.implementation;

import com.app.persistence.entity.RoleEntity;
import com.app.persistence.entity.RoleEnum;
import com.app.persistence.entity.UserEntity;
import com.app.persistence.repository.IRoleCrudRepository;
import com.app.persistence.repository.IUserCrudRepository;
import com.app.presentation.advice.BadCredentialsException;
import com.app.presentation.dto.AuthRequestLogInDTO;
import com.app.presentation.dto.AuthRequestSignUpDTO;
import com.app.presentation.dto.AuthResponseDTO;
import com.app.service.interfaces.IUserService;
import com.app.util.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserCrudRepository userCrudRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final IRoleCrudRepository roleCrudRepository;

    @Override
    public AuthResponseDTO logIn(AuthRequestLogInDTO authRequestLogInDTO) {
        Authentication authentication = this.authenticate(authRequestLogInDTO.username(), authRequestLogInDTO.password());
        String jwt = this.jwtUtils.createToken(authentication);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        return new AuthResponseDTO(authRequestLogInDTO.username(), "User logged successfully", jwt, true);
    }

    @Override
    public AuthResponseDTO signUp(AuthRequestSignUpDTO authRequestSignUpDTO) {

        //valida si ya existe dicho usuario
        if (this.userCrudRepository.findUserByUsername(authRequestSignUpDTO.username()).isPresent()){
            throw new BadCredentialsException("User with username " + authRequestSignUpDTO.username() + " already exist!");
        }
        //obtencion y verificacion de los roles suministrados
        List<RoleEnum> roleList = authRequestSignUpDTO.authRolesRequestDto().rolesListName()
                .stream()
                .map(RoleEnum::valueOf)
                .toList();

        List<RoleEntity> roleEntityList = this.roleCrudRepository.getRolesEntitiesByRoleEnumIn(roleList);
        System.out.println("LLEGO");
        if (roleEntityList.isEmpty()) {
            throw new BadCredentialsException("There are not authorities!");
        }
        String encodedPassword = this.passwordEncoder.encode(authRequestSignUpDTO.password());
        UserEntity newUserEntity = UserEntity.builder()
                .roleEntities(roleEntityList)
                .username(authRequestSignUpDTO.username())
                .password(encodedPassword)
                .accountNoLocked(true)
                .isEnabled(true)
                .credentialNoExpired(true)
                .accountNoExpired(true)
                .build();

        this.userCrudRepository.save(newUserEntity);
        List<GrantedAuthority> authorityList = new ArrayList<>();
        roleEntityList.stream()
                .flatMap(roleEntity -> {
                    authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(roleEntity.getRoleEnum().name())));
                    return roleEntity.getPermissionEntities().stream();
                }).forEach(permissionEntity -> {
                    authorityList.add(new SimpleGrantedAuthority(permissionEntity.getName()));
                });
        Authentication authentication = new UsernamePasswordAuthenticationToken(authRequestSignUpDTO.username(),encodedPassword,authorityList);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        String jwt = this.jwtUtils.createToken(authentication);
        return new AuthResponseDTO(authRequestSignUpDTO.username(),"User created and logged successfully",jwt,true);
    }

    public Authentication authenticate(String username, String password) {
        UserDetails user = this.userDetailsService.loadUserByUsername(username);
        if (user == null) {
            throw new BadCredentialsException("User with username " + username + " does not exist!");
        }
        if (!this.passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("The password is incorrect!");
        }
        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}
