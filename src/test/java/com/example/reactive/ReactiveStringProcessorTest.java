package com.example.reactive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReactiveStringProcessorTest {

  private ReactiveStringProcessor processor;

  @BeforeEach
  void setUp() {
    processor = new ReactiveStringProcessor();
  }

  @Test
  @DisplayName("Should convert string to Flux of characters")
  void testStringToFlux() {
    // Given
    String input = "hello";

    // When
    Flux<Character> result = processor.stringToFlux(input);

    // Then
    StepVerifier.create(result)
                .expectNext('h', 'e', 'l', 'l', 'o')
                .verifyComplete();
  }

  @Test
  @DisplayName("Should find most used character reactively")
  void testFindMostUsedCharacterReactive() {
    // Given
    String input = "hello world";

    // When
    Mono<Character> result = processor.findMostUsedCharacterReactive(input);

    // Then
    StepVerifier.create(result)
                .expectNext('l') // 'l' appears 3 times
                .verifyComplete();
  }

  @Test
  @DisplayName("Should find all most used characters when there are ties")
  void testFindAllMostUsedCharactersReactive() {
    // Given
    String input = "aabbcc";

    // When
    Flux<Character> result = processor.findAllMostUsedCharactersReactive(input);

    // Then
    StepVerifier.create(result)
                .expectNextCount(3) // a, b, c all appear twice
                .verifyComplete();
  }

  @Test
  @DisplayName("Should count vowels reactively")
  void testCountVowelsReactive() {
    // Given
    String input = "Hello World";

    // When
    Mono<Long> result = processor.countVowelsReactive(input);

    // Then
    StepVerifier.create(result)
                .expectNext(3L) // e, o, o
                .verifyComplete();
  }

  @Test
  @DisplayName("Should filter only alphabetic characters")
  void testFilterAlphabetic() {
    // Given
    Flux<Character> input = Flux.just('h', 'e', '1', 'l', '2', 'o', '!');

    // When
    Flux<Character> result = processor.filterAlphabetic(input);

    // Then
    StepVerifier.create(result)
                .expectNext('h', 'e', 'l', 'o')
                .verifyComplete();
  }

  @Test
  @DisplayName("Should transform strings to uppercase reactively")
  void testToUpperCaseReactive() {
    // Given
    Flux<String> input = Flux.just("hello", "world", "java");

    // When
    Flux<String> result = processor.toUpperCaseReactive(input);

    // Then
    StepVerifier.create(result)
                .expectNext("HELLO", "WORLD", "JAVA")
                .verifyComplete();
  }

  @Test
  @DisplayName("Should emit words with delay")
  void testEmitWithDelay() {
    // Given
    List<String> words = Arrays.asList("one", "two", "three");
    Duration delay = Duration.ofMillis(100);

    // When
    Flux<String> result = processor.emitWithDelay(words, delay);

    // Then
    StepVerifier.create(result)
                .expectNext("one")
                .expectNoEvent(Duration.ofMillis(90))
                .expectNext("two")
                .expectNoEvent(Duration.ofMillis(90))
                .expectNext("three")
                .verifyComplete();
  }

  @Test
  @DisplayName("Should get character frequencies reactively")
  void testGetCharacterFrequenciesReactive() {
    // Given
    String input = "hello";

    // When
    Mono<Map<Character, Long>> result = processor.getCharacterFrequenciesReactive(input);

    // Then
    StepVerifier.create(result)
                .assertNext(map -> {
                  assertEquals(2L, map.get('l'));
                  assertEquals(1L, map.get('h'));
                  assertEquals(1L, map.get('e'));
                  assertEquals(1L, map.get('o'));
                })
                .verifyComplete();
  }

  @Test
  @DisplayName("Should check palindrome reactively")
  void testIsPalindromeReactive() {
    // Given
    String palindrome = "racecar";
    String notPalindrome = "hello";

    // When & Then
    StepVerifier.create(processor.isPalindromeReactive(palindrome))
            .expectNext(true)
            .verifyComplete();

    StepVerifier.create(processor.isPalindromeReactive(notPalindrome))
            .expectNext(false)
            .verifyComplete();
  }

  @Test
  @DisplayName("Should filter words by minimum length")
  void testFilterWordsByLength() {
    // Given
    Flux<String> words = Flux.just("I", "am", "learning", "Java", "WebFlux");

    // When
    Flux<String> result = processor.filterWordsByLength(words, 5);

    // Then
    StepVerifier.create(result)
            .expectNext("learning", "WebFlux")
            .verifyComplete();
  }

  @Test
  @DisplayName("Should count total characters from merged streams")
  void testCountTotalCharacters() {
    // Given
    Flux<String> stream1 = Flux.just("hello", "world");
    Flux<String> stream2 = Flux.just("foo", "bar");

    // When
    Mono<Long> result = processor.countTotalCharacters(stream1, stream2);

    // Then
    StepVerifier.create(result)
            .expectNext(16L) // hello(5) + world(5) + foo(3) + bar(3) = 16
            .verifyComplete();
  }

  @Test
  @DisplayName("Should find longest word reactively")
  void testFindLongestWordReactive() {
    // Given
    Flux<String> words = Flux.just("cat", "elephant", "dog", "bird");

    // When
    Mono<String> result = processor.findLongestWordReactive(words);

    // Then
    StepVerifier.create(result)
            .expectNext("elephant")
            .verifyComplete();
  }

  @Test
  @DisplayName("Should emit words with index")
  void testEmitWordsWithIndex() {
    // Given
    Flux<String> words = Flux.just("first", "second", "third");

    // When
    Flux<String> result = processor.emitWordsWithIndex(words);

    // Then
    StepVerifier.create(result)
            .expectNext("0: first", "1: second", "2: third")
            .verifyComplete();
  }

  @Test
  @DisplayName("Should group characters by vowel/consonant")
  void testGroupByVowelConsonant() {
    // Given
    String input = "hello";

    // When
    Mono<Map<String, List<Character>>> result = processor.groupByVowelConsonant(input);

    // Then
    StepVerifier.create(result)
            .assertNext(map -> {
              assertTrue(map.get("vowel").containsAll(Arrays.asList('e', 'o')));
              assertTrue(map.get("consonant").containsAll(Arrays.asList('h', 'l', 'l')));
            })
            .verifyComplete();
  }

  @Test
  @DisplayName("Should handle errors with fallback")
  void testProcessWithFallback() {
    // Given
    Mono<String> errorMono = Mono.error(new RuntimeException("Error"));
    String fallback = "default";

    // When
    Mono<String> result = processor.processWithFallback(errorMono, fallback);

    // Then
    StepVerifier.create(result)
            .expectNext("default")
            .verifyComplete();
  }

  @Test
  @DisplayName("Should batch words in groups")
  void testBatchWords() {
    // Given
    Flux<String> words = Flux.just("a", "b", "c", "d", "e");

    // When
    Flux<List<String>> result = processor.batchWords(words, 2);

    // Then
    StepVerifier.create(result)
            .assertNext(batch -> assertEquals(Arrays.asList("a", "b"), batch))
            .assertNext(batch -> assertEquals(Arrays.asList("c", "d"), batch))
            .assertNext(batch -> assertEquals(List.of("e"), batch))
            .verifyComplete();
  }

  @Test
  @DisplayName("Should retry on failure")
  void testProcessWithRetry() {
    // Given
    Mono<String> failingMono = Mono.defer(() -> {
      if (Math.random() > 0.5) {
        return Mono.just("success");
      }
      return Mono.error(new RuntimeException("Temporary failure"));
    });

    // When
    Mono<String> result = processor.processWithRetry(failingMono, 3);

    // Then - should eventually succeed or fail after retries
    StepVerifier.create(result)
                .expectNextMatches( s -> s == null || s.equals("success"))
                .verifyComplete();
  }

  @Test
  @DisplayName("Should concatenate words with delimiter")
  void testConcatenateWords() {
    // Given
    Flux<String> words = Flux.just("Hello", "Reactive", "World");

    // When
    Mono<String> result = processor.concatenateWords(words, " ");

    // Then
    StepVerifier.create(result)
            .expectNext("Hello Reactive World")
            .verifyComplete();
  }

  @Test
  @DisplayName("Should handle empty stream")
  void testEmptyStream() {
    // Given
    Flux<String> empty = Flux.empty();

    // When
    Mono<String> result = processor.findLongestWordReactive(empty);

    // Then
    StepVerifier.create(result)
            .verifyComplete(); // Should complete without emitting
  }

  @Test
  @DisplayName("Should emit with backoff")
  void testEmitWithBackoff() {
    // Given
    String input = "abc";

    // When
    Flux<Character> result = processor.emitWithBackoff(input);

    // Then
    StepVerifier.create(result)
            .expectNext('a')
            .expectNoEvent(Duration.ofMillis(90)) // ~100ms delay
            .expectNext('b')
            .expectNoEvent(Duration.ofMillis(190)) // ~200ms delay
            .expectNext('c')
            .verifyComplete();
  }

  @Test
  @DisplayName("Should process in parallel")
  void testProcessInParallel() {
    // Given
    List<String> words = Arrays.asList("one", "two", "three", "four");

    // When
    Mono<List<String>> result = processor.processInParallel(words);

    // Then
    StepVerifier.create(result)
            .assertNext(list -> {
              assertEquals(4, list.size());
              assertTrue(list.containsAll(words));
            })
            .verifyComplete();
  }
}
