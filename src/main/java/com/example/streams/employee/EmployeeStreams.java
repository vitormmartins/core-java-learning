package com.example.streams.employee;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class EmployeeStreams {
  public Map<String, Long> groupAndCountByDepartment(List<Employee> employees) {
    return employees.stream()
            .collect(groupingBy(Employee::department, counting()));
  }

  public List<Integer> findTopNSalaries(List<Employee> employees, int i) {
    return employees.stream()
            .map(Employee::salary)
            .distinct()
            .sorted((a, b) -> b - a)
            .limit(i)
            .toList();
  }

  public Map<Boolean, List<Employee>> partitionBySalaryThreshold(List<Employee> employees, int threshold) {
    return employees.stream()
            .collect(partitioningBy(e -> e.salary() > threshold));
  }

  public Map<String, Double> calculateAverageSalaryByDepartment(List<Employee> employees) {
    return employees.stream()
            .collect(groupingBy(Employee::department, averagingInt(Employee::salary)));
  }

  public Set<String> extractUniqueSkills(List<Employee> employees) {
    return employees.stream()
            .flatMap(employee -> employee.skills().stream())
            .collect(Collectors.toSet());
  }

  public List<String> findEmployeesInSalaryRange(List<Employee> employees, int i, int i1) {
    return employees.stream()
            .filter(employee -> employee.salary() >= i && employee.salary() <= i1)
            .map(Employee::name)
            .toList();
  }

  public Map<String, String> joinNamesByDepartment(List<Employee> employees, String s) {
    return employees.stream()
            .collect(groupingBy(Employee::department,
                    mapping(Employee::name,
                            joining(s)))
            );
  }

  public Optional<Integer> findSecondHighestSalary(List<Employee> employees) {
    return employees.stream()
            .map(Employee::salary)
            .distinct()
            .sorted((a, b) -> b - a)
            .skip(1)
            .findFirst();
  }

  // collectingAndThen() is a collector that performs a finishing transformation after collecting.
  // In the code below, it first collects all Employee objects with the same name into a List<Employee>
  // (using toList()), then applies List::getFirst to get the first Employee from that list.
  // So, for each unique name, it keeps only the first Employee found with that name, resulting
  // in a Map<String, Employee> where the key is the name and the value is the first Employee with
  // that name.
  public Map<String, Employee> convertToMapByName(List<Employee> employees) {
    return employees.stream()
            .collect(groupingBy(Employee::name,
                    collectingAndThen(toList(), List::getFirst)));
  }

  public List<Employee> findEmployeesByNamePrefix(List<Employee> employees, String prefix) {
    return employees.stream()
            .filter(e -> e.name().startsWith(prefix))
            .toList();
  }

}
