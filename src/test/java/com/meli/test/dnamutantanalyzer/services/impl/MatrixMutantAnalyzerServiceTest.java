package com.meli.test.dnamutantanalyzer.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.meli.test.dnamutantanalyzer.gateway.persistence.repository.StatRepository;
import com.meli.test.dnamutantanalyzer.model.CharMatrix;
import com.meli.test.dnamutantanalyzer.services.DNAStatsService;
import com.meli.test.dnamutantanalyzer.services.MatrixProcessor;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MatrixMutantAnalyzerServiceTest {

  private static final String SHOULD_BE_MUTANT = "Should be Mutant";
  private static final String SHOULD_NOT_BE_MUTANT = "Should NOT be Mutant";
  @Mock
  private StatRepository statRepository;
  @Spy
  private CharMatrixProcessorService charMatrixProcessorService = new CharMatrixProcessorService();
  @Mock
  private DNAStatsService dnaMutantStatsService;

  @InjectMocks
  private DNAMutantAnalyzerService dnaMutantAnalyzerService;

  @BeforeEach
  public void setUp() {
    when(statRepository.findById(anyInt())).thenReturn(Optional.empty());
  }

  @Test
  public void isMutantByVertical() {
    assertTrue(dnaMutantAnalyzerService
            .isMutant(new String[]{"AACTCA", "ATCCGT", "ACCGGC", "AGCAAG", "AATTAA", "ATGCTT"}),
        SHOULD_BE_MUTANT);
  }

  @Test
  public void isMutantByHorizontal() {
    assertTrue(dnaMutantAnalyzerService
            .isMutant(new String[]{"AAAAAA", "ATCGAT", "CCCCTG", "TCGATC", "CGGAAT", "ATCGAT"}),
        SHOULD_BE_MUTANT);
  }

  @Test//
  public void isMutantByDiagonalRight() {
    assertTrue(dnaMutantAnalyzerService
            .isMutant(new String[]{"AATGGG", "TACTAT", "TCACTG", "CCGATT", "CGGATT", "TTCGAC"}),
        SHOULD_BE_MUTANT);
  }

  @Test
  public void isMutantByDiagonalLeft() {
    assertTrue(dnaMutantAnalyzerService
            .isMutant(new String[]{"GGGTAA", "TATCAT", "GTCACT", "TTAGCC", "TTAGGC", "CAGCTT"}),
        SHOULD_BE_MUTANT);
  }

  @Test
  public void isMutantByCross() {
    assertTrue(dnaMutantAnalyzerService
            .isMutant(new String[]{"ATCGAT", "CGATCG", "TCAAAA", "TAGATA", "CGAACG", "TAGATA"}),
        SHOULD_BE_MUTANT);
  }

  @Test
  public void isMutantByX() {
    assertTrue(dnaMutantAnalyzerService
            .isMutant(new String[]{"ATCGAT", "CGATCG", "TCAAAA", "TAGATA", "CGATAG", "TAGCTA"}),
        SHOULD_BE_MUTANT);
  }

  @Test
  public void isMutantByVerticalDiagonal() {
    assertTrue(dnaMutantAnalyzerService
            .isMutant(new String[]{"ATCGAT", "CGATCG", "TCGAAC", "TAGATA", "CGAACG", "TAGATA"}),
        SHOULD_BE_MUTANT);
  }

  @Test
  public void isMutantFalse() {
    assertFalse(dnaMutantAnalyzerService
            .isMutant(new String[]{"ATCGAT", "GCTAGC", "TCGATC", "AGCTAG", "TCGATC", "AGCTAG"}),
        SHOULD_NOT_BE_MUTANT);
  }

}