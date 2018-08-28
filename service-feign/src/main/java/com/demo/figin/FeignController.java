package com.demo.figin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {

    @Autowired
    FeignService feignService;

    @RequestMapping(method = RequestMethod.GET, value = "/hi")
    public String sayHi(@RequestParam String name){
         return feignService.sayHiFromClientOne(name);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/my")
    public String sayHi(){
        return "调用Feign自己的服务";
    }
}
