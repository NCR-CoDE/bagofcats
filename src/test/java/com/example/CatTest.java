package com.example;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class CatTest {

  @Test
  public void shouldFollowHashcodeEqualsContract() {
    EqualsVerifier.forClass(Cat.class).verify();
  }

}
