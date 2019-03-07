package org.smartinrubio.springboottransparentcache;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@AllArgsConstructor
public class PaymentOptionsController {

    private final CountryService countryService;

    @Cacheable("paymentOptions")
    @GetMapping("/{countryID}")
    public List<String> getPaymentOptions(@PathVariable("countryID") Long countryID) throws InterruptedException {
        log.info("Payment options for country {} will be cached.", countryID);
        Thread.sleep(1000L);
        return countryService
                .findById(countryID)
                .orElseThrow(CountryNotFoundException::new)
                .getPaymentOptions()
                .stream()
                .map(PaymentOption::getName)
                .collect(Collectors.toList());
    }
}
