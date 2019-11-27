package com.mmahu.account.user.controllers;

import com.mmahu.account.user.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/buyer")
    public User getBuyer() {
        return new User().setName("buyer smith");
    }

    @GetMapping("/seller")
    public User getSeller() {
        return new User().setName("seller bob");
    }

}
