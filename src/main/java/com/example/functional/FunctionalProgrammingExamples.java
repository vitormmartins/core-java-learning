package com.example.functional;

import java.util.List;
import java.util.function.*;

import static java.util.stream.Stream.generate;

public class FunctionalProgrammingExamples {
  public List<Integer> applyPredicateToFilter(List<Integer> numbers, Predicate<Integer> isEven) {
    return numbers.stream()
                  .filter(isEven)
                  .toList();
  }

  public <R> List<R> applyFunctionToTransform(List<String> list, Function<String, R> transformer) {
    return list.stream()
               .map(transformer)
               .toList();
  }

  // Java
  public <T, R, V> V composeFunctions(Function<T, R> f1, Function<R, V> f2, T t) {
    return f2.apply(f1.apply(t));
  }

  public void applyConsumerToEach(List<String> names, Consumer<String> addToList) {
    names.forEach(addToList);
  }

  public <T> List<T> useSupplierToGenerate(Supplier<T> supplier, int count) {
    return generate(supplier)
            .limit(count)
            .toList();
  }

  public List<Integer> chainPredicatesWithAnd(List<Integer> numbers, IntPredicate isEven, IntPredicate greaterThanFive) {
    return numbers.stream().filter(n -> isEven.test(n) && greaterThanFive.test(n)).toList();
  }

  public List<Integer> chainPredicatesWithOr(List<Integer> numbers, IntPredicate lessThanThree, IntPredicate greaterThanEight) {
    return numbers.stream().filter(n ->lessThanThree.test(n) || greaterThanEight.test(n)).toList();
  }

  public <T, R> R applyBiFunction(T a, T b, BiFunction<T, T, R> biFunction) {
    return biFunction.apply(a, b);
  }

  public Integer applyUnaryOperator(int i, IntUnaryOperator square) {
    return square.applyAsInt(i);
  }

  public Integer chainUnaryOperators(Integer initial,
                                     IntUnaryOperator multiplyByTwo,
                                     IntUnaryOperator addFive,
                                     IntUnaryOperator square) {
    return square.applyAsInt(addFive.applyAsInt(multiplyByTwo.applyAsInt(initial)));
  }

  public Integer reduceWithBinaryOperator(List<Integer> numbers, int i, IntBinaryOperator operator) {
    return numbers.stream().reduce(i, operator::applyAsInt);
  }

  public <T, U, V, R> R applyTriFunction(T a, U b, V c, TriFunction<T, U, V, R> function) {
    return function.apply(a, b, c);
  }

  public <T, R> List<R> applyMethodReference(List<T> list, Function<T, R> mapper) {
    return list.stream()
               .map(mapper)
               .toList();
  }

  public <T> List<T> createObjectsWithConstructor(List<String> names, Function<String, T> constructor) {
    return names.stream()
                .map(constructor)
                .toList();
  }

  public <T, U, R> Function<U, R> curry(BiFunction<T, U, R> biFunction, T firstArg) {
    return secondArg -> biFunction.apply(firstArg, secondArg);
  }

  public Boolean useFunctionAndThen(String input, ToIntFunction<String> length, IntPredicate isEven) {
    return isEven.test(length.applyAsInt(input));
  }

  public List<String> filterAndMap(List<Integer> numbers,
                                   Predicate<Integer> isEven,
                                   Function<Integer, String> toSquareString) {
    return numbers.stream()
                  .filter(isEven)
                  .map(toSquareString)
                  .toList();
  }

  public Function<String, String> createPrefixFunction(String prefix) {
    return str -> prefix + str;
  }
}
