package Compiler.Parsers;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;
import Compiler.Utils;

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
            String params = Utils.check("params", attributes);
            String owner = Utils.tryEmpty("owner", "window", attributes);
            String Method = (result.containsKey(owner)) ? result.get(owner) : "";
            if(!methodCall.isEmpty() && !classCall.isEmpty())
                Method += String.format("public void %s(%s){ new %s().%s(%s); }\n",
                        methodName, params, classCall, methodCall, params);
            result.put(owner, Method);
        }


        return result;
    }
}
