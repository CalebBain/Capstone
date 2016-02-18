package GeneratedCode;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;

public class FileFuncs {

    File localPath = new File("C:\\Users\\Caleb Bain\\Desktop\\test");
    Git git;

    public FileFuncs() {
        InitCommand initCommand = Git.init();
        initCommand.setDirectory(localPath);
        try{
            git = Git.open(localPath);
        } catch (IOException ignored) {
        }
    }

    public void Clone(){
        try {
            git = Git.cloneRepository().setURI("https://github.com/CalebBain/test.git").setDirectory(localPath)
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider("calebbain", "a2001033954"))
                    .call();
            System.out.println("cloned");
        } catch (GitAPIException e) {
        }
    }

    public void Commit(){
        try {
            File myfile = new File(localPath + "/test.txt");
            git.add().addFilepattern("test.txt").call();
            git.commit().setMessage("test commit").call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    public void Push(){
        try {
            git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider("calebbain", "a2001033954")).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    public void Pull(){
        try {
            git.pull().setCredentialsProvider(new UsernamePasswordCredentialsProvider("calebbain", "a2001033954")).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }
}
