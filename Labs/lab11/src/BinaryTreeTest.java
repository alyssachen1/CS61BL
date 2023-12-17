import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class BinaryTreeTest {
    @Test
    public void treeFormatTest() {
        BinarySearchTree<String> x = new BinarySearchTree<String>();
        x.add("C");
        x.add("A");
        x.add("E");
        x.add("B");
        x.add("D");
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(outContent));
        BinaryTree.print(x, "x");
        System.setOut(oldOut);
        assertWithMessage("The preorder and/or inorder traversals for the given BinarySearchTree are incorrect")
                .that(outContent.toString().trim())
                .isEqualTo("x in preorder\nC A B E D \nx in inorder\nA B C D E \n\n".trim());

        /* if the above test isn't working due to operating system differences, try commenting out the above line
        and replace it with the following:

       assertWithMessage("The preorder and/or inorder traversals for the given BinarySearchTree are incorrect")
                .that(outContent.toString().trim())
                .isEqualTo("x in preorder" + System.lineSeparator() + "C A B E D " + System.lineSeparator() + "x in inorder"
                        + System.lineSeparator() + "A B C D E ");

        */
    }
    @Test
    public void addContainsTest() {
        BinarySearchTree<Integer> t1 = new BinarySearchTree<>();
        t1.add(1);
        t1.add(2);
        t1.add(3);
        t1.contains(3);
        assertThat(t1.contains(3)).isTrue();
    }
}