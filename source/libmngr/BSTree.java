package libmngr;

// (Hector): I am aware of the existence of the much better 'TreeSet' and the 'TreeMap'
//           from the java.util package, but wheres the fun in that.
public class BSTree<Key extends Comparable<Key>, T> {
  public class Node {
    private Key key;
    private T value;

    private Node left;
    private Node right;

    public Node(Key key, T value) {
      this.key = key;
      this.value = value;

      this.left = null;
      this.right = null;
    }

    public T getValue() { return this.value; }
  }

  private Node root;
  
  public BSTree() {
    this.root = null;
  }

  public void insert(Key key, T value) {
    this.root = insert(this.root, key, value); 
  }

  private Node insert(Node root, Key key, T value) {
    if (root == null) {
      return root = new Node(key, value);
    }

    if (key.compareTo(root.key) > 0) {
      root.right = insert(root.right, key, value);
    } else if (key.compareTo(root.key) < 0) {
      root.left = insert(root.left, key, value);
    }

    return root;
  }

  public void remove(Key key) {
    this.root = remove(this.root, key);
  }

  private Node remove(Node root, Key key) {
    if (root == null) {
      return root;
    }

    if (key.compareTo(root.key) > 0) {
      root.right = remove(root.right, key);
    } else if (key.compareTo(root.key) < 0) {
      root.left = remove(root.left, key);
    } else {
      if (root.left == null) {
        return root.right;
      } else if (root.right == null) {
        return root.left;
      }

      root.key = findMinimumValue(root.right);
      root.right = remove(root.right, root.key);
    }

    return root;
  }

  private Key findMinimumValue(Node root) {
    Key i = root.key;
    while (root.left != null) {
      i = root.left.key;
      root = root.left;
    }

    return i;
  }

  public Node search(Key key) {
    return search(this.root, key);
  }

  private Node search(Node root, Key key) {
    if (root == null) { return null; }
    if (key == root.key) { return root; }

    if (key.compareTo(root.key) > 0) {
      return search(root.right, key);
    }

    return search(root.left, key);
  }
}
