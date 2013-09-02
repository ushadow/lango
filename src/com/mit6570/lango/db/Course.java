package com.mit6570.lango.db;

import net.epsilonlabs.datamanagementefficient.annotations.Id;

public class Course {
  @Id
  private int id;
  private String title, hash, url;
  
  public Course(String title, String hash, String url) {
    this.title = title;
    this.hash = hash;
    this.url = url;
  }
  
  public Course() {}
}
