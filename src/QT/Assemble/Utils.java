package QT.Assemble;

import QT.StyleComponents.Style;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.*;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.Map;

/**
 * Created by Caleb Bain on 1/13/2016.
 */
public final class Utils {

    public static boolean tryValue(String value) {
        boolean result = false;
        try {
            Integer.parseInt(value);
            result = true;
        } catch (NumberFormatException nfe) {
        }
        return result;
    }

    public static String check(String keyword, NamedNodeMap nodeMap) {
        String result = "";
        try {
            Node word = nodeMap.getNamedItem(keyword);
            result = (word != null) ? word.getNodeValue() : "";
        } catch (NullPointerException e) {
        }
        return result;
    }

    public static boolean check(String keyword, String check, NamedNodeMap nodeMap) {
        String result;
        try {
            Node word = nodeMap.getNamedItem(keyword);
            result = (word != null) ? word.getNodeValue() : "";
        } catch (NullPointerException e) {
            result =  "";
        }
        return result.equals(check);
    }

    private static void addAttribute(Style style, NamedNodeMap nodeMap, String attribute) {
        String prop = check(attribute, nodeMap);
        try {
            if (!prop.isEmpty()) style.addAttribute(attribute, prop);
        } catch (NullPointerException e) {
        }
    }

    public static void addAttribute(Style style, NamedNodeMap nodeMap, String attribute, String attributeName) {
        String prop = check(attribute, nodeMap);
        if (!prop.isEmpty()) style.addAttribute(attributeName, prop);
    }

    public static int[] StringArrayToIntArray(String prop, String split){
        String[] array = prop.split(split);
        int[] result = new int[array.length];
        for(int i = 0; i < result.length; i++) result[i] = Integer.parseInt(array[i]);
        return result;
    }

    public static String[] Func(String prop, NamedNodeMap nodeMap) {
        String call;
        if (!(call = Utils.check(prop, nodeMap)).isEmpty()) return call.split(":");
        return new String[0];
    }

    public static void onAbstractSliderFunctions(QAbstractSlider component, NamedNodeMap nodeMap){
        String[] callParts;
        if ((callParts = Func("on-value-change", nodeMap)).length == 1) component.valueChanged.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) component.valueChanged.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Func("on-release", nodeMap)).length == 1) component.sliderReleased.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) component.sliderReleased.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Func("on-move", nodeMap)).length == 1) component.sliderMoved.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) component.sliderMoved.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Func("on-press", nodeMap)).length == 1) component.sliderPressed.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) component.sliderPressed.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Func("on-action-trigger", nodeMap)).length == 1) component.actionTriggered.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) component.actionTriggered.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Func("on-range-change", nodeMap)).length == 1) component.rangeChanged.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) component.rangeChanged.connect(QT.findComponent(callParts[0]), callParts[1]);
        onWidgetFunctions(component, nodeMap);
    }

    public static void onAbstractButtonFunctions(QAbstractButton component, NamedNodeMap nodeMap){
        String[] callParts;
        if ((callParts = Func("on-click", nodeMap)).length == 1) component.clicked.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) component.clicked.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Func("on-release", nodeMap)).length == 1) component.released.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) component.released.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Func("on-press", nodeMap)).length == 1) component.pressed.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) component.pressed.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Func("on-toggle", nodeMap)).length == 1) component.toggled.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) component.toggled.connect(QT.findComponent(callParts[0]), callParts[1]);
        onWidgetFunctions(component, nodeMap);
    }

    public static void onAbstractItemViewFunctions(QAbstractItemView component, NamedNodeMap nodeMap){
        String[] callParts;
        if ((callParts = Func("on-click", nodeMap)).length == 1) component.clicked.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) component.clicked.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Func("on-press", nodeMap)).length == 1) component.pressed.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) component.pressed.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Func("on-double-click", nodeMap)).length == 1) component.doubleClicked.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) component.doubleClicked.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Func("on-activate", nodeMap)).length == 1) component.activated.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) component.activated.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Func("on-enter", nodeMap)).length == 1) component.entered.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) component.entered.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Func("on-viewport-enter", nodeMap)).length == 1) component.viewportEntered.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) component.viewportEntered.connect(QT.findComponent(callParts[0]), callParts[1]);
        onWidgetFunctions(component, nodeMap);
    }

    public static void onWidgetFunctions(QWidget component, NamedNodeMap nodeMap){
        String[] callParts;
        if ((callParts = Func("on-custom-context-menu-request", nodeMap)).length == 1)
            component.customContextMenuRequested.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) component.customContextMenuRequested.connect(QT.findComponent(callParts[0]), callParts[1]);
    }

    public static int[] StringArrayToIntArray(String[] array){
        int[] result = new int[array.length];
        for(int i = 0; i < result.length; i++) result[i] = Integer.parseInt(array[i]);
        return result;
    }

    public static String getStyleSheets(String component, Map<String, Style> styles, String Name, String Class){
        String name = (!Name.equals("")) ? Name : component;
        for (Map.Entry<String, Style> style : QT.styles.entrySet()) {
            if (style.getKey().startsWith(component)) {
                if (style.getKey().equals(component)) styles.get(name).addAll(style.getValue());
                else {
                    style.getValue().setComponent(component);
                    styles.put(style.getKey(), style.getValue());
                }
            }
            if (style.getKey().startsWith(Name) && !Name.isEmpty()) {
                if (style.getKey().equals(Name)) styles.get(name).addAll(style.getValue());
                else {
                    style.getValue().setComponent(component);
                    styles.put(style.getKey(), style.getValue());
                }
            }
            if (style.getKey().startsWith(Class) && !Class.isEmpty()) {
                if (style.getKey().equals(Class)) styles.get(name).addAll(style.getValue());
                else {
                    style.getValue().setComponent(component);
                    styles.put(style.getKey(), style.getValue());
                }
            }
        }
        return name;
    }

    public static void setWidgetProps(QWidget component, NamedNodeMap nodeMap){
        String prop;
        if(!(prop = check("margin", nodeMap)).isEmpty()){
            prop = prop.replace("px", "");
            int[] margins = StringArrayToIntArray(prop, " ");
            if(margins.length == 1) component.setContentsMargins(margins[0], margins[0], margins[0], margins[0]);
            else if(margins.length == 2) component.setContentsMargins(margins[0], margins[1], margins[0], margins[1]);
            else if(margins.length == 3) component.setContentsMargins(margins[0], margins[1], margins[2], margins[1]);
            else if(margins.length == 4) component.setContentsMargins(margins[0], margins[1], margins[2], margins[3]);
        }
        switch(check("cursor", nodeMap)){
            case "arrow": component.setCursor(new QCursor(Qt.CursorShape.ArrowCursor));
            case "up-arrow": component.setCursor(new QCursor(Qt.CursorShape.UpArrowCursor));
            case "cross": component.setCursor(new QCursor(Qt.CursorShape.CrossCursor));
            case "wait": component.setCursor(new QCursor(Qt.CursorShape.WaitCursor));
            case "vertical-beam": component.setCursor(new QCursor(Qt.CursorShape.IBeamCursor));
            case "size-vertical": component.setCursor(new QCursor(Qt.CursorShape.SizeVerCursor));
            case "size-horizontal": component.setCursor(new QCursor(Qt.CursorShape.SizeHorCursor));
            case "size-major-diagonal": component.setCursor(new QCursor(Qt.CursorShape.SizeFDiagCursor));
            case "size-minor-diagonal": component.setCursor(new QCursor(Qt.CursorShape.SizeBDiagCursor));
            case "size-all": component.setCursor(new QCursor(Qt.CursorShape.SizeAllCursor));
            case "blank": component.setCursor(new QCursor(Qt.CursorShape.BlankCursor));
            case "vertical-split": component.setCursor(new QCursor(Qt.CursorShape.SplitVCursor));
            case "horizontal-split": component.setCursor(new QCursor(Qt.CursorShape.SplitHCursor));
            case "hand-pointer": component.setCursor(new QCursor(Qt.CursorShape.PointingHandCursor));
            case "forbidden": component.setCursor(new QCursor(Qt.CursorShape.ForbiddenCursor));
            case "open-hand": component.setCursor(new QCursor(Qt.CursorShape.OpenHandCursor));
            case "closed-hand": component.setCursor(new QCursor(Qt.CursorShape.OpenHandCursor));
            case "question-mark": component.setCursor(new QCursor(Qt.CursorShape.WhatsThisCursor));
            case "busy": component.setCursor(new QCursor(Qt.CursorShape.BusyCursor));
            case "drag-move": component.setCursor(new QCursor(Qt.CursorShape.DragMoveCursor));
            case "drag-copy": component.setCursor(new QCursor(Qt.CursorShape.DragMoveCursor));
            case "drag-link": component.setCursor(new QCursor(Qt.CursorShape.DragLinkCursor));
            case "bitmap": component.setCursor(new QCursor(Qt.CursorShape.BitmapCursor));
        }
        if (Utils.check("mouse-tracking", "true", nodeMap)) component.setMouseTracking(true);
        else if (Utils.check("mouse-tracking", "false", nodeMap)) component.setMouseTracking(false);
        if (Utils.check("visibility", "show", nodeMap)) component.setHidden(false);
        else if (Utils.check("visibility", "hide", nodeMap)) component.setHidden(true);
        if(!(prop = check("tool-tip", nodeMap)).isEmpty()) component.setToolTip(prop);
        if (Utils.check("update", "true", nodeMap)) component.setUpdatesEnabled(true);
        else if (Utils.check("update", "false", nodeMap)) component.setUpdatesEnabled(false);
    }

    public static void setStyle(Style style, NamedNodeMap nodeMap) {
        String[] attributes = {"alt-background-color", "background", "background-color", "background-image", "image",
                "background-repeat", "background-position", "background-attachment", "background-clip", "opacity",
                "background-origin", "border", "border-top", "border-right", "border-bottom", "border-left", "color",
                "border-image", "border-color", "border-top-color", "font-weight", "border-right-color", "padding",
                "border-bottom-color", "border-left-color", "border-style", "border-top-style", "icon-size", "top",
                "border-right-style", "border-bottom-style", "border-left-style", "border-width", "border-top-width",
                "spacing", "border-right-width", "border-bottom-width", "border-left-width", "border-radius", "right",
                "border-top-left-radius", "border-top-right-radius", "border-top-right-radius", "text-decoration",
                "selection-background-color", "selection-color", "border-bottom-right-radius", "text-align",
                "border-bottom-left-radius", "bottom", "left", "height", "width", "gridline-color", "button-layout",
                "subcontrol-position", "font", "font-family", "font-size", "font-style", "image-position", "position",
                "padding-top", "margin-top", "margin-bottom", "margin-left", "max-height", "max-width", "min-height",
                "min-width", "padding-right", "padding-bottom", "margin-right", "padding-left", "subcontrol-origin"};
        for (String attribute : attributes) addAttribute(style, nodeMap, attribute);
        addAttribute(style, nodeMap, "alt-empty-row-color", "paint-alternating-row-colors-for-empty-area");
        addAttribute(style, nodeMap, "textbox-interaction", "messagebox-text-interaction-flags");
        addAttribute(style, nodeMap, "icon", "file-icon");
        addAttribute(style, nodeMap, "dialogbuttons-have-icons", "dialogbuttonbox-buttons-have-icons");
        addAttribute(style, nodeMap, "selection-decoration", "show-decoration-selected");
    }
}
