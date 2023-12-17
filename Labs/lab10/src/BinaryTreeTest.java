import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
public class BinaryTreeTest {
    @Test
    public void heightTest() {
        BinaryTree<Integer> t1 = new BinaryTree<>(new BinaryTree.TreeNode<>(1));
        t1.getRoot().setLeft(new BinaryTree.TreeNode<>(2));
        t1.getRoot().setRight(new BinaryTree.TreeNode<>(3));
        t1.getRoot().getRight().setRight(new BinaryTree.TreeNode<>(4));
        assertThat(t1.height()).isEqualTo(3);
    }
    @Test
    public void isCompletelyBalancedTest() {
        BinaryTree<Integer> t1 = new BinaryTree<>();
        assertThat(t1.isCompletelyBalanced()).isTrue();

        BinaryTree<Integer> t2 = new BinaryTree<>(new BinaryTree.TreeNode<>(1));
        t2.getRoot().setLeft(new BinaryTree.TreeNode<>(2));
        t2.getRoot().setRight(new BinaryTree.TreeNode<>(3));
        t2.getRoot().getLeft().setLeft(new BinaryTree.TreeNode<>(4));
        assertThat(t2.isCompletelyBalanced()).isFalse();
    }

    @Test
    public void fibTreeTest() {
        BinaryTree<Integer> f1 = new BinaryTree<>();
        assertThat (f1.fibTree(0). root.getItem () ).isEqualTo(0);
        assertThat(f1.fibTree(4).root.getItem()).isEqualTo(3);
        assertThat(f1.fibTree(6).root.getItem()).isEqualTo(8);

    }
}

