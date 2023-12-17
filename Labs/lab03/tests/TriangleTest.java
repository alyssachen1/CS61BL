import org.junit.Test;
import com.google.common.truth.Truth;
public abstract class TriangleTest {

    /** For autograding purposes; do not change this line. */
    abstract Triangle getNewTriangle();

    /* ***** TESTS ***** */

    @Test
    public void test2() {
        Triangle t = getNewTriangle();
        Truth.assertThat(t.sidesFormTriangle(1, 2, 3)).isFalse();
        Truth.assertThat(t.sidesFormTriangle(2, 4, 5)).isTrue();
    }

    @Test
    public void test3() {
        Triangle t = getNewTriangle();
        Truth.assertThat(t.triangleType(5, 5, 5)).isEqualTo("Equilateral");
        Truth.assertThat(t.triangleType(5, 5, 8)).isEqualTo("Isosceles");
        Truth.assertThat(t.triangleType(5, 6, 7)).isEqualTo("Scalene");
    }
    @Test
    public void test1() {
        Triangle t = getNewTriangle();
        Truth.assertThat(t.squaredHypotenuse(2, 3)).isEqualTo(13);
        Truth.assertThat(t.squaredHypotenuse(4, 4)).isEqualTo(32);
        }

    @Test
    public void test4() {
        Triangle t = getNewTriangle();
        Truth.assertThat(t.pointsFormTriangle(1, 1, 2, 4, 5, 2)).isTrue();
        Truth.assertThat(t.pointsFormTriangle(1, 1, 2, 2, 3, 3)).isFalse();
        Truth.assertThat(t.pointsFormTriangle(0, 0, 1, 0, 0, 1)).isTrue();
        Truth.assertThat(t.pointsFormTriangle(-2, -2, -3, 4, 1, 0)).isTrue();
    }
}
