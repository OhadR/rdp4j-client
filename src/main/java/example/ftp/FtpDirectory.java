package example.ftp;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.github.drapostolos.rdp4j.spi.FileElement;
import com.github.drapostolos.rdp4j.spi.PolledDirectory;

public class FtpDirectory implements PolledDirectory{
    private String host;
    private String workingDirectory;
    private String username;
    private String password;

    public FtpDirectory(String host, String workingDirectory, String username, String password) {
        this.host = host;
        this.workingDirectory = workingDirectory;
        this.username = username;
        this.password = password;
    }

    public Set<FileElement> listFiles() throws IOException 
    {
        FTPClient ftp = null;
        try{
            ftp = new FTPClient();

            ftp.connect(host, 22);
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                ftp.disconnect();
                throw new IOException("Exception when connecting to FTP Server: " + ftp);
            }
            ftp.login(username, password);
            ftp.enterLocalPassiveMode();

            Set<FileElement> result = new LinkedHashSet<FileElement>();
            for(FTPFile file : ftp.listFiles(workingDirectory)){
                result.add(new FtpFile(file));
            }
            return result;
        } catch (Exception e){
            throw new IOException(e);
        } finally {
            try {
                if(ftp.isConnected()) {
                    ftp.disconnect();
                }
            } catch(Throwable ioe) {
                // do nothing
            }
        }
    }
}