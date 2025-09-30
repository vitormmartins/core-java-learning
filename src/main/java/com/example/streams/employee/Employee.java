package com.example.streams.employee;

import lombok.NonNull;

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
  @NonNull
  public String toString() {
    return String.format("Employee{name='%s', department='%s', salary=%d}", name, department, salary);
  }
}