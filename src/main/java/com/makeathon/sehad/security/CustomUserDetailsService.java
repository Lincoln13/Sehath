package com.makeathon.sehad.security;

import com.makeathon.sehad.dao.AuthDao;
import com.makeathon.sehad.models.db.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthDao authDao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Authentication auth = authDao.findByEmail(userName);
        if (auth == null || auth.getDisabled()) {
            throw new UsernameNotFoundException("Not found: User - " + userName);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Date date = new Date();
        auth.getAuthorityList()
                .stream()
                .filter(a -> (a.getStartDate()!= null &&
                        (a.getStartDate().equals(date) || a.getStartDate().before(date))) &&
                        (a.getEndDate() == null || (a.getEndDate() != null && a.getEndDate().after(date)))
                )
                .forEach(a -> grantedAuthorities.add(new SimpleGrantedAuthority(a.getRoleName())));

        return new User(auth.getEmail(), auth.getPassword(), grantedAuthorities);
//        return new User("test", "test", new ArrayList<>());
    }
}
