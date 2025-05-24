package libmngr;

public class Author {
  private String name;

  public Author(String name) {
    this.name = name;
  }

  public String getName() { return name; }
  public Author setName(String name) { this.name = name; return this; }

  public String toString() { return name; }
}
