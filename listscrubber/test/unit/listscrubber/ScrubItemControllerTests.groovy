package listscrubber

import grails.test.*

class ScrubItemControllerTests extends GroovyTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    protected void csvProcessingSpec()
    {
        println "Testing csv file processing";
        String nocomma = FileHandler.processFile("comma,", "csv");
        
        assertEquals "comma", nocomma[0];
    }
    
    protected void md5ProcessingSpec()
    {
        println "Testing md5 file processing";
        String comma = FileHandler.processFile("comma,", "md5");
        assertEquals "comma,", comma[0];
    }

    protected void plainTextProcessingSpec()
    {
        println "Testing plain text file processing";
        String comma = FileHandler.processFile("comma,", "md5");
        assertEquals "comma,", comma[0];
    }
}
