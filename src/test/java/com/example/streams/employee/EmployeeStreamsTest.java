package com.example.streams.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class EmployeeStreamsTest {

  private EmployeeStreams employeeStreams;

  @BeforeEach
  void setUp() {
    employeeStreams = new EmployeeStreams();
  }

  @Test
  @DisplayName("Should group employees by department and count them")
  void testGroupAndCountByDepartment() {
    // Given
    List<Employee> employees = Arrays.asList(
            new Employee("Alice", "Engineering", 75000),
            new Employee("Bob", "Engineering", 80000),
            new Employee("Charlie", "Sales", 60000),
            new Employee("Diana", "Sales", 65000),
            new Employee("Eve", "HR", 55000)
    );

    // When
    Map<String, Long> departmentCounts = employeeStreams.groupAndCountByDepartment(employees);

    // Then
    assertEquals(3, departmentCounts.size(), "Should have 3 departments");
    assertEquals(2L, departmentCounts.get("Engineering"), "Engineering should have 2 employees");
    assertEquals(2L, departmentCounts.get("Sales"), "Sales should have 2 employees");
    assertEquals(1L, departmentCounts.get("HR"), "HR should have 1 employee");
  }

  @Test
  @DisplayName("Should find top N highest salaries")
  void testFindTopNSalaries() {
    // Given
    List<Employee> employees = Arrays.asList(
            new Employee("Alice", "Engineering", 75000),
            new Employee("Bob", "Engineering", 95000),
            new Employee("Charlie", "Sales", 60000),
            new Employee("Diana", "Sales", 85000),
            new Employee("Eve", "HR", 55000),
            new Employee("Frank", "Engineering", 90000)
    );

    // When
    List<Integer> top3Salaries = employeeStreams.findTopNSalaries(employees, 3);

    // Then
    assertEquals(Arrays.asList(95000, 90000, 85000), top3Salaries,
            "Should return top 3 salaries in descending order");

    // Edge case: N larger than list size
    List<Integer> topAll = employeeStreams.findTopNSalaries(employees, 10);
    assertEquals(6, topAll.size(), "Should return all salaries when N > list size");
  }

  @Test
  @DisplayName("Should partition employees by salary threshold")
  void testPartitionBySalaryThreshold() {
    // Given
    List<Employee> employees = Arrays.asList(
            new Employee("Alice", "Engineering", 75000),
            new Employee("Bob", "Engineering", 95000),
            new Employee("Charlie", "Sales", 60000),
            new Employee("Diana", "Sales", 85000),
            new Employee("Eve", "HR", 55000)
    );
    int threshold = 70000;

    // When
    Map<Boolean, List<Employee>> partitioned =
            employeeStreams.partitionBySalaryThreshold(employees, threshold);

    // Then
    assertEquals(3, partitioned.get(true).size(),
            "Should have 3 employees above threshold");
    assertEquals(2, partitioned.get(false).size(),
            "Should have 2 employees below threshold");
    assertTrue(partitioned.get(true)
                          .stream()
                          .allMatch(e -> e.salary() >= threshold),
            "All high earners should be >= threshold");
  }

  @Test
  @DisplayName("Should calculate average salary by department")
  void testCalculateAverageSalaryByDepartment() {
    // Given
    List<Employee> employees = Arrays.asList(
            new Employee("Alice", "Engineering", 80000),
            new Employee("Bob", "Engineering", 90000),
            new Employee("Charlie", "Sales", 60000),
            new Employee("Diana", "Sales", 70000),
            new Employee("Eve", "HR", 55000)
    );

    // When
    Map<String, Double> avgSalaries =
            employeeStreams.calculateAverageSalaryByDepartment(employees);

    // Then
    assertEquals(85000.0, avgSalaries.get("Engineering"), 0.01,
            "Engineering average should be 85000");
    assertEquals(65000.0, avgSalaries.get("Sales"), 0.01,
            "Sales average should be 65000");
    assertEquals(55000.0, avgSalaries.get("HR"), 0.01,
            "HR average should be 55000");
  }

  @Test
  @DisplayName("Should flatten and extract unique skills from all employees")
  void testExtractUniqueSkills() {
    // Given
    List<Employee> employees = Arrays.asList(
            new Employee("Alice", "Engineering", 75000,
                    Arrays.asList("Java", "Python", "AWS")),
            new Employee("Bob", "Engineering", 80000,
                    Arrays.asList("Java", "Kubernetes", "Docker")),
            new Employee("Charlie", "Sales", 60000,
                    Arrays.asList("Salesforce", "Excel")),
            new Employee("Diana", "Engineering", 85000,
                    Arrays.asList("Python", "Docker", "AWS"))
    );

    // When
    Set<String> uniqueSkills = employeeStreams.extractUniqueSkills(employees);

    // Then
    assertEquals(7, uniqueSkills.size(), "Should have 7 unique skills");
    assertTrue(uniqueSkills.containsAll(
                    Arrays.asList("Java", "Python", "AWS", "Kubernetes", "Docker", "Salesforce", "Excel")),
            "Should contain all unique skills");
  }

  @Test
  @DisplayName("Should find employees with salary in given range")
  void testFindEmployeesInSalaryRange() {
    // Given
    List<Employee> employees = Arrays.asList(
            new Employee("Alice", "Engineering", 75000),
            new Employee("Bob", "Engineering", 95000),
            new Employee("Charlie", "Sales", 60000),
            new Employee("Diana", "Sales", 85000),
            new Employee("Eve", "HR", 55000)
    );

    // When
    List<String> namesInRange =
            employeeStreams.findEmployeesInSalaryRange(employees, 70000, 90000);

    // Then
    assertEquals(Arrays.asList("Alice", "Diana"), namesInRange,
            "Should return names of employees in salary range [70000-90000]");
  }

  @Test
  @DisplayName("Should join employee names by department with custom delimiter")
  void testJoinNamesByDepartment() {
    // Given
    List<Employee> employees = Arrays.asList(
            new Employee("Alice", "Engineering", 75000),
            new Employee("Bob", "Engineering", 80000),
            new Employee("Charlie", "Sales", 60000),
            new Employee("Diana", "Sales", 65000),
            new Employee("Eve", "HR", 55000)
    );

    // When
    Map<String, String> departmentNames =
            employeeStreams.joinNamesByDepartment(employees, " | ");

    // Then
    assertEquals("Alice | Bob", departmentNames.get("Engineering"),
            "Engineering names should be joined with ' | '");
    assertEquals("Charlie | Diana", departmentNames.get("Sales"),
            "Sales names should be joined with ' | '");
    assertEquals("Eve", departmentNames.get("HR"),
            "HR should have single name");
  }

  @Test
  @DisplayName("Should find second highest salary (Kth largest problem)")
  void testFindSecondHighestSalary() {
    // Given
    List<Employee> employees = Arrays.asList(
            new Employee("Alice", "Engineering", 75000),
            new Employee("Bob", "Engineering", 95000),
            new Employee("Charlie", "Sales", 60000),
            new Employee("Diana", "Sales", 95000), // duplicate highest
            new Employee("Eve", "HR", 85000)
    );

    // When
    Optional<Integer> secondHighest = employeeStreams.findSecondHighestSalary(employees);

    // Then
    assertTrue(secondHighest.isPresent(), "Second highest should exist");
    assertEquals(85000, secondHighest.get(), "Second highest should be 85000");

    // Edge case: only one unique salary
    List<Employee> singleSalary = Arrays.asList(
            new Employee("Alice", "Engineering", 75000),
            new Employee("Bob", "Engineering", 75000)
    );
    Optional<Integer> noSecond = employeeStreams.findSecondHighestSalary(singleSalary);
    assertFalse(noSecond.isPresent(), "Should return empty when only one unique salary");
  }

  @Test
  @DisplayName("Should convert list of employees to map with name as key")
  void testConvertToMapByName() {
    // Given
    List<Employee> employees = Arrays.asList(
            new Employee("Alice", "Engineering", 75000),
            new Employee("Bob", "Engineering", 80000),
            new Employee("Charlie", "Sales", 60000)
    );

    // When
    Map<String, Employee> employeeMap = employeeStreams.convertToMapByName(employees);

    // Then
    assertEquals(3, employeeMap.size(), "Map should have 3 entries");
    assertEquals("Engineering", employeeMap.get("Alice").department(),
            "Alice should be in Engineering");
    assertEquals(80000, employeeMap.get("Bob").salary(),
            "Bob's salary should be 80000");
  }

  @Test
  @DisplayName("Should find employees with names starting with given prefix")
  void testFindEmployeesByNamePrefix() {
    // Given
    List<Employee> employees = Arrays.asList(
            new Employee("Alice", "Engineering", 75000),
            new Employee("Andrew", "Sales", 60000),
            new Employee("Bob", "Engineering", 80000),
            new Employee("Amanda", "HR", 55000),
            new Employee("Charlie", "Sales", 65000)
    );

    // When
    List<Employee> startsWithA = employeeStreams.findEmployeesByNamePrefix(employees, "A");

    // Then
    assertEquals(3, startsWithA.size(), "Should find 3 employees starting with 'A'");
    assertTrue(startsWithA.stream().allMatch(e -> e.name().startsWith("A")),
            "All names should start with 'A'");

    // Case sensitivity test
    List<Employee> lowercase = employeeStreams.findEmployeesByNamePrefix(employees, "a");
    assertEquals(0, lowercase.size(), "Should be case-sensitive by default");
  }

}