package com.meli.test.dnamutantanalyzer.services;

import com.meli.test.dnamutantanalyzer.model.CharMatrix;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * MatrixProcessor provide the methods to retrieve the different types of lines in a matrix
 */
public interface MatrixProcessor {

  /**
   * getDiagonalsRight retrieve all the lines in diagonal from left to right of a 2D Char Matrix
   *
   * @param matrix in which the lines are
   * @return the List of different lines
   */
  CompletableFuture<List<String>> getDiagonalsRight(CharMatrix matrix);

  /**
   * getDiagonalsLeft retrieve all the lines in diagonal from right to left of a 2D Char Matrix
   *
   * @param matrix in which the lines are
   * @return the List of different lines
   */
  CompletableFuture<List<String>> getDiagonalsLeft(CharMatrix matrix);

  /**
   * getHorizontals retrieve all the lines in horizontal(rows) of a 2D Char Matrix
   *
   * @param matrix in which the lines are
   * @returnthe List of different lines
   */
  CompletableFuture<List<String>> getHorizontals(CharMatrix matrix);

  /**
   * getVerticals retrieve all the lines in vertical(columns) of a 2D Char Matrix
   *
   * @param matrix in which the lines are
   * @return List of different lines
   */
  CompletableFuture<List<String>> getVerticals(CharMatrix matrix);

  /**
   * calculateSquarePosition calculates the position in the array data of a 2D Square Matrix
   * representation, based on the coordinates and the length or number of cols/rows
   *
   * @param row    the corresponding row in the matrix representation
   * @param col    the corresponding col in the matrix representation
   * @param length in a NxN matrix the length is the value of N
   * @return the index in data of the matrix
   */
  default int calculateSquarePosition(final int row, final int col, final int length) {
    return length * row + col;
  }

}
