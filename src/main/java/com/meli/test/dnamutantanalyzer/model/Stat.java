package com.meli.test.dnamutantanalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Stat {

  @JsonProperty("count_mutant_dna")
  @Min(0)
  @ApiModelProperty(notes = "the number of analysis with result mutant")
  private int countMutantDNA;
  @JsonProperty("count_human_dna")
  @Min(0)
  @ApiModelProperty(notes = "the number of analysis with result human")
  private int countHumanDNA;
  @JsonProperty("ratio")
  @Min(0)
  @ApiModelProperty(notes = "the ratio of analyzed dna between mutant to human")
  private double ratio;

}
