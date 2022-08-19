package com.mysite.sbb.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));

        try {
            userRepository.save(user);
        }catch (DataIntegrityViolationException e){
            SiteUser siteUser = userRepository.findByEmail(email).orElse(null);
            if (username.equals(siteUser.getUsername())) {
                System.out.println("응?");
                throw  new SignupUserNameDuplicated();
            }
            else if (email.equals(siteUser.getEmail())) {
                System.out.println("이메일 중복");
                throw  new SignupUserEmailDuplicated();
            }
        }
        return user;
    }

    public SiteUser findByEmail(String email){
        SiteUser siteUser = userRepository.findByEmail(email).orElse(null);
        return siteUser;
    }


}