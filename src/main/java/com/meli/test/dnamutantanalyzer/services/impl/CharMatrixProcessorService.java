package com.meli.test.dnamutantanalyzer.services.impl;

import com.meli.test.dnamutantanalyzer.model.CharMatrix;
import com.meli.test.dnamutantanalyzer.services.MatrixProcessor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CharMatrixProcessorService implements MatrixProcessor {

  @Async
  @Override
  public CompletableFuture<List<String>> getDiagonalsRight(final CharMatrix matrix) {
    List<String> lines = Collections.synchronizedList(new ArrayList<>());
    CharMatrix charMatrix = (CharMatrix) matrix;
    StringBuffer center = new StringBuffer();
    for (var i = 0; i < charMatrix.getLength(); i++) {
      center.append(charMatrix.getData()[calculateSquarePosition(i, i, charMatrix.getLength())]);
    }
    lines.add(center.toString());
    for (var i = 1; i < charMatrix.getLength() - 3; i++) {
      StringBuffer left = new StringBuffer();
      StringBuffer right = new StringBuffer();
      for (var j = 0; j < charMatrix.getLength() - 1; j++) {
        if (i + j == charMatrix.getLength()) {
          break;
        }
        left.append(
            charMatrix.getData()[calculateSquarePosition(j + i, j, charMatrix.getLength())]);
        right.append(
            charMatrix.getData()[calculateSquarePosition(j, j + i, charMatrix.getLength())]);
      }
      lines.add(left.toString());
      lines.add(right.toString());
    }
    return CompletableFuture.completedFuture(lines);
  }

  @Async
  @Override
  public CompletableFuture<List<String>> getDiagonalsLeft(final CharMatrix charMatrix) {
    List<String> lines = Collections.synchronizedList(new ArrayList<>());
    StringBuffer center = new StringBuffer();
    var j = charMatrix.getLength() - 1;
    for (var i = 0; i < charMatrix.getLength(); i++) {
      center.append(charMatrix.getData()[calculateSquarePosition(i, j--, charMatrix.getLength())]);
    }
    lines.add(center.toString());
    var rw = 1;
    for (var i = charMatrix.getLength() - 2; i > 2; i--) {
      StringBuffer left = new StringBuffer();
      StringBuffer right = new StringBuffer();
      for (j = 0; j < charMatrix.getLength() - 1; j++) {
        if (i - j < 0) {
          break;
        }
        left.append(
            charMatrix.getData()[calculateSquarePosition(j, i - j, charMatrix.getLength())]);
        right.append(
            charMatrix.getData()[calculateSquarePosition(j + rw, charMatrix.getLength() - 1 - j,
                charMatrix.getLength())]);
      }
      lines
          .add(left.toString());
      lines.add(right.toString());
      rw++;
    }
    return CompletableFuture.completedFuture(lines);
  }

  @Async
  @Override
  public CompletableFuture<List<String>> getHorizontals(final CharMatrix charMatrix) {
    List<String> lines = Collections.synchronizedList(new ArrayList<>());
    for (var i = 0; i < charMatrix.getLength(); i++) {
      StringBuffer center = new StringBuffer();
      for (var j = 0; j < charMatrix.getLength(); j++) {
        center.append(charMatrix.getData()[calculateSquarePosition(i, j, charMatrix.getLength())]);
      }
      lines.add(center.toString());
    }
    return CompletableFuture.completedFuture(lines);
  }

  @Async
  @Override
  public CompletableFuture<List<String>> getVerticals(final CharMatrix charMatrix) {
    List<String> lines = Collections.synchronizedList(new ArrayList<>());
    for (var i = 0; i < charMatrix.getLength(); i++) {
      StringBuffer center = new StringBuffer();
      for (var j = 0; j < charMatrix.getLength(); j++) {
        center.append(charMatrix.getData()[calculateSquarePosition(j, i, charMatrix.getLength())]);
      }
      lines.add(center.toString());
    }
    return CompletableFuture.completedFuture(lines);
  }
}
