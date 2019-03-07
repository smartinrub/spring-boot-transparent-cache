package org.smartinrubio.springboottransparentcache;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such country")
public class CountryNotFoundException extends RuntimeException {
}
