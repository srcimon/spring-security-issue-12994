package io.github.srcimon.issue;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class ApiController {

    @GetMapping("/api/is-session-new")
    public String isNewSession(HttpServletRequest request) {
        boolean isNewSession = request.getSession(true).isNew();
        return String.format("is %s session", isNewSession ? "new" : "existing");
    }
}
