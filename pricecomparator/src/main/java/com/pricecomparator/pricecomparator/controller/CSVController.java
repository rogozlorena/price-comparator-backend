package com.pricecomparator.pricecomparator.controller;

import com.pricecomparator.pricecomparator.service.CSVLoaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class CSVController {

    private final CSVLoaderService loaderService;

    @PostMapping("/load-csv")
    public ResponseEntity<?> loadCSV(@RequestParam String filename, @RequestParam String store, @RequestParam String date) {
        loaderService.loadCSV(filename, store, LocalDate.parse(date));
        return ResponseEntity.ok("CSV loaded");
    }
}

