package example.ftp;

import java.util.concurrent.TimeUnit;

import com.github.drapostolos.rdp4j.DirectoryPoller;
import com.github.drapostolos.rdp4j.spi.PolledDirectory;

import example.MyListener;

public class FtpExample {

    public static void main(String[] args) throws Exception {
        String host = "vbox.localdomain";
        String workingDirectory = 
        //		"/usr/ohad";
        		args[0];
        System.out.println("monitoring directory: " + workingDirectory);
        String username = "root";
        String password = "nicecti1!";
        PolledDirectory polledDirectory = new FtpDirectory(host, workingDirectory, username, password);

        DirectoryPoller dp = DirectoryPoller.newBuilder()
            .addPolledDirectory(polledDirectory)
            .addListener(new MyListener())
            .enableFileAddedEventsForInitialContent()
//            .setPollingInterval(10, TimeUnit.MINUTES)
            .setPollingInterval(10, TimeUnit.SECONDS)
            .start();

        TimeUnit.HOURS.sleep(2);

        dp.stop();
    }
}