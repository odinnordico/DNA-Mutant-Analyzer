package com.meli.test.dnamutantanalyzer.services;

import com.meli.test.dnamutantanalyzer.common.constants.DNABase;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@FunctionalInterface
public interface MutantSequenceAnalyzerService {

  CompletableFuture<Integer> countMutantSequences(List<List<String>> linesOfLines, DNABase base);

}
