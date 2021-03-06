package com.example.rest.controllers;

import com.example.rest.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ServerWebExchange;

@Slf4j
@Controller
public class UserController {

    private final ApiService apiService;

    public UserController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping({"", "/", "/index"})
    public String index() {
        return "index";
    }

    @PostMapping("/users/")
    public String formPost(Model model, ServerWebExchange serverWebExchange) {
        MultiValueMap<String, String> map = serverWebExchange.getFormData().block();
        Integer limit = Integer.valueOf(map.get("limit").get(0));

        log.debug("Limit value: " + limit);
        if (limit == 0) {
            log.debug("Setting limit to 10");
            limit = 10;
        }
        model.addAttribute("users", apiService.getUsers(limit));
        return "userlist";
    }

    @PostMapping("/users-reactive")
    public String formPostReactive(Model model, ServerWebExchange serverWebExchange) {
        model.addAttribute("users", apiService
                .getUsers(serverWebExchange.getFormData()
                        .map(data -> Integer.valueOf(String.valueOf(data.get("limit"))))));
        return "userlist";
    }
}
