package com.meli.test.dnamutantanalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class DNARequest {

  @JsonProperty("dna")
  @Valid
  private String[] dna;
}
