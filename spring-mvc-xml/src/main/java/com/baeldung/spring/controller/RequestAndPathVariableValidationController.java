package com.baeldung.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.*;

@RestController
@RequestMapping("/public/api/v1")
@Validated
public class RequestAndPathVariableValidationController {
    private final Logger logger = LoggerFactory.getLogger(RequestAndPathVariableValidationController.class);

    @GetMapping("/name-for-day")
    public String getNameOfDayByNumberRequestParam(@RequestParam @Min(1) @Max(7) Integer dayOfWeek) {
        logger.info("getNameOfDayByNumberRequestParam()");
        return dayOfWeek + "";
    }

    @GetMapping("/name-for-day/{dayOfWeek}")
    public String getNameOfDayByPathVariable(@PathVariable("dayOfWeek") @Min(1) @Max(7) Integer dayOfWeek) {
        return dayOfWeek + "";
    }

    @GetMapping("/valid-name")
    public void validStringRequestParam(@RequestParam @NotBlank @Size(max = 10) @Pattern(regexp = "^[A-Z][a-zA-Z0-9]+$") String name) {

    }

    @GetMapping("/liveness")
    // 跨域测试
    // 果然协议，域名和端口是必须一致的，域名是必须完全一致，没有说一级和二级匹配就行这样的说法
    @CrossOrigin(origins = "https://www.baidu.com")
    public String healthCheck() {
        logger.info("healthCheck()");
        return "Okay";
    }

    @GetMapping("/null-test")
    public String nullTest(@RequestParam @Min(1) @Max(7) Integer dayOfWeek) {
        logger.info("nullTest()");
        // 以下浏览器显示"null"，所以null对象显示的时候是"null"是不错的，虽然会跟"null"字符串冲突
        // 所以业务系统设计时要小心处理真"null"字符串，可以用"NA"代表null，这样就不冲突了
         return null + "";

        // 以下浏览器显示空白，用Postman看返回HTTP code 200，但是response是空白
        // 所以还是可以的，系统不会因为这个NPE直接崩了，还是比较健壮的
//        return null;
    }

}
