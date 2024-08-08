package doctorBookingApp.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping

@Api(value = "TestController", description = "REST API для приветствия")
public class HalloController {

    @GetMapping("/hallo")
    @ApiOperation(value = "Скажи привет", notes = "Возвращает строку 'hallo'")
    public String sayHallo() {
        return "hallo";
    }
}