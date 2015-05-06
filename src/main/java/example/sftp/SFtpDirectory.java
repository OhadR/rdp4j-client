package example.sftp;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import java.util.Vector;

import com.github.drapostolos.rdp4j.spi.FileElement;
import com.github.drapostolos.rdp4j.spi.PolledDirectory;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SFtpDirectory implements PolledDirectory{
    private String host;
    private String workingDirectory;
    private String username;
    private String password;

    public SFtpDirectory(String host, String workingDirectory, String username, String password) {
        this.host = host;
        this.workingDirectory = workingDirectory;
        this.username = username;
        this.password = password;
    }

    public Set<FileElement> listFiles() throws IOException 
    {
        Set<FileElement> result = new LinkedHashSet<FileElement>();

        JSch jsch = new JSch();
        Session session = null;
        try {
            session = jsch.getSession( username, host, 22 );
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword( password );
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
//            sftpChannel.get("remotefile.txt", "localfile.txt");
            Vector<LsEntry> filesList = sftpChannel.ls( workingDirectory );
            for(LsEntry file : filesList)
            {
            	result.add( new SFtpFile( file ) );
            	
            }
            sftpChannel.exit();
            session.disconnect();
        } catch (JSchException e) {
            e.printStackTrace();  
            throw new IOException(e);
        } catch (SftpException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        
        return result;
    }
}