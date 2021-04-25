package com.meli.test.dnamutantanalyzer.services;

import com.meli.test.dnamutantanalyzer.common.constants.DNABase;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * MutantSequenceAnalyzerService provide the methods to analyze the DNA represented in lines of the
 * table representation
 */
@FunctionalInterface
public interface MutantSequenceAnalyzerService {

  /**
   * countMutantSequences counts how many mutant sequences are present in the different lines of the
   * DNA, the count is based on the given DNA Base
   *
   * @param linesOfLines set of lines (horizontal, vertical and diagonal) representing all the
   *                     permutation of lines in a DNA sequence
   * @param base         the DNA base to count in the line permutations
   * @return the number of mutant sub-sequences in the DNA lines
   */
  CompletableFuture<Integer> countMutantSequences(List<List<String>> linesOfLines, DNABase base);

}
