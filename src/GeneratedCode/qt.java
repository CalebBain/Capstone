package GeneratedCode;
import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;
public class qt extends QApplication{
public qt() { super(new String[0]);
run();
}
public void run() {
QMainWindow window = new QMainWindow(){
public void undo(){ new GeneratedCode.FileFuncs().undo(); }
public void redo(){ new GeneratedCode.FileFuncs().redo(); }
public void cut(){ new GeneratedCode.FileFuncs().cut(); }
public void copy(){ new GeneratedCode.FileFuncs().copy(); }
public void paste(){ new GeneratedCode.FileFuncs().paste(); }
public void delete(){ new GeneratedCode.FileFuncs().delete(); }

};
QWidget centerWidget = new QWidget();
window.setCentralWidget(centerWidget);
window.setWindowTitle(tr("Hello World"));
QMenuBar menubar = new QMenuBar();
window.setMenuBar(menubar);
QMenu menu = new QMenu();
menu.setTitle("Repository");
menubar.addMenu(menu);
QAction action = new QAction("Explore Working Copy", this);
menu.addAction(action);
QAction action1 = new QAction("Git Bash", this);
menu.addAction(action1);
menu.addSeparator();
QAction action2 = new QAction("Browse master's Files", this);
menu.addAction(action2);
QAction action3 = new QAction("Browse Branch Files", this);
menu.addAction(action3);
menu.addSeparator();
QAction action4 = new QAction("Visualize master's History", this);
menu.addAction(action4);
QAction action5 = new QAction("Visualize All Branch History", this);
menu.addAction(action5);
menu.addSeparator();
QAction action6 = new QAction("Database Statistics", this);
menu.addAction(action6);
QAction action7 = new QAction("Compress Database", this);
menu.addAction(action7);
QAction action8 = new QAction("Verify Database", this);
menu.addAction(action8);
menu.addSeparator();
QAction action9 = new QAction("Create Desktop Icon", this);
menu.addAction(action9);
QAction action10 = new QAction("Quit", this);
action10.setShortcut(new QKeySequence(tr("Ctrl+Q")));
action10.triggered.connect(this, "quit()");
menu.addAction(action10);
QMenu menu1 = new QMenu();
menu1.setTitle("Edit");
menubar.addMenu(menu1);
QAction action11 = new QAction("Undo", this);
action11.setShortcut(new QKeySequence(tr("Ctrl+Z")));
action11.triggered.connect(window, "undo()");
menu1.addAction(action11);
QAction action12 = new QAction("Redo", this);
action12.setShortcut(new QKeySequence(tr("Ctrl+Y")));
action12.triggered.connect(window, "redo()");
menu1.addAction(action12);
menu1.addSeparator();
QAction action13 = new QAction("Cut", this);
action13.setShortcut(new QKeySequence(tr("Ctrl+X")));
action13.triggered.connect(window, "cut()");
menu1.addAction(action13);
QAction action14 = new QAction("Copy", this);
action14.setShortcut(new QKeySequence(tr("Ctrl+X")));
action14.triggered.connect(window, "copy()");
menu1.addAction(action14);
QAction action15 = new QAction("Paste", this);
action15.setShortcut(new QKeySequence(tr("Ctrl+V")));
action15.triggered.connect(window, "paste()");
menu1.addAction(action15);
QAction action16 = new QAction("Delete", this);
action16.setShortcut(new QKeySequence(tr("Del")));
action16.triggered.connect(window, "delete()");
menu1.addAction(action16);
menu1.addSeparator();
QAction action17 = new QAction("Delete", this);
action17.setShortcut(new QKeySequence(tr("Ctrl+A")));
menu1.addAction(action17);
QGridLayout grid = new QGridLayout();
centerWidget.setLayout(grid);
QListWidget list = new QListWidget();
grid.addWidget(list,  1,  1,  1,  1, Qt.AlignmentFlag.AlignAbsolute);
QPushButton button = new QPushButton("quit!");
grid.addWidget(button,  4,  1,  4,  1, Qt.AlignmentFlag.AlignAbsolute);
QPushButton button1 = new QPushButton("push me!"){
protected void mousePressEvent(QMouseEvent event) {
new GeneratedCode.FileFuncs().generate(this, event);
}
};
grid.addWidget(button1,  6,  1,  6,  1, Qt.AlignmentFlag.AlignAbsolute);
window.show();
this.exec();
}
}
