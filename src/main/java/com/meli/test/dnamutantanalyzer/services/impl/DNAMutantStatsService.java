package com.meli.test.dnamutantanalyzer.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.test.dnamutantanalyzer.gateway.persistence.entity.StatsEntity;
import com.meli.test.dnamutantanalyzer.gateway.persistence.repository.StatRepository;
import com.meli.test.dnamutantanalyzer.model.Stat;
import com.meli.test.dnamutantanalyzer.services.DNAStatsService;
import java.util.Iterator;
import java.util.List;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class DNAMutantStatsService implements DNAStatsService {

  private final StatRepository statRepository;

  @Override
  public Stat getAnalysisStats() {
    log.info("Calculating DNA stats");
    Iterator<StatsEntity> statsIterator = statRepository.findAll().iterator();
    int mutant = 0;
    int human = 0;
    while (statsIterator.hasNext()) {
      final StatsEntity statsEntity = statsIterator.next();
      if (statsEntity.isMutant()) {
        mutant++;
      } else {
        human++;
      }
    }
    return Stat.builder().countMutantDNA(mutant).countHumanDNA(human)
        .ratio(human == 0 ? 0 : mutant / human).build();
  }

  @Override
  @Transactional
  public void saveStat(final String dna, final boolean isMutant, final int length) {
    log.info("Saving {} with DNA {}", isMutant ? "Mutant" : "Human", dna);
    statRepository.save(StatsEntity.builder().id(dna.hashCode()).isMutant(isMutant).jsonDNA(dna).length(length).build());
  }


}
