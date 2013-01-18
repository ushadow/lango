package com.mit6570.lango;

import java.util.ArrayList;
import java.util.List;

public class Exercise {
  private List<Exercise> drills = new ArrayList<Exercise>();
  private String name;
  
  public Exercise(String name) {
    this.name = name;
  }
  
  public String name() { return name; }
  public String toString() { return name; }
  public void add(Exercise d) { drills.add(d); }
}
