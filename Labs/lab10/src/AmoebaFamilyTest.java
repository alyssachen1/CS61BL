import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
public class AmoebaFamilyTest {
    @Test
    public void longestNameTest() {
        AmoebaFamily af1 = new AmoebaFamily("Hi");
        af1.addChild("Hi", "Hello");
        af1.addChild("Hello", "Hehe");
        assertThat(af1.longestName()).isEqualTo("Hello");
        af1.addChild("Hello", "Supercali");
        assertThat(af1.longestName()).isEqualTo("Supercali");
    }
}
