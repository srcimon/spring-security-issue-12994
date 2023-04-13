package io.github.srcimon.issue;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.stereotype.Component;

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
