package listscrubber

import grails.test.*

class ScrubItemControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }
    
    void testUniqueProcessing()
    {
        println "Testing unique processing."

        def multiples = "one\none\ntwo\ntwo\nthree";
        def expect = ["one", "two", "three"];
        
        println "INPUT: " + multiples;
        println "EXPECTING: " + expect;
        println "GOT: " + FileHandler.processFile(multiples, "plain");
        assertEquals expect, FileHandler.processFile(multiples, "plain");
    }

    void testCSVProcessing()
    {
        println "Testing CSV processing"
        def nocomma = FileHandler.processFile("comma,", "csv");   
        assertEquals "comma", nocomma[0];
    }
    
    void testMD5Processing()
    {
        println "Testing MD5 processing"
        def comma = FileHandler.processFile("comma,", "md5");
        assertEquals "comma,", comma[0];
    }

    void testPlainTextProcessing()
    {
        println "Testing plain text processing"
        def comma = FileHandler.processFile("comma,", "md5");
        assertEquals "comma,", comma[0];
    }
    
    void testScrubbedFile()
    {
        println "Testing scrubbed file object"
        def s = new ScrubbedFile();
        s.timestamp = new Date();
        s.label = "Hello, Kitty!";
        
        assertEquals "Hello, Kitty!", s.label;
    }
    
    void testScrubbedFileList()
    {
        println "Testing file list timestamps"
        def urlList = FileHandler.getReadyFilesUrls();
        def prev;
        urlList.each() { url ->
            assertNotNull url;
            if(prev == null)
            {
                prev = url.timestamp;
            }
            else
            {
                assertTrue prev > url.timestamp;
                prev = url.timestamp;
            }
        }
    }
}
