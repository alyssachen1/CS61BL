import deque.ArrayDeque;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
public class ArrayDequeTest {

    @Test
    public void iteratorTest() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        StringBuilder sb = new StringBuilder();
        for (Object element : deque) {
            sb.append(element).append(" ");
        }

        assertThat(sb.toString().trim()).isEqualTo("1 2 3");
    }

    @Test
    public void equalsTest() {
        ArrayDeque<Integer> deque1 = new ArrayDeque<>();
        deque1.addLast(1);
        deque1.addLast(2);
        deque1.addLast(3);

        ArrayDeque<Integer> deque2 = new ArrayDeque<>();
        deque2.addLast(1);
        deque2.addLast(2);
        deque2.addLast(3);

        ArrayDeque<Integer> deque3 = new ArrayDeque<>();
        deque3.addLast(1);
        deque3.addLast(4);
        deque3.addLast(3);

        assertThat(deque1).isEqualTo(deque2);
        assertThat(deque1).isNotEqualTo(deque3);
    }

    @Test
    public void toStringTest() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        assertThat(deque.toString()).isEqualTo("[1, 2, 3]");
    }
}
