package ma.bservices.tgcc.authentification;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class CustomUserAuthentication implements Authentication {

    private static final long serialVersionUID = -3091441742758356129L;

    private boolean authenticated;

    private final Collection<GrantedAuthority> grantedAuthoritys;
    private final Authentication authentication;

    public CustomUserAuthentication(Collection<GrantedAuthority> role, Authentication authentication) {
        this.grantedAuthoritys = role;
        this.authentication = authentication;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return grantedAuthoritys;
    }

    @Override
    public Object getCredentials() {
        return authentication.getCredentials();
    }

    @Override
    public Object getDetails() {
        return authentication.getDetails();
    }

    @Override
    public Object getPrincipal() {
        return authentication.getPrincipal();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        this.authenticated = authenticated;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

}
