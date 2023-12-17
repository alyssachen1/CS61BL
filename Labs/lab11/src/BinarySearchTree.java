import org.antlr.v4.runtime.tree.Tree;

public class BinarySearchTree<T extends Comparable<T>> extends BinaryTree<T> {

    /* Creates an empty BST. Super() calls the constructor for BinaryTree (not in scope). */
    public BinarySearchTree() {
        super();
    }

    /* Creates a BST with root as ROOT. */
    public BinarySearchTree(TreeNode root) {
        super(root);
    }

    /* Returns true if the BST contains the given KEY. */
    public boolean contains(T key) {
        // TODO: YOUR CODE HERE: an extra helper method might be useful
        if (root == null) {
            return false;
        }
        else {
            return containsHelper(root, key);
        }
    }

    public boolean containsHelper(TreeNode t, T key) {
        if (t == null) {
            return false;
        }
        else if (key == t.item) {
            return true;
        }
        else if (t.item.compareTo(key) > 0) {
            return containsHelper(t.left, key);
        }
        else if (t.item.compareTo(key) < 0) {
            return containsHelper(t.right, key);
        }
        return false;
    }

    /* Adds a node for KEY if KEY isn't in the BST already. */
    public void add(T key) {
        // TODO: YOUR CODE HERE: an extra helper method might be useful
        if (root == null) {
            root = new TreeNode(key);
            root.size ++;
        }
        else if (!this.contains(key)) {
            addHelper(root, key);
        }
    }

    public void addHelper(TreeNode t, T key) {
        if (t.right == null && t.item.compareTo(key) < 0) {
            t.right = new TreeNode(key);
        }
        else if (t.left == null && t.item.compareTo(key) > 0) {
            t.left = new TreeNode(key);
        }
        else if (t.item.compareTo(key) > 0) {
            addHelper(t.left, key);
        }
        else if (t.item.compareTo(key) < 0) {
            addHelper(t.right, key);
        }
    }

    /* Deletes a node from the BST. 
     * Even though you do not have to implement delete, you 
     * should read through and understand the basic steps.
    */
    public T delete(T key) {
        TreeNode parent = null;
        TreeNode curr = root;
        TreeNode delNode = null;
        TreeNode replacement = null;
        boolean rightSide = false;

        while (curr != null && !curr.item.equals(key)) {
            if (curr.item.compareTo(key) > 0) {
                parent = curr;
                curr = curr.left;
                rightSide = false;
            } else {
                parent = curr;
                curr = curr.right;
                rightSide = true;
            }
        }
        delNode = curr;
        if (curr == null) {
            return null;
        }

        if (delNode.right == null) {
            if (root == delNode) {
                root = root.left;
            } else {
                if (rightSide) {
                    parent.right = delNode.left;
                } else {
                    parent.left = delNode.left;
                }
            }
        } else {
            curr = delNode.right;
            replacement = curr.left;
            if (replacement == null) {
                replacement = curr;
            } else {
                while (replacement.left != null) {
                    curr = replacement;
                    replacement = replacement.left;
                }
                curr.left = replacement.right;
                replacement.right = delNode.right;
            }
            replacement.left = delNode.left;
            if (root == delNode) {
                root = replacement;
            } else {
                if (rightSide) {
                    parent.right = replacement;
                } else {
                    parent.left = replacement;
                }
            }
        }
        return delNode.item;
    }
}
