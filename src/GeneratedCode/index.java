package GeneratedCode;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class index {

    public void run(){
        QApplication app = new QApplication(new String[0]);
        QMainWindow window = new QMainWindow();
        QWidget centerWidget = new QWidget();
        window.setCentralWidget(centerWidget);
        QGridLayout grid = new QGridLayout();
        centerWidget.setLayout(grid);
        QLCDNumber lcd = new QLCDNumber();
        lcd.setAccessibleName("lcd");
        lcd.setMode(QLCDNumber.Mode.Dec);
        QSlider slide = new QSlider();
        slide.setAccessibleName("slide");
        slide.setRange(0, 99);
        slide.setValue(0);
        slide.setTickPosition(QSlider.TickPosition.TicksAbove);
        slide.setOrientation(Qt.Orientation.Horizontal);
        slide.valueChanged.connect(lcd, "display(int)");
        QPushButton button = new QPushButton("button");
        grid.addWidget(lcd, 1, 1, Qt.AlignmentFlag.AlignAbsolute);
        grid.addWidget(slide, 2, 1, Qt.AlignmentFlag.AlignAbsolute);
        grid.addWidget(button, 4, 1, Qt.AlignmentFlag.AlignAbsolute);
        window.setStyleSheet(
                "QMainWindow { background-color: #1d1d1d; }\n" +
                "QPushButton { background-color: red; position: relative; right: 20px; color: white; }\n" +
                "QPushButton:hover { background-color: yellow; color: black; }\n" +
                "QPushButton:selected { color: red; }\n" +
                "QPushButton:flat { color: red; }\n" +
                "QPushButton:flat:hover { color: yellow; }\n" +
                "QSlider#slide:horizontal { height: 30px; width: 120px; }");
        app.exec();
    }
}
