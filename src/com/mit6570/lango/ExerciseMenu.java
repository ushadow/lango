package com.mit6570.lango;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class ExerciseMenu {
  private List<ExerciseMenu> drills = new ArrayList<ExerciseMenu>();
  private String name, src;
  
  public ExerciseMenu(String name) {
    this.name = name;
  }
  
  public ExerciseMenu(String name, String src) {
    this.name = name;
    this.src = src;
  }
  
  public String name() { return name; }
  
  /**
   * Source file of the exercise. Can be null.
   * @return the path of the source file.
   */
  public String src() { return src; }
  public String toString() { return name; }
  public void add(ExerciseMenu d) { drills.add(d); }
  public ExerciseMenu getDrill(int position) {
    if (position >= drills.size()) 
      throw new InvalidParameterException(String.format("There are %d drills, but %d requested.", 
        drills.size(), position));
    return drills.get(position);
  }
  public int drillCount() { return drills.size(); }
}
