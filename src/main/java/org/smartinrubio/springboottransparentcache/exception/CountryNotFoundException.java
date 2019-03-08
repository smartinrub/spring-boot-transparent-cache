package org.smartinrubio.springboottransparentcache.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException(Long countryId) {
        super("Country with id " + countryId + " not found!");
    }

}
