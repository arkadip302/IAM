package com.tux.iam.service;

import com.tux.iam.dto.AuthenticationRequestDTO;
import com.tux.iam.dto.AuthenticationResponseDTO;
import com.tux.iam.dto.RegisterRequestDTO;
import com.tux.iam.entity.Role;
import com.tux.iam.entity.User;
import com.tux.iam.exception.CustomServiceException;
import com.tux.iam.exception.DataNotFoundException;
import com.tux.iam.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private static  final String SECURITY_KEY = "99B7B81AAA99EC2F71B14D654CC9ChJBJUFUU6FVGJJCJCJCJGs";

    public String register(RegisterRequestDTO request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getAuthorities()==null && !(authentication.getAuthorities().stream().findFirst().get().getAuthority().equals("SUPER_ADMIN") ||
                authentication.getAuthorities().stream().findFirst().get().getAuthority().equals("BDO"))){
            throw new CustomServiceException("You Don't Have Access To Create User");
        }else if (!ObjectUtils.isEmpty(repository.findByEmail(request.getEmail())) || !ObjectUtils.isEmpty(repository.findByName(request.getUserName()))){
            throw  new DataNotFoundException("User Already Exists !!!");
        }

        var user = User.builder()
                .name(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf("USER"))
                .build();

        var savedUser = repository.save(user);
        if(savedUser == null){
            throw  new DataNotFoundException("Not Able To Create User");
        }
        return "Account Created !!!";
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponseDTO.builder()
                .accessToken(jwtToken)
                .build();
    }

    public boolean validate(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(SECURITY_KEY).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            return false;
        }


    }
}