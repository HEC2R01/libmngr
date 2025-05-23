package libmngr;

import java.util.Date;
import java.util.EnumSet;
import java.util.TreeSet;

public class Book {
  public enum Genre {
    NON_FICTION,
    FICTION,
    ROMANCE,
    THRILLER
  }

  private String title;
  private Author author;
  private Date releaseDate;
  private EnumSet<Genre> genres;

  private int id;
  // (Hector): The current iteration of out BSTree is limited to solely function as
  //           a map, not only that, but the nature of this fields usage will cause
  //           out current BSTree implementation to become unbalanced really quickly.
  //           As proud as I am with my BSTree, Java's red-black implementation seems
  //           like a no-brainer.
  private static TreeSet<Integer> idDatabase = new TreeSet<Integer>();

  public Book(String title, Author author, Date releaseDate, EnumSet<Genre> genres) {
    this.title = title;
    this.author = author;
    this.releaseDate = releaseDate;
    this.genres = genres;

    int i = 0;
    while (idDatabase.contains(i)) {
      i++;
    }

    id = i;
    idDatabase.add(i);
  }

  public String getTitle() { return this.title; }
  public Author getAuthor() { return this.author; }

  public int getId() { return this.id; }
}
