package org.smartinrubio.springboottransparentcache.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smartinrubio.springboottransparentcache.exception.CountryNotFoundException;
import org.smartinrubio.springboottransparentcache.service.CountryService;
import org.smartinrubio.springboottransparentcache.model.PaymentOption;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@RestController
@AllArgsConstructor
public class PaymentOptionsController {

    private final CountryService countryService;

    @Cacheable({"paymentOptions", "secondaryCache"}) // it will create two cache entries
    @GetMapping("/") // if no parameters are given it will cache an empty SimpleKey
    public List<String> getAllPaymentOptionsByRandomCountryId() throws InterruptedException {
        log.info("Payment Options from random country id will be cached.");
        Thread.sleep(1000L);
        Long countryId = (long) new Random().nextInt(4);
        return countryService
                .findById(countryId)
                .orElseThrow(() -> new CountryNotFoundException(countryId))
                .getPaymentOptions()
                .stream()
                .map(PaymentOption::getName)
                .collect(Collectors.toList());
    }

    @Cacheable("paymentOptions")
    @GetMapping("/{countryId}") // if only one parameter is given, it will be used as key
    public List<String> getPaymentOptionsByCountryId(@PathVariable("countryId") Long countryId) throws InterruptedException {
        log.info("Payment options for country {} will be cached.", countryId);
        Thread.sleep(1000L);
        return countryService
                .findById(countryId)
                .orElseThrow(() -> new CountryNotFoundException(countryId))
                .getPaymentOptions()
                .stream()
                .map(PaymentOption::getName)
                .collect(Collectors.toList());
    }

    @Cacheable("paymentOptions")
    @GetMapping("/{countryId}/{letter}") // it will cache SimpleKey with all parameters
    public List<String> getPaymentOptionsWithInitialLetterByCountryId(@PathVariable("countryId") Long countryId, @PathVariable("letter") String letter) throws InterruptedException {
        log.info("Payment options for country {} and initial letter \"{}\" will be cached.", countryId, letter);
        Thread.sleep(1000L);
        return countryService
                .findById(countryId)
                .orElseThrow(() -> new CountryNotFoundException(countryId))
                .getPaymentOptions()
                .stream()
                .map(PaymentOption::getName)
                .filter(name -> name.startsWith(letter))
                .collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "paymentOptions", key = "#endingLetter")
    @GetMapping("/{countryId}/{initialLetter}/{endingLetter}") // it will cache SimpleKey with all parameters
    public List<String> getPaymentOptionsWithEndingLetterByCountryId(
            @PathVariable("countryId") Long countryId,
            @PathVariable("initialLetter") String initialLetter,
            @PathVariable("endingLetter") String endingLetter) throws InterruptedException {
        log.info("Payment options for country {} will be cached.", countryId);
        Thread.sleep(1000L);
        return countryService
                .findById(countryId)
                .orElseThrow(() -> new CountryNotFoundException(countryId))
                .getPaymentOptions()
                .stream()
                .map(PaymentOption::getName)
                .filter(name -> name.startsWith(initialLetter))
                .filter(name -> name.endsWith(endingLetter))
                .collect(Collectors.toList());
    }


    @Cacheable(cacheNames = "paymentOptions", keyGenerator = "customKeyGenerator") // the custom key generator will create a key with all parameters
    @GetMapping("/{countryId}/{initialLetter}/{endingLetter}/v2") // it will cache SimpleKey with all parameters
    public List<String> getPaymentOptionsWithEndingLetterByCountryIdV2(
            @PathVariable("countryId") Long countryId,
            @PathVariable("initialLetter") String initialLetter,
            @PathVariable("endingLetter") String endingLetter) throws InterruptedException {
        log.info("Payment options for country {} will be cached: V2.", countryId);
        Thread.sleep(1000L);
        return countryService
                .findById(countryId)
                .orElseThrow(() -> new CountryNotFoundException(countryId))
                .getPaymentOptions()
                .stream()
                .map(PaymentOption::getName)
                .filter(name -> name.startsWith(initialLetter) && name.endsWith(endingLetter))
                .collect(Collectors.toList());
    }

    @CacheEvict(cacheNames = "paymentOptions", key = "#countryId")
    @GetMapping("/remove/{countryId}")
    public void removePaymentOptionsFromCacheByCountryId(@PathVariable("countryId") Long countryId) {
        log.info("Payment options with country id {} was removed.", countryId);
    }
}
