package au.com.rpn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.Test;

public class DragonAppTest
{
    DragonApp app = new DragonApp();
    
    ByteArrayOutputStream stream= new ByteArrayOutputStream();
    PrintStream  out = new PrintStream(stream);
    
    InputStream in;
    
    public InputStream createInputStream(String input) {
        return new ByteArrayInputStream(input.getBytes());
    }

    @Test
    public void testExample1() throws IOException
    {
    }
}
