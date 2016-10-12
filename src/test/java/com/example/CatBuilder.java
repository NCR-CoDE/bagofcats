package com.example;

public class CatBuilder {
  private final Cat cat;

  public CatBuilder() {
    this(new Cat("fifi", 10, true, 4));
  }

  private CatBuilder(Cat cat) {
    this.cat = cat;
  }

  CatBuilder withName(String name) {
    return new CatBuilder(
        new Cat(name, cat.velocity(), cat.isAlive(), cat.legs()));
  }

  CatBuilder withVelocity(int velocity) {
    return new CatBuilder(
        new Cat(cat.name(), velocity, cat.isAlive(), cat.legs()));
  }

  CatBuilder withIsAlive(boolean isAlive) {
    return new CatBuilder(
        new Cat(cat.name(), cat.velocity(), isAlive, cat.legs()));
  }

  CatBuilder withLegs(int legs) {
    return new CatBuilder(
        new Cat(cat.name(), cat.velocity(), cat.isAlive(), legs));
  }

  public Cat build() {
    return cat;
  }

}
