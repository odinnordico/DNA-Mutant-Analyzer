package com.meli.test.dnamutantanalyzer.services;

import com.meli.test.dnamutantanalyzer.model.Stat;

/**
 * DNAStatsService is the service providing most of the interactions with stat repository
 */
public interface DNAStatsService {

  /**
   * getAnalysisStats calculate the stats from all the analysis performed before
   *
   * @return the stats
   */
  Stat getAnalysisStats();

  /**
   * saveStat generates the proper DNA Analysis entity to be saved
   *
   * @param dna      the dna to be saved
   * @param isMutant the result of the analysis
   * @param length   the length (NxN -> N) of the matrix representation
   */
  void saveStat(String dna, boolean isMutant, int length);
}
