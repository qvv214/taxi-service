package ru.digitalleague.core.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.digitalleague.core.model.UserAccountEntity;
import ru.digitalleague.core.service.UserAccountService;

import java.security.Principal;


@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final UserAccountService userAccountService;

    @GetMapping("home")
    public UserAccountEntity home(@RequestParam String login) {
        return userAccountService.test(login);
    }

    @GetMapping("auth")
    public String auth(Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "auth " + principal.getName();
    }
}
