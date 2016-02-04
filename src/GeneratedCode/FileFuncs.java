package GeneratedCode;

import com.trolltech.qt.gui.QMouseEvent;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QSlider;

public class FileFuncs {

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

}
