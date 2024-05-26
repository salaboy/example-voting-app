package com.salaboy.worker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkerController {

    @GetMapping("/")
    public String ok(){
        return "OK";
    }

}
