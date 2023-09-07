package hello.springmvc.basic.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class MappingController {

    @RequestMapping("/hello-basic")
    public String helloBasic(){
        log.info("helloBasic");
        return "ok";
    }

    @GetMapping("/hello-get")
    public String helloGet(){
        log.info("helloGet");
        return "ok";
    }

    @PostMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String id){
        log.info("mappingPath userId={}", id);
        return "ok";
    }

}
