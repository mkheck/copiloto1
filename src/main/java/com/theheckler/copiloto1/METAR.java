package com.theheckler.copiloto1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public record METAR(@Id String id, String raw, String flight_rules) {}
//public record METAR(String raw, Time time, String flight_rules) {}
