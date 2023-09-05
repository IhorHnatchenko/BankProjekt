package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class ApiController {

    @GetMapping("hello/client")
    public ResponseEntity<String> helloUser() {
        return ResponseEntity.ok("Hello client.");
    }

    @GetMapping("hello/admin")
    public ResponseEntity<String> helloAdmin() {
        SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok("Hello admin ");
    }
}
