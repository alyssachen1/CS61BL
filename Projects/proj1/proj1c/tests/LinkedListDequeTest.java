import deque.LinkedListDeque;
import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
public class LinkedListDequeTest {
    @Test
    public void iteratorTest() {
        LinkedListDeque<Integer> deque = new LinkedListDeque<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        StringBuilder sb = new StringBuilder();
        for (Integer element : deque) {
            sb.append(element).append(" ");
        }

        assertThat(sb.toString().trim()).isEqualTo("1 2 3");
    }

    @Test
    public void equalsTest() {
        LinkedListDeque<Integer> deque1 = new LinkedListDeque<>();
        deque1.addLast(1);
        deque1.addLast(2);
        deque1.addLast(3);

        LinkedListDeque<Integer> deque2 = new LinkedListDeque<>();
        deque2.addLast(1);
        deque2.addLast(2);
        deque2.addLast(3);

        LinkedListDeque<Integer> deque3 = new LinkedListDeque<>();
        deque3.addLast(4);
        deque3.addLast(5);

        assertThat(deque1).isEqualTo(deque2);
        assertThat(deque1).isNotEqualTo(deque3);
    }

    @Test
    public void toStringTest() {
        LinkedListDeque<String> deque = new LinkedListDeque<>();
        deque.addLast("front");
        deque.addLast("middle");
        deque.addLast("back");

        assertThat(deque.toString()).isEqualTo("[front, middle, back]");
    }
}
