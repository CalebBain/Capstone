package Compiler.Assemble;

import Compiler.Parser.Style;
import com.trolltech.qt.core.QRect;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.*;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Caleb Bain on 1/22/2016.
 */
public class CodeAssembler<T> {


    public CodeAssembler(String name) {
    }

    public void assemble(String name){
        StringBuilder sb = new StringBuilder();
        sb.append(
                "package GeneratedCode;\n" +
                "import com.trolltech.qt.core.*;\n" +
                "import com.trolltech.qt.gui.*;\n" +
                "public class" + name + " {\n" +
                    "\tpublic void run() {" +
                        "\t\tQApplication app = new QApplication(new String[0]);");
        sb.append(      "\t\tapp.exec();\n" +
                    "\t}\n" +
                "}");
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
