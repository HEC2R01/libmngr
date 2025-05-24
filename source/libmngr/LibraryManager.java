package libmngr;

import java.util.ArrayList;

public class LibraryManager {
  // TODO: Look into 'B Trees' for indexing and 'B+ Trees' for database storage
  //       if we have enough time (or motivation...).
  private BSTree<String, ArrayList<Book>> titleIndexer;
  private BSTree<String, ArrayList<Book>> authorIndexer;

  public LibraryManager() {
    Logger.info("Creating 'LibraryManager' instance...");
  
    this.titleIndexer = new BSTree<String, ArrayList<Book>>();
    this.authorIndexer = new BSTree<String, ArrayList<Book>>();
  }

  public void addBook(Book book) {
    Logger.info("========== [Adding Book] ==========");
    Logger.info("Title: %s", book.getTitle());
    Logger.info("Author: %s", book.getAuthor().toString());
    Logger.info("Genres: %s", book.getGenres().toString());
    Logger.info("Id: %d", book.getId());
    Logger.info("===================================");

    if (titleIndexer.search(book.getTitle()) == null) {
      titleIndexer.insert(book.getTitle(), new ArrayList<Book>());
    }
    titleIndexer.search(book.getTitle()).getValue().add(book);

    if (authorIndexer.search(book.getAuthor().toString()) == null) {
      authorIndexer.insert(book.getAuthor().toString(), new ArrayList<Book>());
    }
    authorIndexer.search(book.getAuthor().toString()).getValue().add(book);
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
    var node = titleIndexer.search(title);
    return node == null ? null : node.getValue();
  }

  public ArrayList<Book> getBooks(Author author) {
    var node = authorIndexer.search(author.toString());
    return node == null ? null : node.getValue();
  }
}
