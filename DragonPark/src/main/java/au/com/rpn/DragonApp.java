package au.com.rpn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class DragonApp
{

    public DragonApp() {
    }
    
    public BufferedReader createReader(InputStream stream) {
        return new BufferedReader(new InputStreamReader(stream));
    }
    
    public void process(InputStream in, PrintStream out) throws IOException {
        BufferedReader reader = createReader(in);
        String line;
        while ((line = reader.readLine()) != null) {
            process(line, out);
        }
    }

    public void process(String line, PrintStream out) throws IOException {
//        try {
//            getParser().process(line);
//            getConsole().display(out);
//        } catch (RpnException ex) {
//            getConsole().display(out, ex);
//        }
    }

    public static void main(String [] args) {
        InputStream in = System.in;
        PrintStream out = System.out;
        DragonApp app = new DragonApp();
        try {
            app.process(in, out);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
