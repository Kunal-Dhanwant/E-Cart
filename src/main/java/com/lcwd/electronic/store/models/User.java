package com.lcwd.electronic.store.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter


@Entity
@Table(name="users")
public class User implements UserDetails{
	
	@Id
	private String userId;
	
	@Column(name="user_name")
	private String name;
	
	@Column(name="user_email",unique = true,nullable = false)
	private String email;
	@Column(name = "user_password",length = 100,nullable = false)
	private String password;
	
	private String gender;
	@Column(length = 1000)
	 private String about;
	
	@Column(name="user_image_name")
	private String userImage;
	
	
	@OneToOne(mappedBy = "user",cascade = CascadeType.REMOVE)
	private Cart cart;
	 
	  @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
	    private List<Order> orders=new ArrayList<>();

	  
	  @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	  private Set<Role> roles = new HashSet<>();
	  
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		
		Set<SimpleGrantedAuthority> authorities= this.roles.stream().map(role->new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toSet());
		
		return authorities;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
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

}
