package emlyn.ma.my.spring.core.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class ResourceTests {

    @Test
    void testClassPathResource() {
        Resource resource = new ClassPathResource("log4j2.xml");
        Assertions.assertTrue(resource.exists());
    }

    @Test
    void testFileSystemResource() {
        Resource resource = new FileSystemResource("src/test/resources/log4j2.xml");
        Assertions.assertTrue(resource.exists());
    }

}
