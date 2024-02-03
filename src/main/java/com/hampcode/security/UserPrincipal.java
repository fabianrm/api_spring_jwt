package com.hampcode.security;


import com.hampcode.model.User;
import com.hampcode.model.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    //User
    private Long id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    //UserProfile
    private String firstName;
    private String lastName;
    private String dni;

    public static UserPrincipal create(User user) {
        //convertimos List<Role> to List<GrantedAuthority>
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name())
                ).collect(Collectors.toList());

        UserProfile profile = user.getUserProfile();

        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities,

                profile.getFirstName(),
                profile.getLastName(),
                profile.getDni()
        );
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
