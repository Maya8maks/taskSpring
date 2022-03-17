package com.borshcheva.springTask.dto;

import com.borshcheva.springTask.entity.CurrencyType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CurrencyPriceDto {
    private Long id;
    @NotNull
    @NotBlank
    private CurrencyType currencyFirst;
    @NotNull
    @NotBlank
    private CurrencyType currencySecond;
    private Double price;
    private Date createdAt;
    private Double maxPrice;
    private Double minPrice;

}
