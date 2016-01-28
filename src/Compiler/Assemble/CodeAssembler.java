package Compiler.Assemble;

import Compiler.Parsers.ComponentParser;
import org.w3c.dom.Node;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CodeAssembler {

    public CodeAssembler() {
    }

    public void assemble(String file, String name, Node node){
        StringBuilder sb = new StringBuilder();
        sb.append("import com.trolltech.qt.core.*;\n");
        sb.append("import com.trolltech.qt.gui.*;\n");
        sb.append("public class qt {\n");
        sb.append("\tpublic void run() {\n");
        sb.append(String.format("\t\tQApplication %1s = new QApplication(new String[0]);\n", name));
        new ComponentParser(file, name, sb, node);
        sb.append("\t\tapp.exec();\n");
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

    public void run(String name){
        try {
            Class<?> c = Class.forName(name);
            Method method = c.getMethod("run");
            method.invoke(c, null);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
