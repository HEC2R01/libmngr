package libmngr;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.plaf.nimbus.NimbusStyle;

public class BPlusTree<Key extends Comparable<Key>, T> {
  public class Node {
    private final int minDegree;

    private int keyCount;
    private final ArrayList<Key> keys;
    private final ArrayList<T> values;
    private final ArrayList<Node> children;

    private final boolean isLeaf;
    private final Node next;

    public Node(int minDegree, boolean isLeaf) {
      this.minDegree = minDegree;

      this.keyCount = 0;
      this.keys = new ArrayList<Key>(Collections.nCopies((2 * minDegree) - 1, null));
      this.values = new ArrayList<T>(Collections.nCopies(this.keys.size(), null));
      this.children = new ArrayList<Node>(Collections.nCopies(2 * minDegree, null));

      this.isLeaf = isLeaf;
      this.next = null;
    }

    public int findKeyIndex(Key key) {
      int i = 0;
      while (i < this.keyCount && key.compareTo(keys.get(i)) > 0) {
        i++;
      }

      return i;
    }

    public T getValue(Key key) {
      for (int i = 0; i < keys.size(); i++) {
        if (key.compareTo(keys.get(i)) == 0) {
          return values.get(i);
        }
      }

      return null;
    }
  }

  private final int minimumDegree;
  private Node root;

  public BPlusTree(int minimumDegree) {
    this.minimumDegree = minimumDegree;
    this.root = new Node(this.minimumDegree, true);
  }

  public void insert(Key key, T value) {
    if (this.root.keyCount == (2 * this.minimumDegree) - 1) {
      Node newRoot = new Node(this.minimumDegree, false);
      newRoot.children.set(0, this.root);

      splitChild(newRoot, this.root, 0);
      insert(newRoot, key, value);

      this.root = newRoot;
    } else {
      insert(root, key, value);
    }
  }

  private void insert(Node node, Key key, T value) {
    int i = node.keyCount - 1;
    if (node.isLeaf) {
      while (i >= 0 && node.keys.get(i).compareTo(key) > 0) {
        node.keys.set(i + 1, node.keys.get(i));
        node.values.set(i + 1, node.values.get(i));
        i--;
      }

      node.keys.set(i + 1, key);
      node.values.set(i + 1, value);
      node.keyCount++;
    } else {
      while (i >= 0 && node.keys.get(i).compareTo(key) > 0) {
        i--;
      }

      i++;
      if (node.children.get(i).keyCount == (2 * node.minDegree) - 1) {
        splitChild(node, node.children.get(i), i);
        if (node.keys.get(i).compareTo(key) < 0) {
          i++;
        }
      }

      insert(node.children.get(i), key, value);
    }
  }

  private void splitChild(Node parent, Node child, int i) {
    Node newChild = new Node(child.minDegree, child.isLeaf);
    newChild.keyCount = child.minDegree - 1;

    for (int j = 0; j < child.minDegree - 1; j++) {
      newChild.keys.set(j, child.keys.get(j + child.minDegree));
      newChild.values.set(j, child.values.get(j + child.minDegree));
    }

    if (!child.isLeaf) {
      for (int j = 0; j < child.minDegree; j++) {
        newChild.children.set(j, child.children.get(j + child.minDegree));
      }
    }
    child.keyCount = child.minDegree - 1;

    for (int j = parent.keyCount; j >= i + 1; j--) {
      parent.children.set(j + 1, parent.children.get(j));
    }
    parent.children.set(i + 1, newChild);

    for (int j = parent.keyCount - 1; j >= i; j--) {
      parent.keys.set(j + 1, parent.keys.get(j));
      parent.values.set(j + 1, parent.values.get(j));
    }
    parent.keys.set(i, child.keys.get(child.minDegree - 1));
    parent.values.set(i, child.values.get(child.minDegree - 1));
    parent.keyCount++;
  }

  public void delete(Key key) {
    delete(this.root, key);
    if (this.root.keyCount == 0 && !this.root.isLeaf) {
      this.root = this.root.children.get(0);
    }
  }

  private void delete(Node node, Key key) {
    int i = node.findKeyIndex(key);
    if (i < node.keyCount && node.keys.get(i).compareTo(key) == 0) {
      if (node.isLeaf) {
        for (int j = i + 1; j < node.keyCount; ++j) {
          node.keys.set(j - 1, node.keys.get(j));
        }

        node.keyCount--;
      } else {
        Key predecessor = getNodePredecessor(node, i);
        node.keys.set(i, predecessor);
        delete(node.children.get(i), predecessor);
      }
    } else {
      if (node.isLeaf) {
        Logger.info("Key '%s' not found", key.toString());
        return;
      }

      if (node.children.get(i).keyCount < node.minDegree) {
        if (i != 0 && node.children.get(i - 1).keyCount >= node.minDegree) {
          borrowFromPreviousNode(node, i);
        } else if (i != node.keyCount && node.children.get(i + 1).keyCount >= node.minDegree) {
          borrowFromNextNode(node, i);
        } else {
          if (i != node.keyCount) {
            merge(node, i);
          } else {
            merge(node, i - 1);
          }
        }
      }

      if (i == node.keyCount && i > node.keyCount) {
        delete(node.children.get(i -1), key);
      } else {
        delete(node.children.get(i), key);
      }
    }
  }

  private void borrowFromPreviousNode(Node node, int i) {
    Node child = node.children.get(i);
    Node sibling = node.children.get(i - 1);

    for (int j = child.keyCount - 1; j >= 0; --j) {
      child.keys.set(j + 1, child.keys.get(j));
    }

    if (!child.isLeaf) {
      for (int j = child.keyCount; j >= 0; --j) {
        child.children.set(j + 1, child.children.get(j));
      }
    }

    child.keys.set(0, node.keys.get(i - 1));

    if (!child.isLeaf) {
      child.children.set(0, sibling.children.get(sibling.keyCount));
    }

    node.keys.set(i - 1, sibling.keys.get(sibling.keyCount - 1));

    child.keyCount++;
    sibling.keyCount--;
  }

  private void borrowFromNextNode(Node node, int i) {
    Node child = node.children.get(i);
    Node sibling = node.children.get(i + 1);

    child.keys.set(child.keyCount, node.keys.get(i));

    if (!child.isLeaf) {
      child.children.set(child.keyCount + 1, sibling.children.get(0));
    }

    node.keys.set(i, sibling.keys.get(0));

    for (int j = 1; j < sibling.keyCount; ++j) {
      sibling.keys.set(j - 1, sibling.keys.get(j));
    }

    if (!sibling.isLeaf) {
      for (int j = 1; j <= sibling.keyCount; ++j) {
        sibling.children.set(j - 1, sibling.children.get(j));
      }
    }

    child.keyCount++;
    sibling.keyCount--;
  }

  private void merge(Node node, int i) {
    Node child = node.children.get(i);
    Node sibling = node.children.get(i + 1);

    child.keys.set(child.keyCount, node.keys.get(i));

    if (!child.isLeaf) {
      child.children.set(child.keyCount + 1, sibling.children.get(0));
    }

    for (int j = 0; j < sibling.keyCount; ++j) {
      child.keys.set(j + child.keyCount + 1, sibling.keys.get(j));
    }

    if (!child.isLeaf) {
      for (int j = 0; j <= sibling.keyCount; ++j) {
        child.children.set(j + child.keyCount + 1, sibling.children.get(j));
      }
    }

    for (int j = i + 1; j < node.keyCount; ++j) {
      node.keys.set(j - 1, node.keys.get(j));
    }

    for (int j = i + 2; j <= node.keyCount; ++j) {
      node.children.set(j - 1, node.children.get(j));
    }

    child.keyCount += sibling.keyCount + 1;
    node.keyCount--;
  }

  private Key getNodePredecessor(Node node, int i) {
    Node curr = node.children.get(i);
    while (!curr.isLeaf) {
      curr = curr.children.get(curr.keyCount);
    }

    if (curr.keyCount - 1 < 0) {
      return null;
    }
    return curr.keys.get(curr.keyCount - 1);
  }

  public T get(Key key) {
    var node = getNode(root, key);
    return node == null ? null : node.getValue(key);
  }

  public Node getNode(Node node, Key key) {
    int i = 0;
    while (i < node.keyCount && key.compareTo(node.keys.get(i)) > 0) {
      i ++;
    }

    if (i < node.keyCount && key.compareTo(node.keys.get(i)) == 0) {
      return node;
    }
    if (node.isLeaf) {
      return null;
    }

    return getNode(node.children.get(i), key);
  }

  public void print() {
    print(this.root);
  }

  private void print(Node node) {
    if (node == null) {
      return;
    }

    int i;
    for (i = 0; i < node.keyCount; i++) {
      if (!node.isLeaf) {
        print(node.children.get(i));
      }

      Logger.info(node.keys.get(i).toString());
    }

    if (!node.isLeaf) {
      print(node.children.get(i));
    }
  }
}
