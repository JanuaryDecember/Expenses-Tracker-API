package pl.januarydecember.expensestrackerapi.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    basic, premium, admin;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
