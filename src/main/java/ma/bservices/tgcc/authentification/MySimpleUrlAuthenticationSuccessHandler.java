/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.authentification;

import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author a.wattah
 */
public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    protected Log logger = LogFactory.getLog(this.getClass());

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication) throws IOException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /**
     * Builds the target URL according to the logic defined in the main class
     * Javadoc.
     *
     * @param authentication
     * @return
     */
    protected String determineTargetUrl(Authentication authentication) {
        boolean is_mensuel = false;
        boolean is_admin = false;
        boolean is_admin_chantier = false;
        boolean is_rh = false;
        boolean is_achat = false;
        boolean is_citerne = false;
        boolean isAdminTrans = false;
        boolean isAdminEcart = false;
        boolean isDocteur = false;
        boolean isAt = false;
        boolean isDemandeInterne = false;
        authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getPrincipal().toString();

        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());

        logger.info("AUTHENTIC USER LOGIN : " + username);

        logger.info("authentification :" + authentication.getAuthorities().toString());
        /*for (GrantedAuthority grantedAuth : authentication.getAuthorities()) {
            System.out.println("GRANTED AUTHORITY: " + grantedAuth.getAuthority());
        }*/
        OUTER:
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            switch (grantedAuthority.getAuthority()) {

                case "pointage_declaratif":
                    is_mensuel = true;
                    break OUTER;
                case "admin":
                    is_admin = true;
                    break OUTER;
                case "citerne":
                    is_citerne = true;
                    break OUTER;
                case "pointage_des_engins":
                    is_admin_chantier = true;
                    break OUTER;
                case "rh":
                    is_rh = true;
                    break OUTER;
                case "admin transferts":
                    isAdminTrans = true;
                    break OUTER;
                case "ecart stock":
                    isAdminEcart = true;
                    break OUTER;
                case "Dossier Medical":
                    isDocteur = true;
                    break OUTER;
                case "At":
                    isAt = true;
                    break OUTER;
                case "créer une nouvelle demande d approvisionnement":
                    is_achat = true;
                    break;
                case "consultation liste demande":
                    isDemandeInterne = true;
                    break OUTER;
            }
        }
        if (is_mensuel) {
            return "/mensuel/pointage.xhtml";

        } else if (is_admin) {
            return "/mensuel/mensuel.xhtml";

        } else if (is_admin_chantier) {

            return "/engin/pointage.xhtml";
        } else if (is_rh) {
            return "/mensuel/pointage.xhtml";
        } else if (is_achat) {
            return "/achat/nouvelledemandeappro.xhtml";
        } else if (is_citerne) {
            return "/engin/Citerne.xhtml";
        } else if (isAdminTrans) {
            return "/stock/traitementTransferts.xhtml";
        } else if (isAdminEcart) {
            return "/stock/ecartStock.xhtml";
        }  else if (isDocteur) {
            return "/dossiersMedicaux/dossiersMedicaux.xhtml";
        }else if (isAt) {
            return "/at/at.xhtml";
        }else if (isDemandeInterne) {
            return "/depot/dmdInterne.xhtml";
        } else {
            return "/evol/salaries.xhtml";
        }

    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
}
