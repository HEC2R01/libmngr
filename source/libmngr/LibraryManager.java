package libmngr;

public class LibraryManager {
  // TODO: Look into 'B Trees' for indexing and 'B+ Trees' for database storage
  //       if we have enough time (or motivation...).
  private BSTree<String, Book> titleIndexer;
  private BSTree<String, Book> authorIndexer;

  public LibraryManager() {
    Logger.info("Creating 'LibraryManager' instance...");
  
    this.titleIndexer = new BSTree<String, Book>();
    this.authorIndexer = new BSTree<String, Book>();
  }

  public void addBook(Book book) {
    Logger.info("========== [Adding Book] ==========");
    Logger.info("Title: %s", book.getTitle());
    Logger.info("Author: %s", book.getAuthor().toString());
    Logger.info("Genres: %s", book.getGenres().toString());
    Logger.info("Id: %d", book.getId());
    Logger.info("===================================");

    this.titleIndexer.insert(book.getTitle(), book);
    this.authorIndexer.insert(book.getAuthor().toString(), book);
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

  public Book getBook(String title) {
    var node = titleIndexer.search(title);
    return node == null ? null : node.getValue();
  }

  public Book getBook(Author author) {
    var node = authorIndexer.search(author.toString());
    return node == null ? null : node.getValue();
  }
}
