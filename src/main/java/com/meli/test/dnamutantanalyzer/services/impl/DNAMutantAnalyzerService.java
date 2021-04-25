package com.meli.test.dnamutantanalyzer.services.impl;

import com.meli.test.dnamutantanalyzer.common.constants.DNABase;
import com.meli.test.dnamutantanalyzer.exception.AnalysisException;
import com.meli.test.dnamutantanalyzer.gateway.persistence.entity.StatsEntity;
import com.meli.test.dnamutantanalyzer.gateway.persistence.repository.StatRepository;
import com.meli.test.dnamutantanalyzer.model.CharMatrix;
import com.meli.test.dnamutantanalyzer.services.DNAAnalyzerService;
import com.meli.test.dnamutantanalyzer.services.DNAStatsService;
import com.meli.test.dnamutantanalyzer.services.MatrixProcessor;
import com.meli.test.dnamutantanalyzer.services.MutantSequenceAnalyzerService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class DNAMutantAnalyzerService implements DNAAnalyzerService {

  private final StatRepository statRepository;
  private final MatrixProcessor charMatrixProcessorService;
  private final DNAStatsService dnaMutantStatsService;
  private static final MutantSequenceAnalyzerService msa = (linesOfLines, base) ->
      CompletableFuture.completedFuture(linesOfLines.stream().flatMap(List::stream).parallel()
          .mapToInt(s -> StringUtils.countMatches(s, base.getMutantRepresentation())).sum());

  @Override
  public boolean isMutant(final String... dna) {
    log.info("Checking DNA {}", Arrays.asList(dna));
    if (Arrays.stream(dna).anyMatch(d -> d.trim().length() != dna.length)) {
      throw new AnalysisException("Invalid dna sequence");
    }

    final var concatDNA = Arrays.stream(dna).map(String::trim).collect(Collectors.joining());
    Optional<StatsEntity> stats = statRepository.findById(concatDNA.hashCode());
    return stats.map(StatsEntity::isMutant).orElseGet(() -> analyzeDNA(concatDNA, dna.length));
  }

  private boolean analyzeDNA(final String dnaSequence, final int size) {
    log.info("DNA is not present in existing records, checking DNA");

    try {
      final var charMatrix = CharMatrix.builder().data(dnaSequence.toCharArray())
          .length(size).build();

      CompletableFuture<List<String>> verticals = charMatrixProcessorService
          .getVerticals(charMatrix);
      CompletableFuture<List<String>> horizontals = charMatrixProcessorService
          .getHorizontals(charMatrix);
      CompletableFuture<List<String>> diagonalRight = charMatrixProcessorService
          .getDiagonalsRight(charMatrix);
      CompletableFuture<List<String>> diagonalLeft = charMatrixProcessorService
          .getDiagonalsLeft(charMatrix);

      CompletableFuture.allOf(verticals, horizontals, diagonalRight, diagonalLeft).join();

      var lines = Collections.synchronizedList(
          Arrays
              .asList(verticals.get(), horizontals.get(), diagonalRight.get(), diagonalLeft.get()));

      final boolean isMutant = getBasesCount(lines) > 1;
      dnaMutantStatsService.saveStat(dnaSequence, isMutant, size);
      return isMutant;

    } catch (ExecutionException | InterruptedException e) {
      log.error("Unable to get lines from matrix");
      throw new AnalysisException("An error occurs while getting lines from matrix", e);
    }

  }

  private int getBasesCount(List<List<String>> lines) {
    try {
      CompletableFuture<Integer> a = msa.countMutantSequences(lines, DNABase.A);
      CompletableFuture<Integer> t = msa.countMutantSequences(lines, DNABase.T);
      CompletableFuture<Integer> c = msa.countMutantSequences(lines, DNABase.C);
      CompletableFuture<Integer> g = msa.countMutantSequences(lines, DNABase.G);

      CompletableFuture.allOf(a, t, c, g).join();

      return (a.get() + t.get() + c.get() + g.get());
    } catch (ExecutionException | InterruptedException e) {
      log.error("Unable to count bases in lines");
      throw new AnalysisException("An error occurs while counting bases in lines", e);
    }
  }
}
