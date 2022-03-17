package com.borshcheva.springTask.controller;

import com.borshcheva.springTask.dto.CurrencyPriceDataDto;
import com.borshcheva.springTask.dto.CurrencyPriceDto;
import com.borshcheva.springTask.entity.CurrencyPrice;
import com.borshcheva.springTask.service.CsvExportService;
import com.borshcheva.springTask.service.CurrencyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class CurrencyController {
    private final CurrencyService currencyService;
    private final CsvExportService csvExportService;
    private final RestTemplate restTemplate;


    @PostMapping(value = "/last-price", produces = MediaType.APPLICATION_JSON_VALUE)
    public CurrencyPrice saveLastPrice(@Valid @RequestBody CurrencyPriceDto currencyPrice) throws JsonProcessingException {

        String response = this.restTemplate.getForObject(UriComponentsBuilder.fromHttpUrl("https://cex.io/api/last_price")
                .pathSegment(currencyPrice.getCurrencyFirst().name(), currencyPrice.getCurrencySecond().name()).build().toUri(), String.class);
        CurrencyPriceDataDto data = new ObjectMapper().readValue(response, CurrencyPriceDataDto.class);
        return currencyService.saveLastPrice(data);

    }

    @GetMapping("/cryptocurrencies/minprice")
    public CurrencyPriceDto getCurrencyWithMinPrice(@RequestParam(value = "name") String currency) {
        return this.currencyService.findCurrencyWithMinPrice(currency);
    }

    @GetMapping("/cryptocurrencies/maxprice")
    public CurrencyPriceDto getCurrencyWithMaxPrice(@RequestParam(value = "name") String currency) {
        return this.currencyService.findCurrencyWithMaxPrice(currency);
    }

    @GetMapping("/cryptocurrencies")
    public List<CurrencyPriceDto> getCurrencyWithMaxPrice(@RequestParam(value = "name") String currency,
                                                          @RequestParam(value = "page") int page,
                                                          @RequestParam(value = "size") int size) {
        return this.currencyService.findAllByCurrency(currency, page, size);

    }

    @GetMapping("/cryptocurrencies/csv")
    public void getCurrencyInCsv(HttpServletResponse servletResponse) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition", "attachment; filename=\"currencies.csv\"");
        this.csvExportService.writeCurrenciesToCsv(servletResponse.getWriter());
    }
}
