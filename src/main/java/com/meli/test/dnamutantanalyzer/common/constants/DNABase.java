package com.meli.test.dnamutantanalyzer.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DNABase is a DNA Base (Adenine, Thymine, Cytosine, Guanine), it contains the mutant
 * representation of each
 */
@AllArgsConstructor
@Getter
public enum DNABase {
  A("AAAA"),
  T("TTTT"),
  C("CCCC"),
  G("GGGG");
  private final String mutantRepresentation;
}
