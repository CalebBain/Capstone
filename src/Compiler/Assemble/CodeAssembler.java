package Compiler.Assemble;

import Compiler.Parsers.ComponentParser;
import org.w3c.dom.Node;

import javax.tools.*;
import java.io.*;
import java.net.URI;
import java.util.*;

public final class CodeAssembler {

    public CodeAssembler() {
    }

    public void assemble(String file, Map<String, String> methodCalls, Node node){
        StringBuilder sb = new StringBuilder();
        sb.append("package GeneratedCode;\n");
        sb.append("import com.trolltech.qt.core.*;\nimport com.trolltech.qt.gui.*;\n");
        sb.append("public class qt extends QApplication{\npublic qt() { super(new String[0]);\nrun();\n}\n");
        sb.append("public void run() {\n");
        ComponentParser parser = new ComponentParser(file, methodCalls, sb, node);
        String styles = parser.StyleSheet();
        if (!styles.isEmpty()) sb.append(String.format("this.setStyleSheet(\"%s\");\n", styles));
        sb.append("window.show();\n");
        sb.append("this.exec();\n}\n}");
        try(PrintWriter writer = new PrintWriter("src/GeneratedCode/qt.java", "UTF-8")) {
            String Code = sb.toString();
            System.out.println(Code);
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            JavaFileObject qtCode = new InMemoryJavaFileObject("GeneratedCode.qt", Code);
            Iterable<? extends JavaFileObject> files = Arrays.asList(qtCode);
            final Iterable<String> options = Arrays.asList("-d", "out/production/JAML/GeneratedCode/");
            JavaCompiler.CompilationTask task = compiler.getTask(null, null, null, options, null, files);
            if(task.call()){
                writer.println(Code);
                Class<?> c = Class.forName("GeneratedCode.qt");
                c.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class InMemoryJavaFileObject extends SimpleJavaFileObject {
    private String contents = null;
    InMemoryJavaFileObject(String className, String contents) throws Exception {
        super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.contents = contents;
    }

    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return contents;
    }
}