package com.borshcheva.springTask.service;

import com.borshcheva.springTask.repository.CurrencyRepository;
import com.borshcheva.springTask.dto.CurrencyPriceDataDto;
import com.borshcheva.springTask.dto.CurrencyPriceDto;
import com.borshcheva.springTask.entity.CurrencyPrice;
import com.borshcheva.springTask.entity.CurrencyType;
import com.borshcheva.springTask.mapper.CurrencyPriceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.borshcheva.springTask.entity.CurrencyType.BTC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurrencyServiceTest {
   private CurrencyService currencyService;

    @Mock
    private CurrencyRepository currencyRepository;
    @Mock
    private CurrencyPriceMapper currencyPriceMapper;

    @BeforeEach
    void init() {

        currencyService = new CurrencyService(currencyRepository, currencyPriceMapper);

    }

    @Test
    void testCreatedCurrencyPriceDto() {
        CurrencyPrice currencyPrice = new CurrencyPrice();
        currencyPrice.setCurrencyFirst(BTC);
        currencyPrice.setCurrencySecond(CurrencyType.USDT);
        currencyPrice.setPrice(100.00);
        CurrencyPriceDto currencyPriceDto = new CurrencyPriceDto();
        currencyPriceDto.setPrice(100.00);
        currencyPriceDto.setCurrencyFirst(BTC);
        currencyPriceDto.setCurrencySecond(CurrencyType.USDT);

        CurrencyPriceDto result = currencyService.createCurrencyPriceDto(currencyPrice);
        assertEquals(currencyPriceDto.getPrice(), result.getPrice());
        assertEquals(currencyPriceDto.getCurrencyFirst(), result.getCurrencyFirst());
        assertEquals(currencyPriceDto.getCurrencySecond(), result.getCurrencySecond());
    }

    @Test
    void testFindCurrencyWithMinPrice() {
        CurrencyPrice currencyPrice = new CurrencyPrice();
        currencyPrice.setCurrencyFirst(BTC);
        currencyPrice.setCurrencySecond(CurrencyType.USDT);
        currencyPrice.setPrice(50.00);

        CurrencyPriceDto currencyPriceDto = new CurrencyPriceDto();
        currencyPriceDto.setPrice(100.00);
        currencyPriceDto.setCurrencyFirst(BTC);
        currencyPriceDto.setCurrencySecond(CurrencyType.USDT);
        CurrencyPriceDto currencyPriceDto2 = new CurrencyPriceDto();
        currencyPriceDto2.setPrice(50.00);
        currencyPriceDto2.setCurrencyFirst(BTC);
        currencyPriceDto2.setCurrencySecond(CurrencyType.USDT);

        CurrencyType currency = BTC;

        when(currencyRepository.findFirstByCurrencyFirstOrderByPriceAsc(currency)).thenReturn(currencyPrice);
        CurrencyPriceDto result = currencyService.findCurrencyWithMinPrice(currency.name());
        assertEquals(50.00, result.getPrice());
        assertEquals(currencyPriceDto2.getCurrencyFirst(), result.getCurrencyFirst());
        assertEquals(currencyPriceDto2.getCurrencySecond(), result.getCurrencySecond());
    }


    @Test
    void testFindCurrencyWithMaxPrice() {
        CurrencyPrice currencyPrice = new CurrencyPrice();
        currencyPrice.setCurrencyFirst(BTC);
        currencyPrice.setCurrencySecond(CurrencyType.USDT);
        currencyPrice.setPrice(100.00);

        CurrencyPriceDto currencyPriceDto1 = new CurrencyPriceDto();
        currencyPriceDto1.setPrice(100.00);
        currencyPriceDto1.setCurrencyFirst(BTC);
        currencyPriceDto1.setCurrencySecond(CurrencyType.USDT);
        CurrencyPriceDto currencyPriceDto2 = new CurrencyPriceDto();
        currencyPriceDto2.setPrice(50.00);
        currencyPriceDto2.setCurrencyFirst(BTC);
        currencyPriceDto2.setCurrencySecond(CurrencyType.USDT);

        CurrencyType currency = BTC;

        when(currencyRepository.findFirstByCurrencyFirstOrderByPriceDesc(currency)).thenReturn(currencyPrice);
        CurrencyPriceDto result = currencyService.findCurrencyWithMaxPrice(currency.name());
        assertEquals(100.00, result.getPrice());
        assertEquals(currencyPriceDto2.getCurrencyFirst(), result.getCurrencyFirst());
        assertEquals(currencyPriceDto2.getCurrencySecond(), result.getCurrencySecond());
    }

    @Test
    void testFindAllByCurrency() {
        CurrencyPrice currencyPrice = new CurrencyPrice();
        currencyPrice.setCurrencyFirst(BTC);
        currencyPrice.setCurrencySecond(CurrencyType.USDT);
        currencyPrice.setPrice(100.00);

        CurrencyPrice currencyPrice2 = new CurrencyPrice();
        currencyPrice2.setCurrencyFirst(BTC);
        currencyPrice2.setCurrencySecond(CurrencyType.USDT);
        currencyPrice2.setPrice(101.00);

        CurrencyPriceDto currencyPriceDto1 = new CurrencyPriceDto();
        currencyPriceDto1.setPrice(100.00);
        currencyPriceDto1.setCurrencyFirst(BTC);
        currencyPriceDto1.setCurrencySecond(CurrencyType.USDT);

        CurrencyPriceDto currencyPriceDto2 = new CurrencyPriceDto();
        currencyPriceDto2.setPrice(101.00);
        currencyPriceDto2.setCurrencyFirst(BTC);
        currencyPriceDto2.setCurrencySecond(CurrencyType.USDT);

        Page<CurrencyPrice> page = new PageImpl<>(List.of(currencyPrice, currencyPrice2));

        when(currencyRepository.findByCurrencyFirst(eq(BTC), any(Pageable.class))).thenReturn(page);
        var result = currencyService.findAllByCurrency("BTC", 0, 2);
        assertEquals(2, result.size());
        assertEquals(100.00, result.get(0).getPrice());
        assertEquals(101.00, result.get(1).getPrice());

    }

    @Test
    void testSaveLastPrice(){

        CurrencyPriceDataDto currencyPriceDataDto1 = new CurrencyPriceDataDto();
        currencyPriceDataDto1.setPrice(17663.00);
        currencyPriceDataDto1.setCurrencyFirst(BTC);
        currencyPriceDataDto1.setCurrencySecond(CurrencyType.USDT);

        CurrencyPrice currencyPrice = new CurrencyPrice();
        currencyPrice.setCurrencyFirst(BTC);
        currencyPrice.setCurrencySecond(CurrencyType.USDT);
        currencyPrice.setPrice(17663.00);
        when(currencyRepository.save(any())).thenReturn(currencyPrice);

        var result = currencyService.saveLastPrice(currencyPriceDataDto1);
        assertEquals(17663.00, result.getPrice());
    }

}
