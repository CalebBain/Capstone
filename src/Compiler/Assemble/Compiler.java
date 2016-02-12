package Compiler.Assemble;

import Compiler.Parsers.ComponentParser;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.w3c.dom.Node;
import java.util.*;

public final class Compiler {

    public Compiler() {
    }

    public void assemble(String file, Map<String, String> methodCalls, Node node){
        StringBuilder sb = new StringBuilder();
        sb.append("import com.trolltech.qt.core.*;\nimport com.trolltech.qt.gui.*;\n");
        sb.append("public class qt extends QApplication{\npublic qt() { super(new String[0]); run(); }\n");
        sb.append("public void run() {\n");
        ComponentParser parser = new ComponentParser(file, methodCalls, sb, node);
        String styles = parser.StyleSheet();
        if (!styles.isEmpty()) sb.append(String.format("this.setStyleSheet(\"%s\");\n", styles));
        sb.append("window.show();\n");
        sb.append("this.exec();\n}\n}");
        String Code = sb.toString();
        System.out.println(Code);
        new GroovyShell(new Binding()).evaluate(Code);
    }
}