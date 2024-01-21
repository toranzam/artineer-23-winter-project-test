package com.artineer.artineer23winterproject.service;

import com.artineer.artineer23winterproject.auth.UserAccount;
import com.artineer.artineer23winterproject.entity.Account;
import com.artineer.artineer23winterproject.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.TypeRegistration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if(account == null){
            throw new UsernameNotFoundException(username + "에 해당하는 유저정보가 존재하지 않습니다.");
        }

        return new UserAccount(account);
//        return new User(account.getUsername(), account.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
