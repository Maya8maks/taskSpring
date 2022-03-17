package com.borshcheva.springTask.dto;

import com.borshcheva.springTask.entity.CurrencyType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrencyPriceDataDto {
    @JsonProperty("curr1")
    private CurrencyType currencyFirst;
    @JsonProperty("curr2")
    private CurrencyType currencySecond;
    @JsonProperty("lprice")
    private Double price;
}
