package EventClass;

import com.trolltech.qt.core.QChildEvent;
import com.trolltech.qt.core.QEvent;
import com.trolltech.qt.core.QTimerEvent;
import com.trolltech.qt.gui.*;

/**
 * Created by Caleb Bain on 1/15/2016.
 */
public abstract class Events {
    /*window events*/
    public void actionEvent(QMainWindow component, QActionEvent event) {}
    public void changeEvent(QMainWindow component, QEvent event) {}
    public void childEvent(QMainWindow component, QChildEvent event) {}
    public void closeEvent(QMainWindow component, QCloseEvent event) {}
    public void contextMenuEvent(QMainWindow component, QEvent event){}
    public void dragEnterEvent(QMainWindow component, QDragEnterEvent event) {}
    public void dragLeaveEvent(QMainWindow component, QDragLeaveEvent event) {}
    public void dragMoveEvent(QMainWindow component, QDragMoveEvent event) {}
    public void dropEvent(QMainWindow component, QDropEvent event) {}
    public void enterEvent(QMainWindow component, QEvent event) {}
    public void focusInEvent(QMainWindow component, QFocusEvent event) {}
    public void focusOutEvent(QMainWindow component, QFocusEvent event) {}
    public void hideEvent(QMainWindow component, QHideEvent event) {}
    public void inputMethodEvent(QMainWindow component, QInputMethodEvent event) {}
    public void keyPressEvent(QMainWindow component, QKeyEvent event) {}
    public void keyReleaseEvent(QMainWindow component, QKeyEvent event) {}
    public void leaveEvent(QMainWindow component, QEvent event) {}
    public void mouseDoubleClickEvent(QMainWindow component, QMouseEvent event) {}
    public void mouseMoveEvent(QMainWindow component, QMouseEvent event) {}
    public void mousePressEvent(QMainWindow component, QEvent event){}
    public void mouseReleaseEvent(QMainWindow component, QMouseEvent event) {}
    public void moveEvent(QMainWindow component, QMoveEvent event) {}
    public void paintEvent(QMainWindow component, QPaintEvent event) {}
    public void resizeEvent(QMainWindow component, QResizeEvent event) {}
    public void showEvent(QMainWindow component, QEvent event){}
    public void tabletEvent(QMainWindow component, QTabletEvent event) {}
    public void timerEvent(QMainWindow component, QTimerEvent event) {}
    public void wheelEvent(QMainWindow component, QWheelEvent event) {}
    /*button events*/
    public void actionEvent(QPushButton component, QActionEvent event) {}
    public void changeEvent(QPushButton component, QEvent event) {}
    public void childEvent(QPushButton component, QChildEvent event) {}
    public void closeEvent(QPushButton component, QCloseEvent event) {}
    public void contextMenuEvent(QPushButton component, QEvent event){}
    public void dragEnterEvent(QPushButton component, QDragEnterEvent event) {}
    public void dragLeaveEvent(QPushButton component, QDragLeaveEvent event) {}
    public void dragMoveEvent(QPushButton component, QDragMoveEvent event) {}
    public void dropEvent(QPushButton component, QDropEvent event) {}
    public void enterEvent(QPushButton component, QEvent event) {}
    public void focusInEvent(QPushButton component, QFocusEvent event) {}
    public void focusOutEvent(QPushButton component, QFocusEvent event) {}
    public void hideEvent(QPushButton component, QHideEvent event) {}
    public void inputMethodEvent(QPushButton component, QInputMethodEvent event) {}
    public void keyPressEvent(QPushButton component, QKeyEvent event) {}
    public void keyReleaseEvent(QPushButton component, QKeyEvent event) {}
    public void leaveEvent(QPushButton component, QEvent event) {}
    public void mouseDoubleClickEvent(QPushButton component, QMouseEvent event) {}
    public void mouseMoveEvent(QPushButton component, QMouseEvent event) {}
    public void mousePressEvent(QPushButton component, QEvent event){}
    public void mouseReleaseEvent(QPushButton component, QMouseEvent event) {}
    public void moveEvent(QPushButton component, QMoveEvent event) {}
    public void paintEvent(QPushButton component, QPaintEvent event) {}
    public void resizeEvent(QPushButton component, QResizeEvent event) {}
    public void showEvent(QPushButton component, QEvent event){}
    public void tabletEvent(QPushButton component, QTabletEvent event) {}
    public void timerEvent(QPushButton component, QTimerEvent event) {}
    public void wheelEvent(QPushButton component, QWheelEvent event) {}
    /*number events*/
    public void actionEvent(QLCDNumber component, QActionEvent event) {}
    public void changeEvent(QLCDNumber component, QEvent event) {}
    public void childEvent(QLCDNumber component, QChildEvent event) {}
    public void closeEvent(QLCDNumber component, QCloseEvent event) {}
    public void contextMenuEvent(QLCDNumber component, QEvent event){}
    public void dragEnterEvent(QLCDNumber component, QDragEnterEvent event) {}
    public void dragLeaveEvent(QLCDNumber component, QDragLeaveEvent event) {}
    public void dragMoveEvent(QLCDNumber component, QDragMoveEvent event) {}
    public void dropEvent(QLCDNumber component, QDropEvent event) {}
    public void enterEvent(QLCDNumber component, QEvent event) {}
    public void focusInEvent(QLCDNumber component, QFocusEvent event) {}
    public void focusOutEvent(QLCDNumber component, QFocusEvent event) {}
    public void hideEvent(QLCDNumber component, QHideEvent event) {}
    public void inputMethodEvent(QLCDNumber component, QInputMethodEvent event) {}
    public void keyPressEvent(QLCDNumber component, QKeyEvent event) {}
    public void keyReleaseEvent(QLCDNumber component, QKeyEvent event) {}
    public void leaveEvent(QLCDNumber component, QEvent event) {}
    public void mouseDoubleClickEvent(QLCDNumber component, QMouseEvent event) {}
    public void mouseMoveEvent(QLCDNumber component, QMouseEvent event) {}
    public void mousePressEvent(QLCDNumber component, QEvent event){}
    public void mouseReleaseEvent(QLCDNumber component, QMouseEvent event) {}
    public void moveEvent(QLCDNumber component, QMoveEvent event) {}
    public void paintEvent(QLCDNumber component, QPaintEvent event) {}
    public void resizeEvent(QLCDNumber component, QResizeEvent event) {}
    public void showEvent(QLCDNumber component, QEvent event){}
    public void tabletEvent(QLCDNumber component, QTabletEvent event) {}
    public void timerEvent(QLCDNumber component, QTimerEvent event) {}
    public void wheelEvent(QLCDNumber component, QWheelEvent event) {}
    /*checkbox events*/
    public void actionEvent(QCheckBox component, QActionEvent event) {}
    public void changeEvent(QCheckBox component, QEvent event) {}
    public void childEvent(QCheckBox component, QChildEvent event) {}
    public void closeEvent(QCheckBox component, QCloseEvent event) {}
    public void contextMenuEvent(QCheckBox component, QEvent event){}
    public void dragEnterEvent(QCheckBox component, QDragEnterEvent event) {}
    public void dragLeaveEvent(QCheckBox component, QDragLeaveEvent event) {}
    public void dragMoveEvent(QCheckBox component, QDragMoveEvent event) {}
    public void dropEvent(QCheckBox component, QDropEvent event) {}
    public void enterEvent(QCheckBox component, QEvent event) {}
    public void focusInEvent(QCheckBox component, QFocusEvent event) {}
    public void focusOutEvent(QCheckBox component, QFocusEvent event) {}
    public void hideEvent(QCheckBox component, QHideEvent event) {}
    public void inputMethodEvent(QCheckBox component, QInputMethodEvent event) {}
    public void keyPressEvent(QCheckBox component, QKeyEvent event) {}
    public void keyReleaseEvent(QCheckBox component, QKeyEvent event) {}
    public void leaveEvent(QCheckBox component, QEvent event) {}
    public void mouseDoubleClickEvent(QCheckBox component, QMouseEvent event) {}
    public void mouseMoveEvent(QCheckBox component, QMouseEvent event) {}
    public void mousePressEvent(QCheckBox component, QEvent event){}
    public void mouseReleaseEvent(QCheckBox component, QMouseEvent event) {}
    public void moveEvent(QCheckBox component, QMoveEvent event) {}
    public void paintEvent(QCheckBox component, QPaintEvent event) {}
    public void resizeEvent(QCheckBox component, QResizeEvent event) {}
    public void showEvent(QCheckBox component, QEvent event){}
    public void tabletEvent(QCheckBox component, QTabletEvent event) {}
    public void timerEvent(QCheckBox component, QTimerEvent event) {}
    public void wheelEvent(QCheckBox component, QWheelEvent event) {}
    /*radiobutton events*/
    public void actionEvent(QRadioButton component, QActionEvent event) {}
    public void changeEvent(QRadioButton component, QEvent event) {}
    public void childEvent(QRadioButton component, QChildEvent event) {}
    public void closeEvent(QRadioButton component, QCloseEvent event) {}
    public void contextMenuEvent(QRadioButton component, QEvent event){}
    public void dragEnterEvent(QRadioButton component, QDragEnterEvent event) {}
    public void dragLeaveEvent(QRadioButton component, QDragLeaveEvent event) {}
    public void dragMoveEvent(QRadioButton component, QDragMoveEvent event) {}
    public void dropEvent(QRadioButton component, QDropEvent event) {}
    public void enterEvent(QRadioButton component, QEvent event) {}
    public void focusInEvent(QRadioButton component, QFocusEvent event) {}
    public void focusOutEvent(QRadioButton component, QFocusEvent event) {}
    public void hideEvent(QRadioButton component, QHideEvent event) {}
    public void inputMethodEvent(QRadioButton component, QInputMethodEvent event) {}
    public void keyPressEvent(QRadioButton component, QKeyEvent event) {}
    public void keyReleaseEvent(QRadioButton component, QKeyEvent event) {}
    public void leaveEvent(QRadioButton component, QEvent event) {}
    public void mouseDoubleClickEvent(QRadioButton component, QMouseEvent event) {}
    public void mouseMoveEvent(QRadioButton component, QMouseEvent event) {}
    public void mousePressEvent(QRadioButton component, QEvent event){}
    public void mouseReleaseEvent(QRadioButton component, QMouseEvent event) {}
    public void moveEvent(QRadioButton component, QMoveEvent event) {}
    public void paintEvent(QRadioButton component, QPaintEvent event) {}
    public void resizeEvent(QRadioButton component, QResizeEvent event) {}
    public void showEvent(QRadioButton component, QEvent event){}
    public void tabletEvent(QRadioButton component, QTabletEvent event) {}
    public void timerEvent(QRadioButton component, QTimerEvent event) {}
    public void wheelEvent(QRadioButton component, QWheelEvent event) {}
    /*slider events*/
    public void actionEvent(QSlider component, QActionEvent event) {}
    public void changeEvent(QSlider component, QEvent event) {}
    public void childEvent(QSlider component, QChildEvent event) {}
    public void closeEvent(QSlider component, QCloseEvent event) {}
    public void contextMenuEvent(QSlider component, QEvent event){}
    public void dragEnterEvent(QSlider component, QDragEnterEvent event) {}
    public void dragLeaveEvent(QSlider component, QDragLeaveEvent event) {}
    public void dragMoveEvent(QSlider component, QDragMoveEvent event) {}
    public void dropEvent(QSlider component, QDropEvent event) {}
    public void enterEvent(QSlider component, QEvent event) {}
    public void focusInEvent(QSlider component, QFocusEvent event) {}
    public void focusOutEvent(QSlider component, QFocusEvent event) {}
    public void hideEvent(QSlider component, QHideEvent event) {}
    public void inputMethodEvent(QSlider component, QInputMethodEvent event) {}
    public void keyPressEvent(QSlider component, QKeyEvent event) {}
    public void keyReleaseEvent(QSlider component, QKeyEvent event) {}
    public void leaveEvent(QSlider component, QEvent event) {}
    public void mouseDoubleClickEvent(QSlider component, QMouseEvent event) {}
    public void mouseMoveEvent(QSlider component, QMouseEvent event) {}
    public void mousePressEvent(QSlider component, QEvent event){}
    public void mouseReleaseEvent(QSlider component, QMouseEvent event) {}
    public void moveEvent(QSlider component, QMoveEvent event) {}
    public void paintEvent(QSlider component, QPaintEvent event) {}
    public void resizeEvent(QSlider component, QResizeEvent event) {}
    public void showEvent(QSlider component, QEvent event){}
    public void tabletEvent(QSlider component, QTabletEvent event) {}
    public void timerEvent(QSlider component, QTimerEvent event) {}
    public void wheelEvent(QSlider component, QWheelEvent event) {}
    /*column view events*/
    public void actionEvent(QColumnView component, QActionEvent event) {}
    public void changeEvent(QColumnView component, QEvent event) {}
    public void childEvent(QColumnView component, QChildEvent event) {}
    public void closeEvent(QColumnView component, QCloseEvent event) {}
    public void contextMenuEvent(QColumnView component, QEvent event){}
    public void dragEnterEvent(QColumnView component, QDragEnterEvent event) {}
    public void dragLeaveEvent(QColumnView component, QDragLeaveEvent event) {}
    public void dragMoveEvent(QColumnView component, QDragMoveEvent event) {}
    public void dropEvent(QColumnView component, QDropEvent event) {}
    public void enterEvent(QColumnView component, QEvent event) {}
    public void focusInEvent(QColumnView component, QFocusEvent event) {}
    public void focusOutEvent(QColumnView component, QFocusEvent event) {}
    public void hideEvent(QColumnView component, QHideEvent event) {}
    public void inputMethodEvent(QColumnView component, QInputMethodEvent event) {}
    public void keyPressEvent(QColumnView component, QKeyEvent event) {}
    public void keyReleaseEvent(QColumnView component, QKeyEvent event) {}
    public void leaveEvent(QColumnView component, QEvent event) {}
    public void mouseDoubleClickEvent(QColumnView component, QMouseEvent event) {}
    public void mouseMoveEvent(QColumnView component, QMouseEvent event) {}
    public void mousePressEvent(QColumnView component, QEvent event){}
    public void mouseReleaseEvent(QColumnView component, QMouseEvent event) {}
    public void moveEvent(QColumnView component, QMoveEvent event) {}
    public void paintEvent(QColumnView component, QPaintEvent event) {}
    public void resizeEvent(QColumnView component, QResizeEvent event) {}
    public void showEvent(QColumnView component, QEvent event){}
    public void tabletEvent(QColumnView component, QTabletEvent event) {}
    public void timerEvent(QColumnView component, QTimerEvent event) {}
    public void wheelEvent(QColumnView component, QWheelEvent event) {}
    /*grid layout events*/
    public void childEvent(QGridLayout component, QChildEvent event) {}
    public void timerEvent(QGridLayout component, QTimerEvent event) {}

}