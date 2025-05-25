# Download this project

Open a terminal to the location of where you want this project to be install and run `git clone [link of repository]` to download a local copy of this project.

> [!NOTE]
> Links should usually end with `.git`

![image](https://github.com/user-attachments/assets/3a156b65-e625-4722-b6c7-459c668cd260)
![image](https://github.com/user-attachments/assets/b8c94649-2e34-4ebf-9d9b-97093430404e)
![image](https://github.com/user-attachments/assets/ebb57bb5-4667-422c-9146-69b6ef4efcc9)


# Building

Build scripts have been provided to help with building. Open a terminal in the root directory and run `scripts\build`. The build file should be located in the "build" folder as a .jar file.
Copy and paste this jar file to the libraries folder of your project.

# Usage

In order to use the library, import the libmngr package: `import libmngr.*`

> [!NOTE]
> Expect false positives with your IDE's error detection

## The `Logger` Class

In order to have a flexible logging system, The `Logger` class must be initialized with an array of `PrintStreams` before the contruction of the `LibraryManager` class
ex.)
```
Logger.initialize(new PrintStream[] { System.out });
LibraryManager manager = new LibraryManager();
```

## The `Book` Class

The `Book` class represents a book object.

### Contruction

`new Book("Title", new Author("Author Name") new Date(2000, 12, 25), EnumSet.of(Book.Genre.FICTION, Book.Genre.ROMANCE));`
- Arg1(String): The title of the book.
- Arg2(Author): The author of the book.
- Arg3(Date): The release date of the book.
- Arg4(EnumSet<Book.Genre>): The genres of the book.

### Methods

- `String getTitle()`: Returns the title of the book.
- `Author getAuthor()`: Returns the author of the book. Call the `toString()` method of the `Author` class to convert it to a string.
- `EnumSet<Book.Genre> getGenres()`: Returns the genres of this book.
- `int getId()`: Returns the auto-genereated id of the book.

### Book.Genre

The `Book.Genre` enum contains the possible genres a book can have. This is meant to be used with Java's `EnumSet<T>` class to allow multiple `Book.Genre`s to be used in a book.

> [!NOTE]
> See `source/libmngr/Book.java` for available genres.

## The `LibraryManager` Class

The `LibraryManager` class is the core of the library.

### Methods

- `void addBook(Book book)`: Adds a book to the database.
  - `book`: The book to add.
- `void removeBook(Book book)`: Removes a book from the database.
  - `book`: The book to remove.
- `ArrayList<Book> getBooks(String title)`: Returns a list of books with the given title.
- `ArrayList<Book> getBooks(Author author)`: Returns a list of books with the given author.
- `ArrayList<Book> getBooks(int id)`: Returns a list of books with the given id. NOTE: This method is guaranteed to return a list with only one book as the books are guaranteed to have a unqiue id.
