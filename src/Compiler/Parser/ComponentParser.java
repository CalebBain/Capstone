package Compiler.Parser;

import QT.QtComponents.*;
import QT.QtComponents.Layouts.Grid;
import Compiler.Utils;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.gui.QLayout;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ComponentParser {
    private InlineStyleParser styles = new InlineStyleParser();
    private FunctionParser funcations = new FunctionParser();

    public String nodeLoop(NodeList nodeList, Component parent) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            QObject component = elementsSwitch(node.getNodeName(), parent, node);
            if (component instanceof QLayout) nodeLoop(node.getChildNodes(), (Component) component);
        }
        return sb.toString();
    }


    public QObject elementsSwitch(String name, Component parent, Node node){
        QObject component = null;
        switch (name) {
            case "check-box":

                component = new Checkbox(node, Utils.check("type", "tri-state", node.getAttributes()));
                sb.append(((Component) component).setStyle());
                if (parent != null) parent.addChild((Component) component, node);
                break;
            case "radio":
                component = new Radio(node);
                sb.append(((Component) component).setStyle());
                if (parent != null) parent.addChild((Component) component, node);
                break;
            case "button":
                component = new Button(node);
                sb.append(((Component) component).setStyle());
                if (parent != null) parent.addChild((Component) component, node);
                break;
            case "number":
                component = new QT.QtComponents.Number(node);
                sb.append(((Component) component).setStyle());
                if (parent != null) parent.addChild((Component)component, node);
                break;
            case "slider":
                component = new Slider(node);
                sb.append(((Component) component).setStyle());
                if (parent != null) parent.addChild((Component)component, node);
                break;
            case "grid":
                component = new Grid();
                sb.append(((Component) component).setStyle());
                if (parent != null) parent.addChild((Component)component, node);
                break;
        }
        return component;
    }

    private void CheckBox(String name, StringBuilder sb, NamedNodeMap nodeMap){
        String prop;
        Utils.tryBoolean(name, "checkable", "%1s.setTristate(%2s);\n", sb, nodeMap);
        if(!(prop = Utils.check("check-state", nodeMap)).isEmpty()){
            sb.append(name + ".setCheckState(Qt.CheckState.");
            switch(prop){
                case "unchecked": sb.append("Unchecked);\n"); break;
                case "partially-checked": sb.append("PartiallyChecked);\n"); break;
                case "checked": sb.append("Checked);\n"); break;
            }
        }
        styles.AbstractButton(name, sb, nodeMap);
        funcations.MakeFunc(name + ".stateChanged.connect(", Utils.check("on-state-change", nodeMap), sb, nodeMap);
        funcations.onAbstractButtonFunctions(name, sb, nodeMap);
    }


}
