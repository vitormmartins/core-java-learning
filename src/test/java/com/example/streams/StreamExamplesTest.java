package com.example.streams;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class StreamExamplesTest {

  private StreamExamples streamExamples;

  @BeforeEach
  void setUp() {
    streamExamples = new StreamExamples();
  }

  @Test
  void testFilterAndMap() {
    var result = streamExamples.filterAndMap(
            List.of("a", "ab", "abc", "abcd", "abcde")
    );
    assertEquals(List.of("ABCD", "ABCDE"), result);

    List<String> input = Arrays.asList("hi", "hello", "world", "a");
    result = streamExamples.filterAndMap(input);

    assertEquals(Arrays.asList("HELLO", "WORLD"), result);
  }

  @Test
  @DisplayName("Should calculate average of integer list")
  void testCalculateAverage() {
    // Given
    List<Integer> numbers = Arrays.asList(10, 20, 30, 40, 50);

    // When
    OptionalDouble result = streamExamples.calculateAverage(numbers);

    // Then
    assertTrue(result.isPresent(), "Average should be present for non-empty list");
    assertEquals(30.0, result.getAsDouble(), 0.01, "Average should be 30.0");

    // Edge case: empty list
    List<Integer> emptyList = Collections.emptyList();
    OptionalDouble emptyResult = streamExamples.calculateAverage(emptyList);
    assertFalse(emptyResult.isPresent(), "Average should not be present for empty list");
  }

  @Test
  @DisplayName("Should count elements in integer list")
  void testCountElements() {
    // Given
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

    // When
    long count = streamExamples.countElements(numbers);

    // Then
    assertEquals(7L, count, "Should count exactly 7 elements");

    // Edge cases
    assertEquals(0L, streamExamples.countElements(Collections.emptyList()),
            "Empty list should have count of 0");
    assertEquals(1L, streamExamples.countElements(List.of(42)),
            "Single element list should have count of 1");
  }

  @Test
  @DisplayName("Should filter odd numbers from integer list")
  void testFilterOddNumbers() {
    // Given
    List<Integer> mixedNumbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    List<Integer> expectedOdds = Arrays.asList(1, 3, 5, 7, 9);

    // When
    List<Integer> actualOdds = streamExamples.filterOddNumbers(mixedNumbers);

    // Then
    assertEquals(expectedOdds, actualOdds, "Should return only odd numbers in same order");
    assertEquals(5, actualOdds.size(), "Should have exactly 5 odd numbers");

    // Edge cases
    List<Integer> evenOnly = Arrays.asList(2, 4, 6, 8);
    assertTrue(streamExamples.filterOddNumbers(evenOnly).isEmpty(),
            "List with only even numbers should return empty list");

    List<Integer> oddOnly = Arrays.asList(1, 3, 5);
    assertEquals(oddOnly, streamExamples.filterOddNumbers(oddOnly),
            "List with only odd numbers should return all elements");
  }

  /*
   * 30/09/2025 06:30 AM BRT for
   * Tech Interview Question for Oskon Tech (Jaime):
   * Interviewer: Avner Pereira (Accenture)
   * Given a string, find the most used characters.
   * Examples:
   * Input: "Vitor Martins" Output: r, t, i
   * Input: "Avner Pereira" Output: r, e
   */
  @Test
  @DisplayName("Given a string, find the most used characters")
  void testMostUsedCharacters() {
    // Given
    String input1 = "Vitor Martins";
    String input2 = "Avner Pereira";

    // When
    List<Character> result1 = streamExamples.findMostUsedCharacters(input1);
    List<Character> result2 = streamExamples.findMostUsedCharacters(input2);

    // Then
    assertEquals(Arrays.asList('r', 't', 'i'), result1, "Most used characters in 'Vitor Martins' should be r, t, i");
    assertEquals(Arrays.asList('r', 'e'), result2, "Most used characters in 'Avner Pereira' should be r, e");
  }

}