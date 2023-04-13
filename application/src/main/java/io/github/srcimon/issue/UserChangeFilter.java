package io.github.srcimon.issue;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class UserChangeFilter extends AbstractPreAuthenticatedProcessingFilter {

    public UserChangeFilter(AuthenticationManager  authenticationManager) {
        setAuthenticationManager(authenticationManager);
        setCheckForPrincipalChanges(true);
        setContinueFilterChainOnUnsuccessfulAuthentication(true);
        setInvalidateSessionOnPrincipalChange(true);
    }
    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new BasicAuthenticationConverter().convert(request);
        return authenticationToken == null ? null : authenticationToken.getPrincipal();
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return null;
    }
}
