package com.jb.spotifybackend.setlistfmcontroller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class SetlistApiController {


    @GetMapping(value = "search/{q}")
    public String getSetlists(@PathVariable("q") String q) {

    return "test";
    }

}
