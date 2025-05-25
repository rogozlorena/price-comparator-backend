package com.pricecomparator.pricecomparator.service;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.pricecomparator.pricecomparator.model.Price;
import com.pricecomparator.pricecomparator.model.Product;
import com.pricecomparator.pricecomparator.repository.PriceRepository;
import com.pricecomparator.pricecomparator.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CSVLoaderService {
    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;

    public void loadCSV(String fileName, String store, LocalDate date) {
        InputStream is = getClass().getClassLoader().getResourceAsStream("sample_data/" + fileName);
        if (is == null) {
            throw new RuntimeException("File not found: " + fileName);
        }
        try (CSVReader reader = new CSVReaderBuilder(
                new InputStreamReader(getClass().getClassLoader().getResourceAsStream("sample_data/" + fileName)))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .withSkipLines(1) // skip header
                .build()) {

            String[] line;
            while ((line = reader.readNext()) != null) {
                String productId = line[0];
                String name = line[1];
                String category = line[2];
                String brand = line[3];
                double quantity = Double.parseDouble(line[4]);
                String unit = line[5];
                double priceValue = Double.parseDouble(line[6]);
                String currency = line[7];

                Product product = productRepository.findByProductId(productId).orElse(null);
                if (product == null) {
                    product = new Product(null, productId, name, category, brand, quantity, unit, currency);
                    productRepository.save(product);
                }

                Price price = new Price(product, store, date, priceValue);
                priceRepository.save(price);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}