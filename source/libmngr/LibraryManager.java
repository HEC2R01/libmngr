package libmngr;

public class LibraryManager {
  // TODO: Look into 'B Trees' for indexing and 'B+ Trees' for database storage
  //       if we have enough time (or motivation...).
  private BSTree<String, Integer> titleIndexer;
  private BSTree<String, Integer> authorIndexer;
  private BSTree<Integer, Book> bookDatabase;

  public LibraryManager() {
    Logger.info("Creating 'LibraryManager' instance...");
  
    this.titleIndexer = new BSTree<String, Integer>();
    this.authorIndexer = new BSTree<String, Integer>();
    this.bookDatabase = new BSTree<Integer, Book>();
  }

  public void addBook(Book book) {
    Logger.info("========== [Adding Book] ==========");
    Logger.info("Title: %s", book.getTitle());
    Logger.info("Author: %s", book.getAuthor().toString());
    Logger.info("Genres: %s", book.getGenres().toString());
    Logger.info("Id: %d", book.getId());
    Logger.info("===================================");

    this.titleIndexer.insert(book.getTitle(), book.getId());
    this.authorIndexer.insert(book.getAuthor().toString(), book.getId());
    this.bookDatabase.insert(book.getId(), book);
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
    this.bookDatabase.remove(book.getId());
  }

  public Book getBook(String title) {
    var n = titleIndexer.search(title);
    if (n == null) {
      return null;
    }

    return bookDatabase.search(n.getValue()).getValue();
  }

  public Book getBook(Author author) {
    var n = authorIndexer.search(author.toString());
    if (n == null) {
      return null;
    }

    return bookDatabase.search(n.getValue()).getValue();
  }
}
