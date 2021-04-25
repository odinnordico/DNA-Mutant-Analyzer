package com.meli.test.dnamutantanalyzer.services;

import com.meli.test.dnamutantanalyzer.model.Stat;

public interface DNAStatsService {

  Stat getAnalysisStats();

  void saveStat(String dna, boolean isMutant, int length);
}
