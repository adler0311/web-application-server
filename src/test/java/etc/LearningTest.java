package etc;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LearningTest {

    @Test
    public void readFile() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("./test.txt"), Charset.defaultCharset());
            for (String line : lines) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
