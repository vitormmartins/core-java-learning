package com.example.reactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.*;
import static java.util.Map.Entry.comparingByValue;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * Reactive string processing exercises using Project Reactor (WebFlux)
 * Perfect for practicing reactive programming patterns
 */
public class ReactiveStringProcessor {

  /**
   * Convert a string to a Flux of characters
   */
  public Flux<Character> stringToFlux(String input){
    // Implementation here
    return Flux.just(input.chars()
                          .mapToObj(c -> (char) c)
                          .toArray(Character[]::new));
  }

  /**
   * Find the most used character reactively
   * Returns a Mono containing the most frequent character
   */
  public Mono<Character> findMostUsedCharacterReactive(String input) {
    return Mono.justOrEmpty(
               input.chars()
                    .mapToObj(c -> (char) c)
                    .map(Character::toLowerCase)
                    .collect(groupingBy(identity(), counting()))
                    .entrySet()
                    .stream()
                    .max(comparingByValue())
                    .map(Entry::getKey)
    );
}
 /**
   * Find all most used characters reactively (when there are ties)
   */
  public Flux<Character> findAllMostUsedCharactersReactive(String input){
    Map<Character, Long> frequencies = input.chars()
                                            .mapToObj(c -> (char)c)
                                            .map(Character::toLowerCase)
                                            .collect(groupingBy(identity(), counting()));
    if(frequencies.isEmpty()) return Flux.empty();
    long maxFreq = frequencies.values()
                              .stream()
                              .max(Long::compare)
                              .orElse(0L);
    List<Character> result = frequencies.entrySet()
                                        .stream()
                                        .filter(e -> e.getValue() == maxFreq)
                                        .map(Map.Entry::getKey)
                                        .toList();
    return Flux.fromIterable(result);
  }

  /**
   * Count vowels reactively
   */
  public Mono<Long> countVowelsReactive(String input) {
    // Implementation here
    return Mono.just(input.chars()
                          .mapToObj(c -> (char) c)
                          .map(Character::toLowerCase)
                          .filter(c -> "aeiou".indexOf(c) >= 0)
                          .count()
    );
  }

  /**
   * Filter only alphabetic characters from a Flux
   */
  public Flux<Character> filterAlphabetic(Flux<Character> chars) {
    return chars.filter(Character::isLetter);
  }

  /**
   * Transform a Flux of strings to uppercase reactively
   */
  public Flux<String> toUpperCaseReactive(Flux<String> words){
    // Implementation here
    return words.map(String::toUpperCase);
  }

  /**
   * Combine multiple strings with delay between emissions
   */
  public Flux<String> emitWithDelay(List<String> words, Duration delay){
    return Flux.fromIterable(words)
               .delayElements(delay);
  }

  /**
   * Get character frequency map reactively
   */
  public Mono<Map<Character, Long>> getCharacterFrequenciesReactive(String input) {
    return Flux.fromStream(input.chars().mapToObj(c -> (char) c))
               .collect(HashMap::new, (map, character) ->
                                        map.merge(character, 1L, Long::sum));
  }

  /**
   * Check if string is palindrome reactively
   */
  public Mono<Boolean> isPalindromeReactive(String input){
    if(input == null) {
      return Mono.just(false);
    }
    String normalized = input.toLowerCase();
    String reversed = new StringBuilder(normalized).reverse().toString();
    return Mono.just(normalized.equals(reversed));
  }

  /**
   * Process a stream of words and emit only those longer than N characters
   */
  public Flux<String> filterWordsByLength(Flux<String> words, int minLength){
    // Implementation here
    return Flux.empty();
  }

  /**
   * Merge multiple text streams and count total characters
   */
  public Mono<Long> countTotalCharacters(Flux<String> stream1, Flux<String> stream2){
    // Implementation here
    return Mono.empty();
  }

  /**
   * Find the longest word from a reactive stream
   */
  public Mono<String> findLongestWordReactive(Flux<String> words){
    // Implementation here
    return Mono.empty();
  }

  /**
   * Emit words with index (position) in the stream
   */
  public Flux<String> emitWordsWithIndex(Flux<String> words){
    // Implementation here
    return Flux.empty();
  }

  /**
   * Group characters by vowel/consonant reactively
   */
  public Mono<Map<String, List<Character>>> groupByVowelConsonant(String input){
    // Implementation here
    return Mono.empty();
  }

  /**
   * Handle errors gracefully - emit default value on error
   */
  public Mono<String> processWithFallback(Mono<String> input, String fallback){
    // Implementation here
    return Mono.empty();
  }

  /**
   * Batch process strings in groups of N
   */
  public Flux<List<String>> batchWords(Flux<String> words, int batchSize){
    // Implementation here
    return Flux.empty();
  }

  /**
   * Retry operation up to N times on failure
   */
  public Mono<String> processWithRetry(Mono<String> input, int maxRetries){
    // Implementation here
    return Mono.empty();
  }

  /**
   * Transform words and collect to a single concatenated string
   */
  public Mono<String> concatenateWords(Flux<String> words, String delimiter){
    // Implementation here
    return Mono.empty();
  }

  /**
   * Emit characters with exponential backoff delay
   */
  public Flux<Character> emitWithBackoff(String input){
    // Implementation here
    return Flux.empty();
  }

  /**
   * Process in parallel and collect results
   */
  public Mono<List<String>> processInParallel(List<String> words){
    // Implementation here
    return Mono.empty();
  }
}
