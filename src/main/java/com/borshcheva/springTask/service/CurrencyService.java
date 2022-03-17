package com.borshcheva.springTask.service;

import com.borshcheva.springTask.repository.CurrencyRepository;
import com.borshcheva.springTask.dto.CurrencyPriceDataDto;
import com.borshcheva.springTask.dto.CurrencyPriceDto;
import com.borshcheva.springTask.entity.CurrencyPrice;
import com.borshcheva.springTask.entity.CurrencyType;
import com.borshcheva.springTask.mapper.CurrencyPriceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyPriceMapper currencyPriceMapper;


    public CurrencyPrice saveLastPrice(CurrencyPriceDataDto response) {

        CurrencyPrice currencyPrice = currencyPriceMapper.map(response);
        return currencyRepository.save(currencyPrice);
    }

    public CurrencyPriceDto findCurrencyWithMinPrice(String currency) {
        CurrencyPrice currencyPrice = this.currencyRepository.findFirstByCurrencyFirstOrderByPriceAsc(CurrencyType.valueOf(currency));
        return createCurrencyPriceDto(currencyPrice);
    }

    public CurrencyPriceDto createCurrencyPriceDto(CurrencyPrice currencyPrice) {
        CurrencyPriceDto currencyPriceDto = new CurrencyPriceDto();
        currencyPriceDto.setId(currencyPrice.getId());
        currencyPriceDto.setPrice(currencyPrice.getPrice());
        currencyPriceDto.setCurrencyFirst(currencyPrice.getCurrencyFirst());
        currencyPriceDto.setCurrencySecond(currencyPrice.getCurrencySecond());
        currencyPriceDto.setCreatedAt(currencyPrice.getCreatedAt());
        return currencyPriceDto;
    }

    public CurrencyPriceDto findCurrencyWithMaxPrice(String currency) {
        CurrencyPrice currencyPrice = this.currencyRepository.findFirstByCurrencyFirstOrderByPriceDesc(CurrencyType.valueOf(currency));
        return createCurrencyPriceDto(currencyPrice);
    }

    public List<CurrencyPriceDto> findAllByCurrency(String currency, int page, int size) {
        Pageable requestedPage = PageRequest.of(page, size);
        Page<CurrencyPrice> currencies = this.currencyRepository.findByCurrencyFirst(CurrencyType.valueOf(currency), requestedPage);
        return currencies.toList().stream().map(elem -> createCurrencyPriceDto(elem)).collect(Collectors.toList());

    }
}
