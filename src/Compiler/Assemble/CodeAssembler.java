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
        sb.append("public class qt extends QApplication{\n\tpublic qt() {\n\t\tsuper(new String[0]);\n\t}\n");
        sb.append("\tpublic void run() {\n");
        ComponentParser parser = new ComponentParser(file, name, sb, node);
        String styles = parser.StyleSheet();
        if (!styles.isEmpty()) sb.append(String.format("\t\t%s.setStyleSheet(\"%s\");\n", name, styles));
        sb.append("\t\twindow.show();\n");
        sb.append("\t\tthis.exec();\n\t}\n}");
        System.out.println(sb.toString());
    }
}
