package org.armitage.inc.AAInfo.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.armitage.inc.AAInfo.dao.UserRepository;
import org.armitage.inc.AAInfo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    User user = userRepository.getByLogin(username);
	    if(user == null){
	        throw new UsernameNotFoundException(username);
	    }
		UserDetails details = new UserDetails(){
			private static final long serialVersionUID = 1L;

			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				List<GrantedAuthority> roles = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
				return roles;
			}

			@Override
			public String getPassword() {
				// TODO Auto-generated method stub
			    System.out.println(passwordEncoder.encode("admin"));
				return user.getPassword();
			}

			@Override
			public String getUsername() {
				// TODO Auto-generated method stub
				return user.getUserName();
			}

			@Override
			public boolean isAccountNonExpired() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public boolean isAccountNonLocked() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public boolean isCredentialsNonExpired() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public boolean isEnabled() {
				// TODO Auto-generated method stub
				return true;
			}
			
		};
		System.out.println(" user "+username+" given");
		return details;
	}

}
