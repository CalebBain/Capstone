package Compiler.Parsers;

import Compiler.Utils;
import org.w3c.dom.NamedNodeMap;

public class FunctionParser {

    public void MakeFunc(String name, String prop, StringBuilder sb, NamedNodeMap nodeMap){
        String[] callParts = new String[0];
        String p;
        if (!(p = Utils.check(prop, nodeMap)).isEmpty()){
            if (!p.isEmpty()) callParts = p.split(":");
            sb.append(name);
            if(callParts.length == 1) sb.append(String.format("QApplication.instance(), %1s);\n", callParts[0]));
            else if(callParts.length == 2) sb.append(String.format("%1s, %2s);\n", callParts[0], callParts[1]));
        }
    }

    public void onAbstractSliderFunctions(String name, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc("\t\t" + name + ".valueChanged.connect(", "on-value-change", sb, nodeMap);
        MakeFunc("\t\t" + name + ".sliderReleased.connect(", "on-release", sb, nodeMap);
        MakeFunc("\t\t" + name + ".sliderMoved.connect(", "on-move", sb, nodeMap);
        MakeFunc("\t\t" + name + ".sliderPressed.connect(", "on-press", sb, nodeMap);
        MakeFunc("\t\t" + name + ".actionTriggered.connect(", "on-action-trigger", sb, nodeMap);
        MakeFunc("\t\t" + name + ".actionTriggered.connect(", "on-range-change", sb, nodeMap);
        onWidgetFunctions(name, sb, nodeMap);
    }

    public void onAbstractButtonFunctions(String name, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc("\t\t" + name + ".clicked.connect(", "on-click", sb, nodeMap);
        MakeFunc("\t\t" + name + ".released.connect(", "on-release", sb, nodeMap);
        MakeFunc("\t\t" + name + ".pressed.connect(", "on-press", sb, nodeMap);
        MakeFunc("\t\t" + name + ".toggled.connect(", "on-toggle", sb, nodeMap);
        onWidgetFunctions(name, sb, nodeMap);
    }

    public void onAbstractItemViewFunctions(String name, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc("\t\t" + name + ".clicked.connect(", "on-click", sb, nodeMap);
        MakeFunc("\t\t" + name + ".pressed.connect(", "on-press", sb, nodeMap);
        MakeFunc("\t\t" + name + ".doubleClicked.connect(", "on-double-click", sb, nodeMap);
        MakeFunc("\t\t" + name + ".activated.connect(", "on-activate", sb, nodeMap);
        MakeFunc("\t\t" + name + ".entered.connect(", "on-enter", sb, nodeMap);
        MakeFunc("\t\t" + name + ".viewportEntered.connect(", "on-viewport-enter", sb, nodeMap);
        onWidgetFunctions(name, sb, nodeMap);
    }

    public void onWidgetFunctions(String name, StringBuilder sb, NamedNodeMap nodeMap){
        MakeFunc("\t\t" + name + ".customContextMenuRequested.connect(", "on-custom-context-menu-request", sb, nodeMap);
    }


}
