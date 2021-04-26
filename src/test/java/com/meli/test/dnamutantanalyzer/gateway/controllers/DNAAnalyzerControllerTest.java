package com.meli.test.dnamutantanalyzer.gateway.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.meli.test.dnamutantanalyzer.gateway.persistence.entity.StatsEntity;
import com.meli.test.dnamutantanalyzer.gateway.persistence.repository.StatRepository;
import com.meli.test.dnamutantanalyzer.model.DNARequest;
import com.meli.test.dnamutantanalyzer.model.Stat;
import com.meli.test.dnamutantanalyzer.services.DNAAnalyzerService;
import com.meli.test.dnamutantanalyzer.services.DNAStatsService;
import com.meli.test.dnamutantanalyzer.services.MatrixProcessor;
import com.meli.test.dnamutantanalyzer.services.impl.CharMatrixProcessorService;
import com.meli.test.dnamutantanalyzer.services.impl.DNAMutantAnalyzerService;
import com.meli.test.dnamutantanalyzer.services.impl.DNAMutantStatsService;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class DNAAnalyzerControllerTest {

  @Mock
  private StatRepository statRepository;
  @Spy
  private MatrixProcessor charMatrixProcessorService;
  @Spy
  private DNAStatsService dnaMutantStatsService;
  @Spy
  private DNAAnalyzerService dnaMutantAnalyzerService;
  private DNAAnalyzerController dnaAnalyzerController;


  @BeforeEach
  void setUp() {
    charMatrixProcessorService = new CharMatrixProcessorService();
    dnaMutantStatsService = new DNAMutantStatsService(statRepository);
    dnaMutantAnalyzerService = new DNAMutantAnalyzerService(statRepository,
        charMatrixProcessorService, dnaMutantStatsService);

    dnaAnalyzerController = new DNAAnalyzerController(dnaMutantAnalyzerService,
        dnaMutantStatsService);
  }

  @Test
  void testGetStats() {
    when(statRepository.findAll()).thenReturn(Arrays
        .asList(
            StatsEntity.builder().isMutant(true).build(),
            StatsEntity.builder().isMutant(false).build()));
    Stat stat = dnaAnalyzerController.getStats().getBody();
    assertEquals(1, stat.getCountHumanDNA());
    assertEquals(1, stat.getCountMutantDNA());
    assertEquals(1, stat.getRatio());
  }

  @Test
  void testGetAnaliseDNAMutantVertical() {
    when(statRepository.findById(anyInt())).thenReturn(Optional.empty());
    final DNARequest dnaRequest = DNARequest.builder()
        .dna(new String[]{"AACTCA", "ATCCGT", "ACCGGC", "AGCAAG", "AATTAA", "ATGCTT"}).build();
    assertTrue(dnaAnalyzerController.analiseDNA(dnaRequest).getBody());
  }

  @Test
  void testGetAnaliseDNAMutantHorizontal() {
    when(statRepository.findById(anyInt())).thenReturn(Optional.empty());
    final DNARequest dnaRequest = DNARequest.builder()
        .dna(new String[]{"AAAAAA", "ATCGAT", "CCCCTG", "TCGATC", "CGGAAT", "ATCGAT"}).build();
    assertTrue(dnaAnalyzerController.analiseDNA(dnaRequest).getBody());
  }

  @Test
  void testGetAnaliseDNAMutantDiagonalRight() {
    when(statRepository.findById(anyInt())).thenReturn(Optional.empty());
    final DNARequest dnaRequest = DNARequest.builder()
        .dna(new String[]{"AATGGG", "TACTAT", "TCACTG", "CCGATT", "CGGATT", "TTCGAC"}).build();
    assertTrue(dnaAnalyzerController.analiseDNA(dnaRequest).getBody());
  }

  @Test
  void testGetAnaliseDNAMutantDiagonalLeft() {
    when(statRepository.findById(anyInt())).thenReturn(Optional.empty());
    final DNARequest dnaRequest = DNARequest.builder()
        .dna(new String[]{"GGGTAA", "TATCAT", "GTCACT", "TTAGCC", "TTAGGC", "CAGCTT"}).build();
    assertTrue(dnaAnalyzerController.analiseDNA(dnaRequest).getBody());
  }

  @Test
  void testGetAnaliseDNAMutantHVlCross() {
    when(statRepository.findById(anyInt())).thenReturn(Optional.empty());
    final DNARequest dnaRequest = DNARequest.builder()
        .dna(new String[]{"ATCGAT", "CGATCG", "TCAAAA", "TAGATA", "CGAACG", "TAGATA"}).build();
    assertTrue(dnaAnalyzerController.analiseDNA(dnaRequest).getBody());
  }

  @Test
  void testGetAnaliseDNAMutantDiagonalX() {
    when(statRepository.findById(anyInt())).thenReturn(Optional.empty());
    final DNARequest dnaRequest = DNARequest.builder()
        .dna(new String[]{"ATCGAT", "CGATCG", "TCAAAA", "TAGATA", "CGATAG", "TAGCTA"}).build();
    assertTrue(dnaAnalyzerController.analiseDNA(dnaRequest).getBody());
  }

  @Test
  void testGetAnaliseDNAMutantVerticalDiagonal() {
    when(statRepository.findById(anyInt())).thenReturn(Optional.empty());
    final DNARequest dnaRequest = DNARequest.builder()
        .dna(new String[]{"ATCGAT", "CGATCG", "TCGAAC", "TAGATA", "CGAACG", "TAGATA"}).build();
    assertTrue(dnaAnalyzerController.analiseDNA(dnaRequest).getBody());
  }

  @Test
  void testGetAnaliseDNAMutantIsHuman() {
    when(statRepository.findById(anyInt())).thenReturn(Optional.empty());
    final DNARequest dnaRequest = DNARequest.builder()
        .dna(new String[]{"ATCGAT", "GCTAGC", "TCGATC", "AGCTAG", "TCGATC", "AGCTAG"}).build();
    assertFalse(dnaAnalyzerController.analiseDNA(dnaRequest).getBody());
  }

  @Test
  void testGetAnaliseDNAMutantIsNotHumanSaved() {
    when(statRepository.findById(anyInt()))
        .thenReturn(Optional.of(StatsEntity.builder().isMutant(true).build()));
    final DNARequest dnaRequest = DNARequest.builder()
        .dna(new String[]{"ATCGAT", "GCTAGC", "TCGATC", "AGCTAG", "TCGATC", "AGCTAG"}).build();
    assertTrue(dnaAnalyzerController.analiseDNA(dnaRequest).getBody());
  }

  @Test
  void testGetAnaliseDNAMalformedDNA() {
    final DNARequest dnaRequest = DNARequest.builder()
        .dna(new String[]{"A", "GCTAGC", "TCGATC", "AGCTAG", "TCGATC", "AGCTAG"}).build();
    ResponseEntity<Boolean> responseEntity = dnaAnalyzerController.analiseDNA(dnaRequest);
    assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
  }

}