package Compiler.Assemble;

import Compiler.Parsers.ComponentParser;
import org.w3c.dom.Node;

import java.io.*;

public final class CodeAssembler {

    public CodeAssembler() {
    }

    public void assemble(String file, String name, Node node){
        StringBuilder sb = new StringBuilder();
        sb.append("import com.trolltech.qt.core.*;\nimport com.trolltech.qt.gui.*;\nimport java.lang.reflect.*;\n");
        sb.append("public class qt extends QApplication{\npublic qt() { super(new String[0]); }\n");
        sb.append("public void run() {\n");
        ComponentParser parser = new ComponentParser(file, name, sb, node);
        String styles = parser.StyleSheet();
        if (!styles.isEmpty()) sb.append(String.format("%s.setStyleSheet(\"%s\");\n", name, styles));
        sb.append("window.show();\n");
        sb.append("this.exec();\n}\n}");
        System.out.println(sb.toString());
    }
}
