package com.mit6570.lango;

import java.security.InvalidParameterException;
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
  public Exercise getDrill(int position) {
    if (position >= drills.size()) 
      throw new InvalidParameterException(String.format("There are %d drills, but %d requested.", 
        drills.size(), position));
    return drills.get(position);
  }
  public int drillCount() { return drills.size(); }
}
