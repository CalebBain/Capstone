package Compiler.Assemble;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CodeAssembler<T> {


    public CodeAssembler(String name) {
    }

    public void assemble(String name){
        StringBuilder sb = new StringBuilder();
        sb.append("package GeneratedCode;\n");
        sb.append("import com.trolltech.qt.core.*;\n");
        sb.append("import com.trolltech.qt.gui.*;\n");
        sb.append(String.format("public class %1s {\n", name));
        sb.append("public void run() {\n");
        sb.append("QApplication app = new QApplication(new String[0]);\n");
        sb.append("app.exec();\n");
        sb.append("}\n");
        sb.append("}");
        write(sb, name);
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
            Method method = c.getDeclaredMethod("run");
            method.invoke(c, null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
