package com.borshcheva.springTask.service;

import com.borshcheva.springTask.repository.CurrencyRepository;
import com.borshcheva.springTask.model.CurrencyPriceModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Service
public class CsvExportService {

    private final CurrencyRepository currencyRepository;

    public CsvExportService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public void writeCurrenciesToCsv(Writer writer) {

        List<CurrencyPriceModel> currencies = currencyRepository.findAllByMinMaxCurrencyPrice();
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            for (CurrencyPriceModel currencyPrice : currencies) {
                csvPrinter.printRecord(currencyPrice.getCurrencyFirst() + "/" + currencyPrice.getCurrencySecond(), currencyPrice.getMaxPrice(), currencyPrice.getMinPrice());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
