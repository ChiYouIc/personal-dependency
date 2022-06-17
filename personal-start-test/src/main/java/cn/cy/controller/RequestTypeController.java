package cn.cy.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @Author: 友
 * @Date: 2022/6/16 18:02
 * @Description:
 */
@RestController
@RequestMapping("/re")
public class RequestTypeController {

    @GetMapping("/get")
    public void get() {

    }

    @PostMapping("/post")
    public void post() {
    }

    @PutMapping("/put")
    public void put() {
    }

    @GetMapping("/pathVariable/{id}")
    public void path(@PathVariable("id") String id) {

    }

    @DeleteMapping("/delete")
    public void delete() {

    }

}
