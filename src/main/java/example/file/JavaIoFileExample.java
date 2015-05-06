package example.file;

import java.io.File;
import java.util.concurrent.TimeUnit;

import com.github.drapostolos.rdp4j.DirectoryPoller;
import com.github.drapostolos.rdp4j.JavaIoFileAdapter;

import example.MyListener;

public class JavaIoFileExample {

    public static void main(String[] args) throws Exception {
        DirectoryPoller dp = DirectoryPoller.newBuilder()
            .addPolledDirectory(new JavaIoFileAdapter(new File("D:\\Dev\\tmp")))
            .addListener(new MyListener())
            .setPollingInterval(2, TimeUnit.SECONDS)
            .start();
        /* 
         * while sleeping here, manually add/remove/modify files
         * in the "C:\\temp\\watched" directory and watch the output 
         * in the console window. 
         */
        TimeUnit.MINUTES.sleep(2);

        dp.stop();
    }
}