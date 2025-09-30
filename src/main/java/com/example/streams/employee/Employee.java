package com.example.streams.employee;

import java.util.ArrayList;
import java.util.List;

/**
 * @param name Getters
 */
public record Employee(String name, String department, int salary, List<String> skills) {
  public Employee(String name, String department, int salary) {
    this(name, department, salary, new ArrayList<>());
  }

  @Override
  public String toString() {
    return StringTemplate.STR."Employee{name='\{name}', department='\{department}', salary=\{salary}}";
  }
}