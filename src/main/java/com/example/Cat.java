package com.example;

public final class Cat {

  private final String name;
  private final int velocity;
  private final boolean isAlive;
  private final int legs;

  public Cat(String name, int velocity, boolean isAlive, int legs) {
    this.velocity = velocity;
    this.name = name;
    this.isAlive = isAlive;
    this.legs = legs;
  }

  public String name() {
    return this.name;
  }

  public int velocity() {
    return this.velocity;
  }

  public boolean isAlive() {
    return this.isAlive;
  }

  public int legs() {
    return this.legs;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (isAlive ? 1231 : 1237);
    result = prime * result + legs;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + velocity;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Cat other = (Cat) obj;
    if (isAlive != other.isAlive)
      return false;
    if (legs != other.legs)
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (velocity != other.velocity)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Cat [name=" + name + ", velocity=" + velocity + ", isAlive="
        + isAlive + ", legs=" + legs + "]";
  }
  
  

}
