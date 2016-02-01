package Compiler.Parsers;

import com.trolltech.qt.QSignalEmitter;
import com.trolltech.qt.gui.*;

/**
 * Created by Caleb Bain on 1/31/2016.
 */
public class KeySequenceParser {

    public void parse(){
        QMenu menu = new QMenu("");
        QAction action = new QAction(menu);
        action.setShortcut(new QKeySequence("Ctrl+N"));
        action.triggered.connect(QApplication.instance(), "newFile()");




    }
}
