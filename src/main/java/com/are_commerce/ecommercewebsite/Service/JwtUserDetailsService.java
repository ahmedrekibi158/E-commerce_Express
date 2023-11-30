package com.are_commerce.ecommercewebsite.Service;

import com.are_commerce.ecommercewebsite.Model.DBUser;
import com.are_commerce.ecommercewebsite.Repository.DBUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class JwtUserDetailsService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);

	private final DBUserRepository userRepository;

	public JwtUserDetailsService(DBUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		logger.info("loadUserByUsername execution started.");
		System.out.println("load.. username: "+username);
		try {
			DBUser user = userRepository.findByUsername(username);
			System.out.println("user: " + user);
			logger.debug("Task in progress...");
			System.out.println("before return user");
            return new User(user.getUsername(),user.getPassword(),getGrantedAuthorities(user.getRole().name()));
		}
		catch(Exception e) {
			throw new UsernameNotFoundException("User not found with username "+username);
		}
	}
	private List<GrantedAuthority> getGrantedAuthorities(String role) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		return authorities;
	}

}
/*
@Service
public class JwtUserDetailsService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("javainuse".equals(username)) {
			return new User("javainuse", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
					new ArrayList<>());

		} else {			throw new UsernameNotFoundException("User not found with username: " + username);
		}	}}
 */