import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDequeTest {
    @Test
    public void addFirstTest(){
        ArrayDeque<Object> lld1 = new ArrayDeque<>();

        lld1.addFirst("back");
        assertThat(lld1.toList().get(3)).isEqualTo("back");

        lld1.addFirst("middle");
        assertThat(lld1.toList().get(3)).isEqualTo("back");
        assertThat(lld1.toList().get(2)).isEqualTo("middle");


        lld1.addFirst("front");
        assertThat(lld1.toList().get(1)).isEqualTo("front");
        assertThat(lld1.toList().get(2)).isEqualTo("middle");
        assertThat(lld1.toList().get(3)).isEqualTo("back");

    }


    @Test
    public void addLastTest() {

        ArrayDeque<Object> lld1 = new ArrayDeque<>();

        lld1.addLast("front");
        assertThat(lld1.toList().get(4)).isEqualTo("front");

        lld1.addLast("middle");
        assertThat(lld1.toList().get(5)).isEqualTo("middle");


        lld1.addLast("back");
        assertThat(lld1.toList().get(6)).isEqualTo("back");

    }

    @Test
    public void toListTest() {

        ArrayDeque<Object> lld1 = new ArrayDeque<>();

        lld1.addFirst("back");
        lld1.addFirst("middle");
        lld1.addFirst("front");

        assertThat(lld1.toList().get(1)).isEqualTo("front");
        assertThat(lld1.toList().get(2)).isEqualTo("middle");
        assertThat(lld1.toList().get(3)).isEqualTo("back");

        lld1.addLast("middle");
        lld1.addLast("back");
        assertThat(lld1.toList().get(4)).isEqualTo("middle");
        assertThat(lld1.toList().get(5)).isEqualTo("back");




    }

    @Test
    public void isEmptyTest() {
        ArrayDeque<Object> lld1 = new ArrayDeque<>();
        assertThat(lld1.isEmpty()).isTrue();

        lld1.addFirst(1);
        assertThat(lld1.isEmpty()).isFalse();
    }

    @Test
    public void sizeTest() {
        ArrayDeque<Object> lld1 = new ArrayDeque<>();
        assertThat(lld1.size()).isEqualTo(0);

        lld1.addFirst(1);
        assertThat(lld1.size()).isEqualTo(1);
        lld1.addLast(2);
        lld1.addLast(3);
        assertThat(lld1.size()).isEqualTo(3);
    }

    @Test
    public  void removeFirstTest() {
        ArrayDeque<Object> lld1 = new ArrayDeque<>();

        assertThat(lld1.removeFirst()).isNull();
        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);

        assertThat(lld1.removeFirst()).isEqualTo(1);
        assertThat(lld1.removeFirst()).isEqualTo(2);
        assertThat(lld1.removeFirst()).isEqualTo(3);
        assertThat(lld1.isEmpty()).isTrue();
    }

    @Test
    public void removeLastTest() {
        ArrayDeque<Object> lld1 = new ArrayDeque<>();

        assertThat(lld1.removeLast()).isNull();
        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);

        assertThat(lld1.removeLast()).isEqualTo(3);
        assertThat(lld1.removeLast()).isEqualTo(2);
        assertThat(lld1.removeLast()).isEqualTo(1);
        assertThat(lld1.isEmpty()).isTrue();
    }

    @Test
    public void getTest() {
        ArrayDeque<Object> lld1 = new ArrayDeque<>();
        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);

        assertThat(lld1.get(0)).isEqualTo(1);
        assertThat(lld1.get(1)).isEqualTo(2);
        assertThat(lld1.get(2)).isEqualTo(3);
        assertThat(lld1.get(3)).isNull();
    }

}
