package com.example.streams;

import java.util.*;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class StreamExamples {
  public List<String> filterAndMap(List<String> input) {
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

  public List<Character> findMostUsedCharacters(String input1) {
    Set<Map.Entry<Character, Long>> entries = input1.chars()
                                                    .mapToObj(c -> (char) c)
                                                    .map(Character::toLowerCase)
                                                    .collect(groupingBy(character -> character, counting()))
                                                    .entrySet();

    Optional<Long> maxValue = entries.stream()
                                     .map(Map.Entry::getValue)
                                     .max(Long::compare);

    return entries.stream()
                  .filter(e -> Objects.equals(e.getValue(), maxValue.get()))
                  .map(Map.Entry::getKey)
                  .toList();


  }
}
