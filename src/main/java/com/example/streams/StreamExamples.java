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
            .sorted()
            .toList();
  }

  /**
   * Find the least frequent characters in a string (ignoring spaces)
   */
  public List<Character> findLeastUsedCharacters(String input) {
    Set<Map.Entry<Character, Long>> entries = input.chars()
                                                   .mapToObj(c -> (char) c)
                                                   .map(Character::toLowerCase)
                                                   .filter(c -> c != ' ')
                                                   .collect(groupingBy(character -> character, counting()))
                                                   .entrySet();

    Optional<Long> minValue = entries.stream()
                                     .map(Map.Entry::getValue)
                                     .min(Long::compare);

    return entries.stream()
                  .filter(e -> Objects.equals(e.getValue(), minValue.get()))
                  .map(Map.Entry::getKey)
                  .sorted()
                  .toList();
  }


  /**
   * Find all characters that appear exactly N times
   */
  public List<Character> findCharactersWithFrequency(String input, int frequency) {
    return input.chars()
                .mapToObj(c -> (char) c)
                .map(Character::toLowerCase)
                .filter(c -> c != ' ')
                .collect(groupingBy(character -> character, counting()))
                .entrySet()
                .stream()
                .filter(e -> Objects.equals(e.getValue(), (long) frequency))
                .map(Map.Entry::getKey)
                .sorted()
                .toList();
  }

  /**
   * Get frequency map of all characters in the string
   */
  public Map<Character, Long> getCharacterFrequencies(String input) {
    return input.chars()
                .mapToObj(c -> (char) c)
                .map(Character::toLowerCase)
                .filter(c -> c != ' ')
                .collect(groupingBy(character -> character, counting()));
  }

  /**
   * Find the longest word in a sentence
   */
  public Optional<String> findLongestWord(String sentence) {
    return Arrays.stream(sentence.split(" "))
                 .max(Comparator.comparingInt(String::length));
  }

  /**
   * Find all words with maximum length in a sentence
   */
  public List<String> findAllLongestWords(String sentence) {
    String[] words = sentence.split(" ");
    int maxLength = Arrays.stream(words)
                          .mapToInt(String::length)
                          .max()
                          .orElse(0);

    return Arrays.stream(words)
                 .filter(word -> word.length() == maxLength)
                 .toList();
  }

  /**
   * Count vowels in a string (case-insensitive)
   */
  public long countVowels(String input) {
    return input.chars()
                .mapToObj(c -> (char) c)
                .map(Character::toLowerCase)
                .filter(c -> "aeiou".indexOf(c) >= 0)
                .count();
  }

  /**
   * Find the most common word in a text
   */
  public Optional<String> findMostCommonWord(String text) {
    return Arrays.stream(text.split("\\W+"))
                 .map(String::toLowerCase)
                 .collect(groupingBy(word -> word, counting()))
                 .entrySet()
                 .stream()
                 .max(Map.Entry.comparingByValue())
                 .map(Map.Entry::getKey);
  }

  /**
   * Group words by their length
   */
  public Map<Integer, List<String>> groupWordsByLength(String text) {
    return Arrays.stream(text.split("\\W+"))
                 .collect(groupingBy(String::length));
  }

  /**
   * Find all unique characters in a string (preserving order)
   */
  public List<Character> findUniqueCharactersInOrder(String input) {
    return input.chars()
                .mapToObj(c -> (char) c)
                .distinct()
                .toList();
  }

  /**
   * Check if string is a palindrome (ignoring spaces and case)
   */
  public boolean isPalindrome(String input) {
    String cleaned = input.chars()
                          .mapToObj(c -> (char) c)
                          .filter(c -> c != ' ')
                          .map(Character::toLowerCase)
                          .map(String::valueOf)
                          .reduce("", String::concat);

    String reversed = new StringBuilder(cleaned).reverse().toString();
    return cleaned.equals(reversed);
  }

  /**
   * Count consonants in a string (case-insensitive)
   */
  public long countConsonants(String input) {
    return input.chars()
                .mapToObj(c -> (char) c)
                .map(Character::toLowerCase)
                .filter(c -> Character.isLetter(c) && "aeiou".indexOf(c) < 0)
                .count();
  }
}