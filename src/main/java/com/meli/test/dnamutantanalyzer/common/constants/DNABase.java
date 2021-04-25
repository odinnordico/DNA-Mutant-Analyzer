package com.meli.test.dnamutantanalyzer.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DNABase {
  A("AAAA"),
  T("TTTT"),
  C("CCCC"),
  G("GGGG");
  private final String mutantRepresentation;
}
