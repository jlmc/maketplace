package org.xine.marketplace.frontend.views.security;

import java.security.Principal;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * The Class SecurityBean.
 */
@Named
@RequestScoped
public class SecurityBean {

    // TODO:: missing SecurityBean provider

    /**
     * Gets the username.
     * @return the username
     */
    public String getUsername() {
        String username = null;

        final SystemUser systemUser = getSessionUser();
        if (systemUser != null) {
            username = systemUser.getUser().getUsername();
        }
        return username;
    }

    @SuppressWarnings("static-method")
    private SystemUser getSessionUser() {
        final Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        final UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) principal;

        if (auth != null && auth.getPrincipal() != null) {
            return (SystemUser) auth.getPrincipal();
        }

        return null;
    }

}
