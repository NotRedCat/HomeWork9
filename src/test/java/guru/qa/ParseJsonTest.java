package guru.qa;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ParseJsonTest {
   /* ClassLoader classLoader = FileParseTest.class.getClassLoader();
    @IgnoreForBinding
    @Test
    void jsonTest() throws Exception {
        InputStream is = classLoader.getResourceAsStream("Car.json");
        ObjectMapper objectMapper = new ObjectMapper();
        Car car = objectMapper.readValue(is, Car.class);
        assertThat(Car.firstCharacter).isEqualTo("I");
        assertThat(Car.secondCharacter).isEqualTo("WAS");
        assertThat(Car.thirdCharacter).isEqualTo("BORN");
        List<String> list = Arrays.asList("FOR", "TEST");
        assertThat(Car.fifthSixthCharacter).isEqualTo(list);
    }*/

}
