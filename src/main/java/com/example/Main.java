package com.example;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
        // click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@Slf4j
@NoArgsConstructor
public class Main {
  static void main() {
    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    log.info("Hello and welcome!");

    for (int i = 1; i <= 5; i++) {
      //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
      // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
      log.info("i = " + i);
    }
  }
}