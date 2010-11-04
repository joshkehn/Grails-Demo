package listscrubber

import grails.test.*

class ScrubItemControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testCSVProcessing()
    {
        println "Testing csv file processing";
        def nocomma = FileHandler.processFile("comma,", "csv");
        
        assertEquals "comma", nocomma[0];
    }
    
    void testMD5Processing()
    {
        println "Testing md5 file processing";
        def comma = FileHandler.processFile("comma,", "md5");
        assertEquals "comma,", comma[0];
    }

    void testPlainTextProcessing()
    {
        println "Testing plain text file processing";
        def comma = FileHandler.processFile("comma,", "md5");
        assertEquals "comma,", comma[0];
    }
}
