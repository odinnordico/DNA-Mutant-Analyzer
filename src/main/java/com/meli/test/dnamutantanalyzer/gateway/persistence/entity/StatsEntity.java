package com.meli.test.dnamutantanalyzer.gateway.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "STATS")
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class StatsEntity {

  @Id
  private int id;
  @Column(name = "json_dna", nullable = false, length = 524288)
  private String jsonDNA;
  @Column(name = "length", nullable = false)
  private int length;
  @Column(name = "is_mutant", nullable = false)
  private boolean isMutant;
}
