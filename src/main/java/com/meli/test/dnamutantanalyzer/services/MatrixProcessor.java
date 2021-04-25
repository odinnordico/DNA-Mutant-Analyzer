package com.meli.test.dnamutantanalyzer.services;

import com.meli.test.dnamutantanalyzer.model.CharMatrix;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface MatrixProcessor {

  CompletableFuture<List<String>> getDiagonalsRight(CharMatrix matrix);

  CompletableFuture<List<String>> getDiagonalsLeft(CharMatrix matrix);

  CompletableFuture<List<String>> getHorizontals(CharMatrix matrix);

  CompletableFuture<List<String>> getVerticals(CharMatrix matrix);

  default int calculateSquarePosition(final int row, final int col, final int length) {
    return length * row + col;
  }

}
