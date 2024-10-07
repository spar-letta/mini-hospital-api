package auth.application.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        return ((List<String>)jwt.getClaims().get("authorities")).stream()
//                .map(roleName -> "ROLE_" + roleName)
                .map(permissionName -> permissionName)// prefix to map to a Spring Security "role"
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
