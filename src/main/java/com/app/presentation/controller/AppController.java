package com.app.presentation.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/app")
public class AppController {

    @GetMapping
    public String read(){
        return "READ!";
    }

    @PostMapping
    public String create(){
        return "CREATE!";
    }

    @PutMapping
    public String update(){
        return "UPDATE!";
    }
    @DeleteMapping
    public String delete(){
        return "DELETE!";
    }
}
