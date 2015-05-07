package example.sftp;

import java.util.concurrent.TimeUnit;

import static org.kohsuke.args4j.ExampleMode.ALL;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import com.github.drapostolos.rdp4j.DirectoryPoller;
import com.github.drapostolos.rdp4j.spi.PolledDirectory;

import example.MyListener;

public class FtpExample {

    @Option(name="-path")
    private String path = "/";

    @Option(name="-host")
    private String host = "vbox.localdomain";

    public static void main(String[] args) throws Exception 
    {
        new FtpExample().doMain(args);
    }

	private void doMain(String[] args) throws InterruptedException {
        CmdLineParser parser = new CmdLineParser(this);
        
        // if you have a wider console, you could increase the value;
        // here 80 is also the default
        parser.setUsageWidth(80);

        try {
            // parse the arguments.
            parser.parseArgument(args);

            // you can parse additional arguments if you want.
            // parser.parseArgument("more","args");

            // after parsing arguments, you should check
            // if enough arguments are given.
//            if( arguments.isEmpty() )
//                throw new CmdLineException(parser,"No argument is given");

        }
        catch( CmdLineException e )
        {
            // if there's a problem in the command line,
            // you'll get this exception. this will report
            // an error message.
            System.err.println(e.getMessage());
            System.err.println("java SampleMain [options...] arguments...");
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();

            // print option sample. This is useful some time
            System.err.println("  Example: java SampleMain"+parser.printExample(ALL));

            return;
        }

        System.out.println("monitoring directory: " + path);
        String username = "root";
        String password = "nicecti1!";
        PolledDirectory polledDirectory = new SFtpDirectory(host, path, username, password);

        DirectoryPoller dp = DirectoryPoller.newBuilder()
            .addPolledDirectory(polledDirectory)
            .addListener(new MyListener())
            .enableFileAddedEventsForInitialContent()
//            .setPollingInterval(10, TimeUnit.MINUTES)
            .setPollingInterval(10, TimeUnit.SECONDS)
            .start();

        TimeUnit.HOURS.sleep(2);

        dp.stop();	}
}