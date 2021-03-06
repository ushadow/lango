package com.mit6570.lango;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data structure of an exercise menu item.
 * @author yingyin
 *
 */
public class ExerciseMenu {
  private List<ExerciseMenu> drills = new ArrayList<ExerciseMenu>();
  private String name, src;
  private ExerciseMenu parent;
  
  public ExerciseMenu(String name) {
    this.name = name;
  }
  
  public ExerciseMenu(String name, String src) {
    this(name);
    this.src = src;
  }
  
  public String name() { return name; }
  
  public ExerciseMenu parent() { return parent; }
  
  public List<ExerciseMenu> drills() { return drills; }
  
  /**
   * Source file of the exercise. Can be null.
   * @return the path of the source file.
   */
  public String src() { return src; }
  public String toString() { return name; }
  
  public void add(ExerciseMenu d) { 
    drills.add(d); 
    d.parent = this;
  }
  
  public ExerciseMenu getDrill(int position) {
    if (position >= drills.size()) 
      throw new InvalidParameterException(String.format("There are %d drills, but %d requested.", 
        drills.size(), position));
    return drills.get(position);
  }
  
  public int drillCount() { return drills.size(); }
  
  boolean isDrill() { return drills.size() == 0; }
}
