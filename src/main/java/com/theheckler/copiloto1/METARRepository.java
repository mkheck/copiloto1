package com.theheckler.copiloto1;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface METARRepository extends CrudRepository<METAR, String> {
    Optional<METAR> findByRawContains(String icao);
}
