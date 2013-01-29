package com.mit6570.lango;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;

public class Utils {
  /**
   * Returns a map of the attributes and their corresponding values of the current tag.
   * @param parser
   * @return
   */
  public static Map<String, String> attributes(XmlPullParser parser) {
    Map<String, String> res = new HashMap<String, String>();
    for (int i = 0; i < parser.getAttributeCount(); i++) {
      String attrName = parser.getAttributeName(i);
      res.put(attrName, parser.getAttributeValue(i));
    }
    return res;
  }
  
  static String join(String delimiter, String... s) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < s.length - 1; i++) {
      builder.append(s[i]);
      builder.append(delimiter);
    }
    builder.append(s[s.length - 1]);
    return builder.toString();
  }
  
  static String join(String delimiter, List<String> s) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < s.size() - 1; i++) {
      builder.append(s.get(i));
      builder.append(delimiter);
    }
    builder.append(s.get(s.size() - 1));
    return builder.toString();
  }
}
