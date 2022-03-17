package com.borshcheva.springTask.repository;

import com.borshcheva.springTask.entity.CurrencyPrice;
import com.borshcheva.springTask.entity.CurrencyType;
import com.borshcheva.springTask.model.CurrencyPriceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository extends PagingAndSortingRepository<CurrencyPrice, Long> {

    CurrencyPrice findFirstByCurrencyFirstOrderByPriceAsc(CurrencyType currency);

    CurrencyPrice findFirstByCurrencyFirstOrderByPriceDesc(CurrencyType currency);

    List<CurrencyPrice> findAll();

    Page<CurrencyPrice> findByCurrencyFirst(CurrencyType currency, Pageable requestedPage);


    @Query(value = "SELECT cp.currency_first as currencyFirst, cp.currency_second as currencySecond, MAX(cp.price) as maxPrice, " +
            "MIN(cp.price) as minPrice FROM currency_price as cp " +
            "GROUP BY cp.currency_first, cp.currency_second", nativeQuery = true)
    List<CurrencyPriceModel> findAllByMinMaxCurrencyPrice();
}
