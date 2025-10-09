package com.example.functional;

import lombok.NonNull;

public record Person(String name) {

  @Override
  @NonNull
  public String toString() {
    return "Person{name='" + name + "'}";
  }
}