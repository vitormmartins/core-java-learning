package com.example.functional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.util.function.*;

class FunctionalProgrammingExamplesTest {

  private FunctionalProgrammingExamples fpExamples;

  @BeforeEach
  void setUp() {
    fpExamples = new FunctionalProgrammingExamples();
  }

  @Test
  @DisplayName("Should apply custom predicate to filter list")
  void testApplyPredicateToFilter() {
    // Given
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    Predicate<Integer> isEven = n -> n % 2 == 0;

    // When
    List<Integer> result = fpExamples.applyPredicateToFilter(numbers, isEven);

    // Then
    assertEquals(Arrays.asList(2, 4, 6, 8, 10), result,
            "Should filter only even numbers");

    // Test with different predicate
    Predicate<Integer> greaterThanFive = n -> n > 5;
    List<Integer> greaterThan5 = fpExamples.applyPredicateToFilter(numbers, greaterThanFive);
    assertEquals(Arrays.asList(6, 7, 8, 9, 10), greaterThan5,
            "Should filter numbers greater than 5");
  }

  @Test
  @DisplayName("Should apply function to transform each element")
  void testApplyFunctionToTransform() {
    // Given
    List<String> words = Arrays.asList("hello", "world", "java", "functional");
    Function<String, Integer> lengthFunction = String::length;

    // When
    List<Integer> lengths = fpExamples.applyFunctionToTransform(words, lengthFunction);

    // Then
    assertEquals(Arrays.asList(5, 5, 4, 10), lengths,
            "Should return length of each word");

    // Test with different function
    Function<String, String> upperCaseFunction = String::toUpperCase;
    List<String> upperCase = fpExamples.applyFunctionToTransform(words, upperCaseFunction);
    assertEquals(Arrays.asList("HELLO", "WORLD", "JAVA", "FUNCTIONAL"), upperCase,
            "Should convert all words to uppercase");
  }

  @Test
  @DisplayName("Should compose two functions and apply them")
  void testComposeFunctions() {
    // Given
    Function<String, Integer> lengthFunc = String::length;
    Function<Integer, Integer> squareFunc = n -> n * n;
    String input = "hello";

    // When
    Integer result = fpExamples.composeFunctions(lengthFunc, squareFunc, input);

    // Then
    assertEquals(25, result, "Should first get length (5) then square it (25)");

    // Test with different composition
    Function<String, String> upperFunc = String::toUpperCase;
    Function<String, Integer> lengthFunc2 = String::length;
    Integer result2 = fpExamples.composeFunctions(upperFunc, lengthFunc2, "test");
    assertEquals(4, result2, "Should convert to upper then get length");
  }

  @Test
  @DisplayName("Should apply consumer to perform action on each element")
  void testApplyConsumerToEach() {
    // Given
    List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
    List<String> results = new ArrayList<>();
    Consumer<String> addToList = name -> results.add("Hello, " + name);

    // When
    fpExamples.applyConsumerToEach(names, addToList);

    // Then
    assertEquals(3, results.size(), "Should process all 3 names");
    assertEquals(Arrays.asList("Hello, Alice", "Hello, Bob", "Hello, Charlie"), results,
            "Should add greeting to each name");
  }

  @Test
  @DisplayName("Should use supplier to generate values")
  void testUseSupplierToGenerate() {
    // Given
    Supplier<Integer> randomSupplier = () -> 42;
    int count = 5;

    // When
    List<Integer> generated = fpExamples.useSupplierToGenerate(randomSupplier, count);

    // Then
    assertEquals(5, generated.size(), "Should generate 5 values");
    assertTrue(generated.stream().allMatch(n -> n == 42),
            "All values should be 42");

    // Test with UUID supplier
    Supplier<String> uuidSupplier = () -> UUID.randomUUID().toString();
    List<String> uuids = fpExamples.useSupplierToGenerate(uuidSupplier, 3);
    assertEquals(3, uuids.size(), "Should generate 3 UUIDs");
    assertEquals(3, new HashSet<>(uuids).size(), "All UUIDs should be unique");
  }

  @Test
  @DisplayName("Should chain predicates with AND logic")
  void testChainPredicatesWithAnd() {
    // Given
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
    IntPredicate isEven = n -> n % 2 == 0;
    IntPredicate greaterThanFive = n -> n > 5;

    // When
    List<Integer> result = fpExamples.chainPredicatesWithAnd(numbers, isEven, greaterThanFive);

    // Then
    assertEquals(Arrays.asList(6, 8, 10, 12), result,
            "Should return numbers that are both even AND greater than 5");
  }

  @Test
  @DisplayName("Should chain predicates with OR logic")
  void testChainPredicatesWithOr() {
    // Given
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    IntPredicate lessThanThree = n -> n < 3;
    IntPredicate greaterThanEight = n -> n > 8;

    // When
    List<Integer> result = fpExamples.chainPredicatesWithOr(numbers, lessThanThree, greaterThanEight);

    // Then
    assertEquals(Arrays.asList(1, 2, 9, 10), result,
            "Should return numbers less than 3 OR greater than 8");
  }

  @Test
  @DisplayName("Should use BiFunction to combine two inputs")
  void testApplyBiFunction() {
    // Given
    BiFunction<Integer, Integer, Integer> multiply = (a, b) -> a * b;

    // When
    Integer result = fpExamples.applyBiFunction(5, 7, multiply);

    // Then
    assertEquals(35, result, "Should multiply 5 * 7 = 35");

    // Test with different BiFunction
    BiFunction<String, String, String> concat = (a, b) -> a + " " + b;
    String stringResult = fpExamples.applyBiFunction("Hello", "World", concat);
    assertEquals("Hello World", stringResult, "Should concatenate strings");
  }

  @Test
  @DisplayName("Should use UnaryOperator to transform value of same type")
  void testApplyUnaryOperator() {
    // Given
    IntUnaryOperator square = n -> n * n;
    IntUnaryOperator addTen = n -> n + 10;

    // When
    Integer result = fpExamples.applyUnaryOperator(5, square);

    // Then
    assertEquals(25, result, "Should square 5 to get 25");

    // Test with different operator
    Integer result2 = fpExamples.applyUnaryOperator(5, addTen);
    assertEquals(15, result2, "Should add 10 to 5 to get 15");
  }

  @Test
  @DisplayName("Should chain multiple UnaryOperators")
  void testChainUnaryOperators() {
    // Given
    IntUnaryOperator multiplyByTwo = n -> n * 2;
    IntUnaryOperator addFive = n -> n + 5;
    IntUnaryOperator square = n -> n * n;
    Integer initial = 3;

    // When
    Integer result = fpExamples.chainUnaryOperators(initial, multiplyByTwo, addFive, square);

    // Then
    // (3 * 2) = 6, (6 + 5) = 11, (11 * 11) = 121
    assertEquals(121, result, "Should apply operators in sequence: multiply by 2, add 5, square");
  }

  @Test
  @DisplayName("Should use BinaryOperator to reduce list to single value")
  void testReduceWithBinaryOperator() {
    // Given
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
    IntBinaryOperator sum = Integer::sum;

    // When
    Integer result = fpExamples.reduceWithBinaryOperator(numbers, 0, sum);

    // Then
    assertEquals(15, result, "Should sum all numbers: 1+2+3+4+5 = 15");

    // Test with multiplication
    IntBinaryOperator multiply = (a, b) -> a * b;
    Integer product = fpExamples.reduceWithBinaryOperator(numbers, 1, multiply);
    assertEquals(120, product, "Should multiply all numbers: 1*2*3*4*5 = 120");
  }

  @Test
  @DisplayName("Should create and use custom functional interface")
  void testCustomFunctionalInterface() {
    // Given
    TriFunction<Integer, Integer, Integer, Integer> sumOfThree = (a, b, c) -> a + b + c;

    // When
    Integer result = fpExamples.applyTriFunction(5, 10, 15, sumOfThree);

    // Then
    assertEquals(30, result, "Should sum three numbers: 5+10+15 = 30");

    // Test with different operation
    TriFunction<String, String, String, String> concatThree =
            (a, b, c) -> a + "-" + b + "-" + c;
    String stringResult = fpExamples.applyTriFunction("Java", "is", "awesome", concatThree);
    assertEquals("Java-is-awesome", stringResult, "Should concatenate three strings");
  }

  @Test
  @DisplayName("Should use method reference with instance method")
  void testMethodReferenceInstanceMethod() {
    // Given
    List<String> words = Arrays.asList("  hello  ", "  world  ", "  java  ");

    // When
    List<String> trimmed = fpExamples.applyMethodReference(words, String::trim);

    // Then
    assertEquals(Arrays.asList("hello", "world", "java"), trimmed,
            "Should trim all strings using method reference");
  }

  @Test
  @DisplayName("Should use method reference with static method")
  void testMethodReferenceStaticMethod() {
    // Given
    List<String> numbers = Arrays.asList("123", "456", "789");

    // When
    List<Integer> parsed = fpExamples.applyMethodReference(numbers, Integer::parseInt);

    // Then
    assertEquals(Arrays.asList(123, 456, 789), parsed,
            "Should parse all strings to integers using method reference");
  }

  @Test
  @DisplayName("Should use constructor reference to create objects")
  void testConstructorReference() {
    // Given
    List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

    // When
    List<Person> people = fpExamples.createObjectsWithConstructor(names, Person::new);

    // Then
    assertEquals(3, people.size(), "Should create 3 Person objects");
    assertEquals("Alice", people.get(0).name(), "First person should be Alice");
    assertEquals("Bob", people.get(1).name(), "Second person should be Bob");
    assertEquals("Charlie", people.get(2).name(), "Third person should be Charlie");
  }

  @Test
  @DisplayName("Should curry a function (partial application)")
  void testCurryFunction() {
    // Given
    BiFunction<Integer, Integer, Integer> add = Integer::sum;
    Integer firstArg = 10;

    // When
    Function<Integer, Integer> addTen = fpExamples.curry(add, firstArg);

    // Then
    assertEquals(15, addTen.apply(5), "Should add 10 + 5 = 15");
    assertEquals(30, addTen.apply(20), "Should add 10 + 20 = 30");

    // Test with different operation
    BiFunction<String, String, String> concat = (a, b) -> a + b;
    Function<String, String> prependHello = fpExamples.curry(concat, "Hello ");
    assertEquals("Hello World", prependHello.apply("World"), "Should prepend 'Hello ' to input");
  }

  @Test
  @DisplayName("Should use Function andThen for chaining")
  void testFunctionAndThen() {
    // Given
    ToIntFunction<String> length = String::length;
    IntPredicate isEven = n -> n % 2 == 0;
    String input = "hello";

    // When
    Boolean result = fpExamples.useFunctionAndThen(input, length, isEven);

    // Then
    assertFalse(result, "Length of 'hello' is 5, which is odd");

    // Test with even length string
    Boolean result2 = fpExamples.useFunctionAndThen("test", length, isEven);
    assertTrue(result2, "Length of 'test' is 4, which is even");
  }

  @Test
  @DisplayName("Should filter and map using functional approach")
  void testFilterAndMapFunctional() {
    // Given
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    Predicate<Integer> isEven = n -> n % 2 == 0;
    Function<Integer, String> toSquareString = n -> String.valueOf(n * n);

    // When
    List<String> result = fpExamples.filterAndMap(numbers, isEven, toSquareString);

    // Then
    assertEquals(Arrays.asList("4", "16", "36", "64", "100"), result,
            "Should filter even numbers and square them as strings");
  }

  @Test
  @DisplayName("Should implement higher-order function that returns a function")
  void testHigherOrderFunction() {
    // Given
    String prefix = "Mr. ";

    // When
    Function<String, String> addPrefix = fpExamples.createPrefixFunction(prefix);

    // Then
    assertEquals("Mr. Smith", addPrefix.apply("Smith"), "Should add 'Mr. ' prefix");
    assertEquals("Mr. Johnson", addPrefix.apply("Johnson"), "Should add 'Mr. ' prefix");

    // Test with different prefix
    Function<String, String> addDr = fpExamples.createPrefixFunction("Dr. ");
    assertEquals("Dr. House", addDr.apply("House"), "Should add 'Dr. ' prefix");
  }
}