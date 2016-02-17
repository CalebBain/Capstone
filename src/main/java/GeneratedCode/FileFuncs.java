package GeneratedCode;

import com.trolltech.qt.gui.QMouseEvent;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QSlider;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;

public class FileFuncs {


    public FileFuncs() {
    }

    public void generate(){
        System.out.println("slots are working");
    }

    public void generate(QPushButton slider, QMouseEvent event){
        System.out.println("events are working : " + event);
    }

    public void generate(QSlider slider, QMouseEvent event){
        System.out.println("events are working : " + event);
    }

    public void undo(){ }

    public void redo(){ }

    public void cut(){ }

    public void copy(){ }

    public void paste(){ }

    public void delete(){ }

    public void Clone(){
        final File localPath = new File("C:\\Users\\Caleb Bain\\Desktop\\test");
        try {
            Git.cloneRepository().setURI("https://github.com/CalebBain/test.git").setDirectory(localPath)
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider("calebbain", "a2001033954"))
                    .call();
            System.out.println("cloning");
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    public void commit() {

    }
}
