package com.meli.test.dnamutantanalyzer.controllers;

import com.meli.test.dnamutantanalyzer.exception.AnalysisException;
import com.meli.test.dnamutantanalyzer.model.DNARequest;
import com.meli.test.dnamutantanalyzer.model.Stat;
import com.meli.test.dnamutantanalyzer.services.DNAAnalyzerService;
import com.meli.test.dnamutantanalyzer.services.DNAStatsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import java.util.Arrays;
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
@Api(
    value = "dna-analyzer",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class DNAAnalyzerController {

  private final DNAAnalyzerService dnaMutantAnalyzerService;
  private final DNAStatsService dnaMutantStatsService;

  /**
   * getAnalysisStats calculate the stats from all the analysis performed before
   *
   * @return the stats
   */
  @GetMapping(value = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "calculate the stats from all the analysis performed before", response = Stat.class)
  @ApiResponses(value = {

      @ApiResponse(code = 200, message = "Successfully retrieve the stats"),
      @ApiResponse(code = 403, message = "An error occurs while retrieving the stats",
          responseHeaders = {
              @ResponseHeader(name = "Cause", description = "The cause of the error")
          }
      ),
  })
  public ResponseEntity<Stat> getStats() {
    try {
      return ResponseEntity.ok(dnaMutantStatsService.getAnalysisStats());
    } catch (Exception e) {
      log.error("Unable to analise the DNA stats");
      return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).header("Cause", e.getMessage())
          .build();
    }
  }

  /**
   * analiseDNA determine if the given DNA sequence belongs to a mutant or a human
   *
   * @param dna sequnce to be analyzed
   * @return {@code true} if the dna comes from a mutant, otherwise {@code false}
   */
  @PostMapping(value = "/mutant", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "determine if the given DNA sequence belongs to a mutant or a human", response = Boolean.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully analyzed the DNA sequence"),
      @ApiResponse(code = 403, message = "An error occurs while analyzing the DNA sequence",
          responseHeaders = {
              @ResponseHeader(name = "Cause", description = "The cause of the error")
          })
  })
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
