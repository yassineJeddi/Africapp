package ma.bservices.tgcc.authentification;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.faces.bean.ManagedProperty;

import javax.faces.context.FacesContext;
import ma.bservices.beans.Groupe;
import ma.bservices.beans.Permission;
import ma.bservices.beans.Utilisateur;
import ma.bservices.tgcc.service.Engin.UtilisateurService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

public class CustomAuthenticationProvider implements AuthenticationProvider, Serializable {

    @ManagedProperty(value = "#{utilisateurService}")
    private UtilisateurService utilisateurService;
    Utilisateur utilisateur = new Utilisateur();

    public CustomAuthenticationProvider() {
    }

    public UtilisateurService getUtilisateurService() {
        return utilisateurService;
    }

    public void setUtilisateurService(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        utilisateurService = ctx.getBean(UtilisateurService.class);
        utilisateur = utilisateurService.getUsersByLogin(username);

        if (utilisateur == null) {
            throw new UsernameNotFoundException("User not found");
        }

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            String storedPass = utilisateur.getPassword();

            //******* remplace password with hashtext********//
            if (!storedPass.equals(hashtext)) {
                throw new BadCredentialsException("Invalid password");
            }

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (Permission p1 : utilisateur.getPermissions()) {
        {   
            authorities.add(new SimpleGrantedAuthority(p1.getPermission()));
        }
        }
//        for (Groupe group : utilisateur.getGroupes()) {
//            for (Permission p : group.getPermissions()) {
//                authorities.add(new SimpleGrantedAuthority(p.getPermission()));
//            }
//        }

        Authentication customAuthentication = new CustomUserAuthentication(authorities, authentication);
        customAuthentication.setAuthenticated(true);

        return customAuthentication;

    }

    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
