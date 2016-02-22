package Compiler.Parsers;

import Compiler.Utils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

public class SlotParser {

    public Map<String, String> Parse(Node methods){
        Map<String, String> result = new HashMap<>();
        NodeList methodCalls = methods.getChildNodes();
        for(int i = 0; i < methodCalls.getLength(); i++){
            Node method = methodCalls.item(i);
            NamedNodeMap attributes = method.getAttributes();
            String methodName = Utils.tryEmpty("name", "method", attributes);
            String methodCall = Utils.check("call", attributes);
            String classCall = Utils.check("class", attributes);
            String params = ParamsParser(attributes);
            String methodParams = params.replaceAll("(, )?this(, )?", "");
            String owner = Utils.tryEmpty("owner", "window", attributes);
            String Method = (result.containsKey(owner)) ? result.get(owner) : "";
            if(!methodCall.isEmpty() && !classCall.isEmpty()){
                if(classCall.contains("("))
                    Method += String.format("public Object %s(%s){ new %s.%s(%s); return null; }\n",
                            methodName, methodParams, classCall, methodCall, params);
                else Method += String.format("public Object %s(%s){ new %s().%s(%s); return null; }\n",
                        methodName, methodParams, classCall, methodCall, params);
            }

            result.put(owner, Method);
        }
        return result;
    }

    private String ParamsParser(NamedNodeMap attributes){
        int count = 1;
        boolean hasProps = false;
        StringBuilder value = new StringBuilder();
        String params = Utils.check("params", attributes);
        for(String p : params.split(",")){
            if(!p.isEmpty()){
                if(Utils.components.containsKey(p)){
                    p = Utils.components.get(p);
                }
                if(hasProps) value.append(", ");
                else hasProps = true;
                value.append(String.format("%s param%s", p, count++));
            }
        }
        return value.toString();
    }
}
