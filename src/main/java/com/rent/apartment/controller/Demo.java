package com.rent.apartment.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class Demo {


    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public String demo() {
        return "hello";
    }
}
