package com.mit6570.lango.db;

import java.util.Collection;

import net.epsilonlabs.datamanagementefficient.annotations.Id;

public class Course {
  
  public static class Lesson {
    @Id
    private int id;
    private String name;
    private Collection<Section> sections;
    
    public Lesson(String name, Collection<Section> sections) { 
      this.name = name;
      this.sections = sections;
    }
    
    public Lesson() {}
  }
  
  public static class Section {
    @Id
    private int id;
    private String name;
    private String url;
    
    public Section(String name, String url) {
      this.name = name;
      this.url = url;
    }
    
    public Section() {}
  }
  
  @Id
  private int id;
  private String name, hash, url;
  private Collection<Lesson> lessons;
  
  public Course(String name, String hash, String url, Collection<Lesson> lessons) {
    this.name = name;
    this.hash = hash;
    this.url = url;
    this.lessons = lessons;
  }
  
  public Course() {}
}
