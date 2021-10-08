package ru.digitalleague.core.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.digitalleague.core.model.UserAccountEntity;
import ru.digitalleague.core.service.UserAccountService;

import java.security.Principal;
import java.util.List;

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

    /**
     *
     * запрос для root
     */
    @DeleteMapping ("deleteUsers/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        userAccountService.deleteByPrimaryKey(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    /**
     *
     * запрос для user
     */
    @GetMapping ("showAllUsers")
    public ResponseEntity<List> showAllUsers() {
        return new ResponseEntity<List>(userAccountService.showAllUsers(), HttpStatus.OK);
    }
}
