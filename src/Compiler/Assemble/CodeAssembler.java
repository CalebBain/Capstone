package Compiler.Assemble;

import Compiler.Parsers.ComponentParser;
import org.w3c.dom.Node;

import java.io.*;

public final class CodeAssembler {

    public CodeAssembler() {
    }

    public void assemble(String file, String name, Node node){
        StringBuilder sb = new StringBuilder();
        sb.append("import com.trolltech.qt.core.*;\n");
        sb.append("import com.trolltech.qt.gui.*;\n");
        sb.append("import java.lang.reflect.*;\n");
        sb.append("public class qt {\n");
        sb.append("\tpublic void run() {\n");
        sb.append(String.format("\t\tQApplication %s = new QApplication(new String[0]);\n", name));
        ComponentParser parser = new ComponentParser(file, name, sb, node);
        String styles = parser.StyleSheet();
        if (!styles.isEmpty()) sb.append(String.format("\t\t%s.setStyleSheet(\"%s\");\n", name, styles));
        sb.append("\t\twindow.show();\n");
        sb.append(String.format("\t\t%s.exec();\n",name));
        sb.append("\t}\n");
        sb.append("}");
        System.out.println(sb.toString());
        //write(sb, name);
    }

    public void write(StringBuilder sb, String name){
        String path = "~\\src\\GeneratedCode\\"+name+".java";
        File f = new File(path);
        if(f.exists() && !f.isDirectory()) f.delete();
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "utf-8"))) {
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
