package org.smartinrubio.springboottransparentcache.service;


import org.smartinrubio.springboottransparentcache.model.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryService extends CrudRepository<Country, Long> {
}
