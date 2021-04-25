package com.meli.test.dnamutantanalyzer.controllers;

import com.meli.test.dnamutantanalyzer.exception.AnalysisException;
import com.meli.test.dnamutantanalyzer.model.DNARequest;
import com.meli.test.dnamutantanalyzer.model.Stat;
import com.meli.test.dnamutantanalyzer.services.DNAAnalyzerService;
import com.meli.test.dnamutantanalyzer.services.DNAStatsService;
import java.util.Arrays;
import jdk.jfr.ContentType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class DNAAnalyzerController {

  private final DNAAnalyzerService dnaMutantAnalyzerService;
  private final DNAStatsService dnaMutantStatsService;

  @GetMapping(value = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Stat> getStats() {
    try {
      return ResponseEntity.ok(dnaMutantStatsService.getAnalysisStats());
    } catch (Exception e) {
      log.error("Unable to analise the DNA stats");
      return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).header("Cause", e.getMessage())
          .build();
    }
  }

  @PostMapping(value = "/mutant", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Boolean> analiseDNA(@RequestBody final DNARequest dnaRequest) {
    try {
      return ResponseEntity.ok(dnaMutantAnalyzerService.isMutant(dnaRequest.getDna()));
    } catch (AnalysisException e) {
      log.error("Unable to analise the DNA sample {}", Arrays.asList(dnaRequest.getDna()));
      return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).header("Cause", e.getMessage())
          .build();
    }

  }
}
