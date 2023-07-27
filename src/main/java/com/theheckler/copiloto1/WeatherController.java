package com.theheckler.copiloto1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/")
public class WeatherController {
    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @GetMapping
    public METAR getDefaultMetar() {
        //return retrieveMetar("KSTL");
        return service.getMetar("KSTL");
    }

    @GetMapping("/metar/{icao}")
    public METAR getMetar(@PathVariable String icao) {
        return service.getMetar(icao);
    }
}
