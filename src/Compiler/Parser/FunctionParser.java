package Compiler.Parser;

import Compiler.Utils;
import org.w3c.dom.NamedNodeMap;

public class FunctionParser {

    public void MakeFunc(String name, String prop, StringBuilder sb, NamedNodeMap nodeMap){
        String[] callParts = new String[0];
        if (!prop.isEmpty()){
            String call;
            if (!(call = Utils.check(prop, nodeMap)).isEmpty()) callParts = call.split(":");
            sb.append(name);
            if(callParts.length == 1) sb.append(String.format("QApplication.instance(), %1s);\n", callParts[0]));
            else if(callParts.length == 2) sb.append(String.format("%1s, %2s);\n", callParts[0], callParts[1]));
        }
    }

    public void onAbstractSliderFunctions(String name, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc(name + ".valueChanged.connect(", "on-value-change", sb, nodeMap);
        MakeFunc(name + ".sliderReleased.connect(", "on-release", sb, nodeMap);
        MakeFunc(name + ".sliderMoved.connect(", "on-move", sb, nodeMap);
        MakeFunc(name + ".sliderPressed.connect(", "on-press", sb, nodeMap);
        MakeFunc(name + ".actionTriggered.connect(", "on-action-trigger", sb, nodeMap);
        MakeFunc(name + ".actionTriggered.connect(", "on-range-change", sb, nodeMap);
        onWidgetFunctions(name, sb, nodeMap);
    }

    public void onAbstractButtonFunctions(String name, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc(name + ".clicked.connect(", "on-click", sb, nodeMap);
        MakeFunc(name + ".released.connect(", "on-release", sb, nodeMap);
        MakeFunc(name + ".pressed.connect(", "on-press", sb, nodeMap);
        MakeFunc(name + ".toggled.connect(", "on-toggle", sb, nodeMap);
        onWidgetFunctions(name, sb, nodeMap);
    }

    public void onAbstractItemViewFunctions(String name, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc(name + ".clicked.connect(", "on-click", sb, nodeMap);
        MakeFunc(name + ".pressed.connect(", "on-press", sb, nodeMap);
        MakeFunc(name + ".doubleClicked.connect(", "on-double-click", sb, nodeMap);
        MakeFunc(name + ".activated.connect(", "on-activate", sb, nodeMap);
        MakeFunc(name + ".entered.connect(", "on-enter", sb, nodeMap);
        MakeFunc(name + ".viewportEntered.connect(", "on-viewport-enter", sb, nodeMap);
        onWidgetFunctions(name, sb, nodeMap);
    }

    public void onWidgetFunctions(String name, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc(name + ".customContextMenuRequested.connect(", "on-custom-context-menu-request", sb, nodeMap);
    }


}
