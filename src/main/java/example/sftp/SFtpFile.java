package example.sftp;

import java.io.IOException;

//import org.apache.commons.net.ftp.FTPFile;
import com.github.drapostolos.rdp4j.spi.FileElement;
import com.jcraft.jsch.ChannelSftp.LsEntry;

public class SFtpFile implements FileElement
{
    private final LsEntry file;

    public SFtpFile(LsEntry file) {
        this.file = file;
    }

//    @Override
    public long lastModified() throws IOException 
    {
    	return file.getAttrs().getMTime();
    }

//  @Override
    public boolean isDirectory() {
        return false;
    }

//  @Override
    public String getName() {
        return file.getFilename();
    }

//  @Override
    public String toString() {
        return getName();
    }
}