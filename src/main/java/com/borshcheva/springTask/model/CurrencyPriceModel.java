package com.borshcheva.springTask.model;

public interface CurrencyPriceModel {
    String getCurrencyFirst();
    String getCurrencySecond();
    Double getMaxPrice();
    Double getMinPrice();
}
