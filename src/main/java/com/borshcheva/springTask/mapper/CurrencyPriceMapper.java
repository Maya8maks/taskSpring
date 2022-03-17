package com.borshcheva.springTask.mapper;

import com.borshcheva.springTask.dto.CurrencyPriceDataDto;
import org.mapstruct.Mapper;
import com.borshcheva.springTask.entity.CurrencyPrice;
import org.mapstruct.Mapping;

@Mapper
public interface CurrencyPriceMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    CurrencyPrice map(CurrencyPriceDataDto currencyPriceDataDto);
}
