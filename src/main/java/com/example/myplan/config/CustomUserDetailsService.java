package com.example.myplan.config;

import com.example.myplan.entity.Authority;
import com.example.myplan.entity.User;
import com.example.myplan.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CustomUserDetailsService implements UserDetailsService {  //认证

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1. 查询：数据库/三方/内存/读取配置/jdbc
        Optional<User> userDetailsOptional = userRepository.findByName(username); //
        User user = userDetailsOptional.orElseThrow(() -> new UsernameNotFoundException("User not exists."));

        //2. 权限转化
        //List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN,ROLE_NORMAL");
        List<SimpleGrantedAuthority> auths = user.getAuthorities().stream()
                .map(Authority::getAuthority)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        //3. 将查询的信息封装到到UserDetails并返回
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getName())
                .password(user.getPassword())
                .authorities(auths)
                .build();
    }

}
