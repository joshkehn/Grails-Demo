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
        def nocomma = FileHandler.processFile("comma,", "csv");   
        assertEquals "comma", nocomma[0];
    }
    
    void testMD5Processing()
    {
        def comma = FileHandler.processFile("comma,", "md5");
        assertEquals "comma,", comma[0];
    }

    void testPlainTextProcessing()
    {
        def comma = FileHandler.processFile("comma,", "md5");
        assertEquals "comma,", comma[0];
    }
    
    void testScrubbedFile()
    {
        def s = new ScrubbedFile();
        s.timestamp = new Date();
        s.label = "Hello, Kitty!";
        
        assertEquals "Hello, Kitty!", s.label;
    }
    
    void testScrubbedFileList()
    {
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
