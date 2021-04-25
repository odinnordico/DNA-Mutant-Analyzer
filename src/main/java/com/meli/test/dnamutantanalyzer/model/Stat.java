package com.meli.test.dnamutantanalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Stat {
  @JsonProperty("count_mutant_dna")
  @Min(0)
  private int countMutantDNA;
  @JsonProperty("count_human_dna")
  @Min(0)
  private int countHumanDNA;
  @JsonProperty("ratio")
  @Min(0)
  private double ratio;

}
