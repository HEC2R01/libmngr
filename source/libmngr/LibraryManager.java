package libmngr;

public class LibraryManager {
  // TODO: Look into 'B Trees' for indexing and 'B+ Trees' for database storage
  //       if we have enough time (or motivation...).
  private BSTree<String, Integer> titleIndexer;
  private BSTree<Author, Integer> authorIndexer;
  private BSTree<Integer, Book> bookDatabase;

  public LibraryManager() {
    Logger.info("Creating 'LibraryManager' instance...");
  
    this.titleIndexer = new BSTree<String, Integer>();
    this.authorIndexer = new BSTree<Author, Integer>();
    this.bookDatabase = new BSTree<Integer, Book>();
  }

  public void addBook(Book book) {
    this.titleIndexer.insert(book.getTitle(), book.getId());
    this.authorIndexer.insert(book.getAuthor(), book.getId());
    this.bookDatabase.insert(book.getId(), book);
  }

  public Book getBook(String title) {
    var n = titleIndexer.search(title);
    if (n == null) {
      return null;
    }

    return bookDatabase.search(n.getValue()).getValue();
  }

  public Book getBook(Author author) {
    var n = authorIndexer.search(author);
    if (n == null) {
      return null;
    }

    return bookDatabase.search(n.getValue()).getValue();
  }
}
