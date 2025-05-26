package libmngr;

import java.util.ArrayList;
import java.util.TreeMap;

public class LibraryManager {
  // TODO: Look into 'B Trees' for indexing and 'B+ Trees' for database storage
  //       if we have enough time (or motivation...).
  private TreeMap<String, ArrayList<Book>> titleIndexer;
  private TreeMap<String, ArrayList<Book>> authorIndexer;
  private TreeMap<Integer, ArrayList<Book>> idIndexer;

  public LibraryManager() {
    Logger.info("Creating 'LibraryManager' instance...");
  
    this.titleIndexer = new TreeMap<>();
    this.authorIndexer = new TreeMap<>();
    this.idIndexer = new TreeMap<>();
  }

  public void addBook(Book book) {
    Logger.info("========== [Adding Book] ==========");
    Logger.info("Title: %s", book.getTitle());
    Logger.info("Author: %s", book.getAuthor().toString());
    Logger.info("Genres: %s", book.getGenres().toString());
    Logger.info("Id: %d", book.getId());
    Logger.info("===================================");

    if (!titleIndexer.containsKey(book.getTitle())) {
      titleIndexer.put(book.getTitle(), new ArrayList<>());
    }
    titleIndexer.get(book.getTitle()).add(book);

    if (!authorIndexer.containsKey(book.getAuthor().toString())) {
      authorIndexer.put(book.getAuthor().toString(), new ArrayList<>());
    }
    authorIndexer.get(book.getAuthor().toString()).add(book);
    
    if (!idIndexer.containsKey(book.getId())) {
      idIndexer.put(book.getId(), new ArrayList<>());
    }
    idIndexer.get(book.getId()).add(book);
  }

  public void removeBook(Book book) {
    Logger.info("========= [Removing Book] =========");
    Logger.info("Title: %s", book.getTitle());
    Logger.info("Author: %s", book.getAuthor().toString());
    Logger.info("Genres: %s", book.getGenres().toString());
    Logger.info("Id: %d", book.getId());
    Logger.info("===================================");

    this.titleIndexer.remove(book.getTitle());
    this.authorIndexer.remove(book.getAuthor().toString());
  }

  public ArrayList<Book> getBooks(String title) {
    return titleIndexer.containsKey(title) ? titleIndexer.get(title) : null;
  }

  public ArrayList<Book> getBooks(Author author) {
    return authorIndexer.containsKey(author.toString()) ? authorIndexer.get(author.toString()) : null;
  }

  public ArrayList<Book> getBooks(int id) {
    return idIndexer.containsKey(id) ? idIndexer.get(id) : null;
  }
}
