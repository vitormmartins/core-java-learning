package com.example.reactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
   * Check if the string is palindrome reactively
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
    return words.filter(word -> word.length() > minLength);
  }

  /**
   * Merge multiple text streams and count total characters
   */
  public Mono<Long> countTotalCharacters(Flux<String> stream1, Flux<String> stream2){
    return Flux.merge(stream1, stream2)
               .map(String::length)
               .reduce(0L, Long::sum);
  }

  /**
   * Finds the longest word from a reactive stream.
   *
   * <p>Edge case behavior:
   * <ul>
   *   <li>If the input {@code Flux} is empty, returns {@code Mono.empty()}.</li>
   *   <li>Null values in the stream are ignored.</li>
   *   <li>If multiple words have the same maximum length, the first such word encountered is returned.</li>
   * </ul>
   *
   * @param words a {@code Flux} of words
   * @return a {@code Mono} emitting the longest word, or {@code Mono.empty()} if none found
   */
  public Mono<String> findLongestWordReactive(Flux<String> words) {
    // Use reduce to find the longest word
    return words.reduce((w1, w2) -> w1.length() >= w2.length() ? w1 : w2);
  }

  /**
   * Emit words with index (position) in the stream
   */
  public Flux<String> emitWordsWithIndex(Flux<String> words){
    return words.index().map(tuple -> tuple.getT1() + ": " + tuple.getT2());
  }

  /**
   * Group characters by vowel/consonant reactively
   */
  public Mono<Map<String, List<Character>>> groupByVowelConsonant(String input) {
    if (input == null) {
      return Mono.just(Map.of());
    }
    return Flux.fromStream(input.chars().mapToObj(c -> (char) c))
               .map(Character::toLowerCase)
               .filter(Character::isLetter)
               .collect(groupingBy(c -> "aeiou".indexOf(c) >= 0 ? "vowel" : "consonant"))
               .map(map -> {
                  map.putIfAbsent("vowel", List.of());
                  map.putIfAbsent("consonant", List.of());
                  return map;
               });
  }

  /**
   * Handle errors gracefully - emit default value on error
   */
  public Mono<String> processWithFallback(Mono<String> input, String fallback) {
    if (input == null) {
      return Mono.justOrEmpty(fallback);
    }
    return input.onErrorResume(e -> Mono.justOrEmpty(fallback));
  }

  /**
   * Batch process strings in groups of N
   */
  public Flux<List<String>> batchWords(Flux<String> words, int batchSize) {
    if (batchSize <= 0) {
      return Flux.error(new IllegalArgumentException("batchSize must be > 0"));
    }
    if (words == null) {
      return Flux.empty();
    }
    return words.buffer(batchSize);
  }

  /**
   * Retry operation up to N times on failure
   */
  public Mono<String> processWithRetry(Mono<String> input, int maxRetries) {
    if (input == null) {
      return Mono.empty();
    }
    if (maxRetries <= 0) {
      return input;
    }
    return input.retry(maxRetries);
  }

  /**
   * Transform words and collect to a single concatenated string
   */
  public Mono<String> concatenateWords(Flux<String> words, String delimiter) {
    if (words == null) {
      return Mono.empty();
    }
    String sep = delimiter == null ? "" : delimiter;
    return words.filter(Objects::nonNull)
                .reduce((a, b) -> a.isEmpty() ? b : a + sep + b);
  }

  /**
    * Emit characters with exponential backoff delay
    */
   public Flux<Character> emitWithBackoff(String input){
     if (input == null || input.isEmpty()) return Flux.empty();
     Duration base = Duration.ofMillis(100);
     return Flux.fromStream(input.chars().mapToObj(c -> (char) c))
                .index()
                .concatMap(tuple -> {
                   long idx = tuple.getT1();
                   int capped = (int) Math.min(idx, 30); // evita overflow no shift
                   long multiplier = 1L << capped;
                   Duration delay = base.multipliedBy(multiplier);
                   return Mono.just(tuple.getT2()).delayElement(delay);
                });
   }

  /**
   * Process in parallel and collect results
   */
  public Mono<List<String>> processInParallel(List<String> words) {
    if (words == null || words.isEmpty()) {
      return Mono.just(List.of());
    }
    int concurrency = Math.min(words.size(), 16);
    return Flux.fromIterable(words)
               .flatMapSequential(w ->
                            Mono.fromCallable(() -> {
                              if (w == null) return null;
                              return w.trim().toUpperCase();
                            }).subscribeOn(reactor.core.scheduler.Schedulers.parallel()), concurrency)
               .collectList();
  }}
