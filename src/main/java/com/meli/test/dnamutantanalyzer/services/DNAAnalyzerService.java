package com.meli.test.dnamutantanalyzer.services;

/**
 * DNAAnalyzerService provides the capabilities to analyze DNA sequences
 */
public interface DNAAnalyzerService {

  /**
   * isMutant determine if the given DNA sequence belongs to a mutant or a human
   *
   * @param dna sequnce to be analyzed
   * @return {@code true} if the dna comes from a mutant, otherwise {@code false}
   */
  boolean isMutant(String[] dna);
}
