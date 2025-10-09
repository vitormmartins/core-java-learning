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
    assertEquals(Arrays.asList('i', 'r', 't'), result1, "Most used characters in 'Vitor Martins' should be i, r, t");
    assertEquals(Arrays.asList('e', 'r'), result2, "Most used characters in 'Avner Pereira' should be e, r");
  }

  @Test
  @DisplayName("Should find least used characters in a string")
  void testLeastUsedCharacters() {
    // Given
    String input1 = "abbbc";
    String input2 = "Hello World";

    // When
    List<Character> result1 = streamExamples.findLeastUsedCharacters(input1);
    List<Character> result2 = streamExamples.findLeastUsedCharacters(input2);

    // Then
    assertEquals(Arrays.asList('a', 'c'), result1, "Least used characters should be a and c (1 occurrence each)");
    assertTrue(result2.contains('h') && result2.contains('e') && result2.contains('w'),
            "Should contain characters with minimum frequency");
  }

  @Test
  @DisplayName("Should find characters with specific frequency")
  void testFindCharactersWithFrequency() {
    // Given
    String input = "aabbbbccc";

    // When
    List<Character> twiceOccurring = streamExamples.findCharactersWithFrequency(input, 2);
    List<Character> fourTimesOccurring = streamExamples.findCharactersWithFrequency(input, 4);
    List<Character> threeTimesOccurring = streamExamples.findCharactersWithFrequency(input, 3);

    // Then
    assertEquals(List.of('a'), twiceOccurring, "Only 'a' appears exactly 2 times");
    assertEquals(List.of('b'), fourTimesOccurring, "Only 'b' appears exactly 4 times");
    assertEquals(List.of('c'), threeTimesOccurring, "Only 'c' appears exactly 3 times");
  }

  @Test
  @DisplayName("Should get character frequencies as a map")
  void testGetCharacterFrequencies() {
    // Given
    String input = "hello";

    // When
    Map<Character, Long> frequencies = streamExamples.getCharacterFrequencies(input);

    // Then
    assertEquals(2L, frequencies.get('l'), "'l' should appear 2 times");
    assertEquals(1L, frequencies.get('h'), "'h' should appear 1 time");
    assertEquals(1L, frequencies.get('e'), "'e' should appear 1 time");
    assertEquals(1L, frequencies.get('o'), "'o' should appear 1 time");
    assertEquals(4, frequencies.size(), "Should have 4 unique characters");
  }

  @Test
  @DisplayName("Should find the longest word in a sentence")
  void testFindLongestWord() {
    // Given
    String sentence1 = "The quick brown fox jumps";
    String sentence2 = "I am learning Java Streams";

    // When
    Optional<String> result1 = streamExamples.findLongestWord(sentence1);
    Optional<String> result2 = streamExamples.findLongestWord(sentence2);

    // Then
    assertTrue(result1.isPresent());
    assertEquals("quick", result1.get(), "Longest word should be 'quick'");
    assertTrue(result2.isPresent());
    assertTrue(result2.get().equals("learning") || result2.get().equals("Streams"),
            "Longest word should be either 'learning' or 'Streams'");
  }

  @Test
  @DisplayName("Should find all longest words in a sentence")
  void testFindAllLongestWords() {
    // Given
    String sentence = "cat dog bird fish";

    // When
    List<String> result = streamExamples.findAllLongestWords(sentence);

    // Then
    assertEquals(2, result.size(), "Should have 2 words with length 4");
    assertTrue(result.contains("bird") && result.contains("fish"),
            "Should contain both 'bird' and 'fish'");
  }

  @Test
  @DisplayName("Should count vowels in a string")
  void testCountVowels() {
    // Given
    String input1 = "Hello World";
    String input2 = "AEIOU";
    String input3 = "bcdfg";

    // When
    long count1 = streamExamples.countVowels(input1);
    long count2 = streamExamples.countVowels(input2);
    long count3 = streamExamples.countVowels(input3);

    // Then
    assertEquals(3L, count1, "'Hello World' should have 3 vowels (e, o, o)");
    assertEquals(5L, count2, "'AEIOU' should have 5 vowels");
    assertEquals(0L, count3, "'bcdfg' should have 0 vowels");
  }

  @Test
  @DisplayName("Should find the most common word in text")
  void testFindMostCommonWord() {
    // Given
    String text = "the cat and the dog and the bird";

    // When
    Optional<String> result = streamExamples.findMostCommonWord(text);

    // Then
    assertTrue(result.isPresent());
    assertEquals("the", result.get(), "'the' appears 3 times and should be most common");
  }

  @Test
  @DisplayName("Should group words by their length")
  void testGroupWordsByLength() {
    // Given
    String text = "I am a Java developer";

    // When
    Map<Integer, List<String>> grouped = streamExamples.groupWordsByLength(text);

    // Then
    assertEquals(List.of("I", "a"), grouped.get(1), "Should have 2 words of length 1");
    assertEquals(List.of("am"), grouped.get(2), "Should have 1 word of length 2");
    assertEquals(List.of("Java"), grouped.get(4), "Should have 1 word of length 4");
    assertEquals(List.of("developer"), grouped.get(9), "Should have 1 word of length 9");
  }

  @Test
  @DisplayName("Should find unique characters preserving order")
  void testFindUniqueCharactersInOrder() {
    // Given
    String input = "hello world";

    // When
    List<Character> result = streamExamples.findUniqueCharactersInOrder(input);

    // Then
    assertEquals(Arrays.asList('h', 'e', 'l', 'o', ' ', 'w', 'r', 'd'), result,
            "Should preserve order of first occurrence");
  }

  @Test
  @DisplayName("Should check if string is palindrome")
  void testIsPalindrome() {
    // Given & When & Then
    assertTrue(streamExamples.isPalindrome("racecar"), "'racecar' should be palindrome");
    assertTrue(streamExamples.isPalindrome("A man a plan a canal Panama"),
            "Should ignore spaces and case");
    assertFalse(streamExamples.isPalindrome("hello"), "'hello' should not be palindrome");
    assertTrue(streamExamples.isPalindrome(""), "Empty string should be palindrome");
  }

  @Test
  @DisplayName("Should count consonants in a string")
  void testCountConsonants() {
    // Given
    String input1 = "Hello World";
    String input2 = "aeiou";
    String input3 = "bcdfg";

    // When
    long count1 = streamExamples.countConsonants(input1);
    long count2 = streamExamples.countConsonants(input2);
    long count3 = streamExamples.countConsonants(input3);

    // Then
    assertEquals(7L, count1, "'Hello World' should have 7 consonants");
    assertEquals(0L, count2, "'aeiou' should have 0 consonants");
    assertEquals(5L, count3, "'bcdfg' should have 5 consonants");
  }

  @Test
  @DisplayName("Edge case: Most used characters with empty string")
  void testMostUsedCharactersEmptyString() {
    // Given
    String empty = "";

    // When
    List<Character> result = streamExamples.findMostUsedCharacters(empty);

    // Then
    assertTrue(result.isEmpty(), "Empty string should return empty list");
  }

  @Test
  @DisplayName("Edge case: Most used characters with single character")
  void testMostUsedCharactersSingleChar() {
    // Given
    String single = "aaaaa";

    // When
    List<Character> result = streamExamples.findMostUsedCharacters(single);

    // Then
    assertEquals(List.of('a'), result, "Should return single character");
  }
}