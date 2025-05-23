package libmngr;

import java.lang.Comparable;

public class Author implements Comparable<Author> {
  private String name;

  public Author(String name) {
    this.name = name;
  }

  @Override
  public int compareTo(Author o) {
    return name.compareTo(o.name);
  } 

  public String getName() { return name; }
  public Author setName(String name) { this.name = name; return this; }

  public String toString() { return name; }
}
