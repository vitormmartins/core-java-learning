package com.example.streams;

import java.util.List;
import java.util.OptionalDouble;

public class StreamExamples {
  public List<String> filterAndMap(List<String> input){
    return input.stream()
                .filter(s -> s.length() > 3)
                .map(String::toUpperCase)
                .toList();

  }

  public OptionalDouble calculateAverage(List<Integer> numbers) {

    return numbers.stream()
                  .mapToInt(Integer::intValue)
                  .average();
  }

  public long countElements(List<Integer> numbers) {
    return numbers.stream()
                  .count();
  }

  public List<Integer> filterOddNumbers(List<Integer> mixedNumbers) {
    return mixedNumbers.stream()
                       .filter(n -> n % 2 != 0)
                       .toList();
  }
}
