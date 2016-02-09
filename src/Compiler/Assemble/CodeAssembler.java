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

    public void assemble(String file, String name, Map<String, String> methodCalls, Node node){
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
    public InMemoryJavaFileObject(String className, String contents) throws Exception {
        super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.contents = contents;
    }

    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return contents;
    }
}
/*package Compiler.Assemble;

import Compiler.Parsers.ComponentParser;
import org.w3c.dom.Node;
import GeneratedCode.*;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

public final class CodeAssembler {

    public CodeAssembler() {
    }

    public void assemble(String file, String name, Map<String, String> methodCalls, Node node) {
        StringBuilder sb = new StringBuilder();
        sb.append("package GeneratedCode;\n");
        sb.append("import com.trolltech.qt.core.*;\nimport com.trolltech.qt.gui.*;\n");
        sb.append("class qt extends QApplication{\npublic qt() { super(new String[0]); run(); }\n");
        sb.append("public void run() {\n");
        ComponentParser parser = new ComponentParser(file, methodCalls, sb, node);
        String styles = parser.StyleSheet();
        if (!styles.isEmpty()) sb.append(String.format("%s.setStyleSheet(\"%s\");\n", name, styles));
        sb.append("window.show();\n");
        sb.append("this.exec();\n}\n}");
        String code = sb.toString();
        write("qt", code);
        System.out.println(code);
        new qt().run();
        //compile(code);
    }

    public void write(String name, String content){
        try {
            File file = new File("src/GeneratedCode/" + name + ".java");
            if (file.exists()){
                file.delete();
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void compile(String Code){
        try{

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            Iterable<? extends JavaFileObject> files = getJavaSourceFromString(Code);
            compiler.getTask(null, null, null, null, null, files);
            Class<?> c = Class.forName("GeneratedCode.qt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static Iterable<InMemoryJavaFileObject> getJavaSourceFromString(String code) throws Exception {
        final InMemoryJavaFileObject jsfs;
        jsfs = new InMemoryJavaFileObject("code", code);
        return new Iterable<InMemoryJavaFileObject>() {
            public Iterator<InMemoryJavaFileObject> iterator() {
                return new Iterator<InMemoryJavaFileObject>() {
                    boolean isNext = true;

                    public boolean hasNext() {
                        return isNext;
                    }

                    public InMemoryJavaFileObject next() {
                        if (!isNext) throw new NoSuchElementException();
                        isNext = false;
                        return jsfs;
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }
}

class InMemoryJavaFileObject extends SimpleJavaFileObject {
    final String code;
    public InMemoryJavaFileObject(String name, String code) throws Exception {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = code;
    }

    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return code;
    }
}*/
