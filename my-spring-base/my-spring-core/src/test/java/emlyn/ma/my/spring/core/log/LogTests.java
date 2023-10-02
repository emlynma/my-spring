package emlyn.ma.my.spring.core.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTests {

    @Test
    void testSlf4j() {
        Logger logger = LoggerFactory.getLogger(LogTests.class);
        logger.warn("Hello, this is a test of slf4j.");
    }

    @Test
    void testSpringJCL() {
        Log log = LogFactory.getLog(LogTests.class);
        log.warn("Hello, this is a test of Spring JCL.");
    }

}
