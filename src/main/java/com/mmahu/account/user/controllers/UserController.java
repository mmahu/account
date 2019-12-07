package com.mmahu.account.user.controllers;

import com.mmahu.account.user.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    @GetMapping("/buyer")
    public User getBuyer(Principal principal) {
        return new User().setId(principal.getName()).setName("buyer smith");
    }

    @GetMapping("/seller")
    public User getSeller(Principal principal) {
        return new User().setId(principal.getName()).setName("seller bob");
    }

}
