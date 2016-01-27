package Compiler.Parsers;

import org.w3c.dom.NamedNodeMap;
import Compiler.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventParser {

    public void ParserParameters(String name, String component, String event, String param, StringBuilder sb){
        String[] params = ParamSwitch(name, component, event, param.split(" "));
        Write(params, sb);
    }

    private String[] ParamSwitch(String name, String component, String event, String... params){
        String Class = name.replaceAll(".jaml", "");
        component = component.replaceAll("-","_");
        event = event.replaceAll("-","_");
        String Method = component + "_" + event.replaceAll("when_", "");
        List<String> parameters = new ArrayList<>();
        parameters.add(component);
        for (String param : params){
            String[] parts = param.split(":");
            switch(parts[0]){
                case "class": Class = parts[1]; break;
                case "method": Method = parts[1]; break;
                case "component": case"components": parameters.add(parts[1]); break;
            }
        }
        parameters.add(event);
        String[] result = new String[parameters.size() + 2];
        result[0] = Class;
        result[1] = Method;
        System.arraycopy(parameters.toArray(), 0, result, 2, parameters.size());
        return result;
    }

    private void Write(String[] Params, StringBuilder sb){
        WriteClass(Params[0], sb);
        WriteMethod(Params[1], sb);
        String[] yourArray = Arrays.copyOfRange(Params, 2, Params.length);
        System.out.println(Arrays.toString(Params));
        System.out.println(Arrays.toString(yourArray));
        WriteParams(sb, yourArray);
    }

    private void WriteClass(String name, StringBuilder sb){
        sb.append(String.format("try{\n\tClass<?> c = Class.forName(\"%s\");\n", name));
    }

    private void WriteMethod(String name, StringBuilder sb){
        sb.append(String.format("\tMethod method = c.getMethod(\"%s\");\n", name));
    }

    private void WriteParams(StringBuilder sb, String... params){
        sb.append("\tmethod.invoke(c");
        for(String param : params) sb.append(String.format(", %s", param));
        sb.append(");\n} catch ( ReflectiveOperationException e) {\n\te.printStackTrace();\n}\n");
    }

    public void WidgetEvents(String name, String component, StringBuilder sb, NamedNodeMap nodeMap){
        String prop;
        boolean hasProps = false;
        if(!(prop = Utils.check("when-action", nodeMap)).isEmpty()){
            if(!hasProps) sb.append("{");
        }


        if(hasProps) sb.append("}");
        sb.append(";\n");
    }
}
