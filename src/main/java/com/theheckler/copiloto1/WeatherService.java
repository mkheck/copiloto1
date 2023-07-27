package com.theheckler.copiloto1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class WeatherService {
    @Value("${avwx.token:NoValidToken}")
    private String token;
    private final RestClient client = RestClient.create("https://avwx.rest/api");

    private final METARRepository repository;

    public WeatherService(METARRepository repository) {
        this.repository = repository;
    }

    public METAR getMetar(String icao) {
        return repository.findByRawContains(icao).orElseGet(() -> retrieveMetar(icao));
    }
    //.getForObject("https://avwx.rest/api/metar/" + id + "?token=" + token, METAR.class);

    private METAR retrieveMetar(String icao) {
        var metar = client.get()
                .uri("/metar/{icao}?token={token}", icao, token)
                .retrieve()
                .body(METAR.class);

        System.out.println(">>> Retrieved METAR for " + icao + " via remote API call.");

        // MH: Revisit and add legit exception handling here ;)
        return (metar != null) ? repository.save(metar) : null;
    }
}
