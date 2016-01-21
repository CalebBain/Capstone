package QT.Assemble;

import QT.StyleComponents.Style;
import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.QRect;
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

    public static void setDimensions(Map<String, Style> styles, String name){
        QDesktopWidget desktop = new QDesktopWidget();
        styles.get(name).addAttribute("max-height", desktop.screenGeometry().height() + "");
        styles.get(name).addAttribute("min-height", "1");
        styles.get(name).addAttribute("max-width", desktop.screenGeometry().width() + "");
        styles.get(name).addAttribute("min-width", "1");
    }

    public static String getStyleSheets(String component, Map<String, Style> styles, String Name, String Class, NamedNodeMap nodeMap){
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
        Utils.setStyle(styles.get(name), nodeMap);
        return name;
    }

    public static void setAbstractItemViewProps(QAbstractItemView component, NamedNodeMap nodeMap){
        String prop;
        if (Utils.check("alt-row-color", "true", nodeMap)) component.setAlternatingRowColors(true);
        else if (Utils.check("alt-row-color", "false", nodeMap)) component.setAlternatingRowColors(false);
        if (Utils.tryValue((prop = Utils.check("auto-scroll-margin", nodeMap)))) component.setAutoScrollMargin(Integer.parseInt(prop));
        switch (Utils.check("drag-drop-mode", nodeMap)) {
            case "no-drag-drop": component.setDragDropMode(QAbstractItemView.DragDropMode.NoDragDrop); break;
            case "drop-only": component.setDragDropMode(QAbstractItemView.DragDropMode.DropOnly); break;
            case "drag-only": component.setDragDropMode(QAbstractItemView.DragDropMode.DragOnly); break;
            case "drag-drop": component.setDragDropMode(QAbstractItemView.DragDropMode.DragDrop); break;
            case "internal-move": component.setDragDropMode(QAbstractItemView.DragDropMode.InternalMove); break;
        }
        if (Utils.check("draggable", "true", nodeMap)) component.setDragEnabled(true);
        else if (Utils.check("draggable", "false", nodeMap)) component.setDragEnabled(false);
        switch (Utils.check("edit-trigger", nodeMap)) {
            case "no-edit-triggers": component.setEditTriggers(QAbstractItemView.EditTrigger.NoEditTriggers); break;
            case "current-change": component.setEditTriggers(QAbstractItemView.EditTrigger.CurrentChanged); break;
            case "double-click": component.setEditTriggers(QAbstractItemView.EditTrigger.DoubleClicked); break;
            case "selected-click": component.setEditTriggers(QAbstractItemView.EditTrigger.SelectedClicked); break;
            case "edit-key-press": component.setEditTriggers(QAbstractItemView.EditTrigger.EditKeyPressed); break;
            case "any-key-press": component.setEditTriggers(QAbstractItemView.EditTrigger.AnyKeyPressed); break;
            case "all-edit-triggers":
                component.setEditTriggers(QAbstractItemView.EditTrigger.AllEditTriggers); break;
        }
        if (Utils.check("auto-scroll", "true", nodeMap)) component.setAutoScroll(true);
        else if (Utils.check("auto-scroll", "false", nodeMap)) component.setAutoScroll(false);
        switch (Utils.check("horizontal-scroll-mode", nodeMap)) {
            case "per-item": component.setHorizontalScrollMode(QAbstractItemView.ScrollMode.ScrollPerItem); break;
            case "per-pixel": component.setHorizontalScrollMode(QAbstractItemView.ScrollMode.ScrollPerPixel); break;
        }
        switch (Utils.check("horizontal-scroll-mode", nodeMap)) {
            case "items": component.setSelectionBehavior(QAbstractItemView.SelectionBehavior.SelectItems); break;
            case "rows": component.setSelectionBehavior(QAbstractItemView.SelectionBehavior.SelectRows); break;
            case "columns": component.setSelectionBehavior(QAbstractItemView.SelectionBehavior.SelectColumns); break;
        }
        if (Utils.check("drop-indicator", "true", nodeMap)) component.setDropIndicatorShown(true);
        else if (Utils.check("drop-indicator", "false", nodeMap)) component.setDropIndicatorShown(false);
        if (Utils.check("tab-key-navigation", "true", nodeMap)) component.setTabKeyNavigation(true);
        else if (Utils.check("tab-key-navigation", "false", nodeMap)) component.setTabKeyNavigation(false);
        switch (Utils.check("text-elide-mode", nodeMap)) {
            case "left": component.setTextElideMode(Qt.TextElideMode.ElideLeft); break;
            case "right": component.setTextElideMode(Qt.TextElideMode.ElideRight); break;
            case "middle": component.setTextElideMode(Qt.TextElideMode.ElideMiddle); break;
            case "none": component.setTextElideMode(Qt.TextElideMode.ElideNone); break;
        }
        switch (Utils.check("vertical-scroll-mode", nodeMap)) {
            case "per-item": component.setVerticalScrollMode(QAbstractItemView.ScrollMode.ScrollPerItem); break;
            case "per-pixel": component.setVerticalScrollMode(QAbstractItemView.ScrollMode.ScrollPerPixel); break;
        }
        setAbstractScrollAreaProps(component, nodeMap);
    }

    public static void setAbstractScrollAreaProps(QAbstractScrollArea component, NamedNodeMap nodeMap){
        switch (Utils.check("horizontal-scroll-bar-policy", nodeMap)) {
            case "always-on": component.setHorizontalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAlwaysOn); break;
            case "as-needed": component.setHorizontalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAsNeeded); break;
            case "always-off": component.setHorizontalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAlwaysOff); break;
        }
        switch (Utils.check("vertical-scroll-bar-policy", nodeMap)) {
            case "always-on": component.setVerticalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAlwaysOn); break;
            case "as-needed": component.setVerticalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAsNeeded); break;
            case "always-off": component.setVerticalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAlwaysOff); break;
        }
        setFrameProps(component, nodeMap);
    }

    public static void setFrameProps(QFrame component, NamedNodeMap nodeMap){
        String prop;
        switch (Utils.check("shadow", nodeMap)) {
            case "sunken": component.setFrameShadow(QFrame.Shadow.Sunken); break;
            case "plain": component.setFrameShadow(QFrame.Shadow.Plain); break;
            case "raised": component.setFrameShadow(QFrame.Shadow.Raised); break;
        }
        switch (Utils.check("shape", nodeMap)) {
            case "no-frame": component.setFrameShape(QFrame.Shape.NoFrame); break;
            case "box": component.setFrameShape(QFrame.Shape.Box); break;
            case "panel": component.setFrameShape(QFrame.Shape.Panel); break;
            case "styled-panel": component.setFrameShape(QFrame.Shape.StyledPanel); break;
            case "horizontal-line": component.setFrameShape(QFrame.Shape.HLine); break;
            case "vertical-line": component.setFrameShape(QFrame.Shape.VLine); break;
            case "window-panel": component.setFrameShape(QFrame.Shape.WinPanel); break;
        }
        if(!(prop = check("size", nodeMap)).isEmpty()){
            int[] sizes = StringArrayToIntArray(prop, " ");
            if(sizes.length == 1) component.setFrameRect(new QRect(0, 0, sizes[0], sizes[0]));
            else if(sizes.length == 2) component.setFrameRect(new QRect(0, 0, sizes[0], sizes[1]));
            else if(sizes.length == 3) component.setFrameRect(new QRect(sizes[0], sizes[0], sizes[1], sizes[2]));
            else if(sizes.length == 4) component.setFrameRect(new QRect(sizes[0], sizes[1], sizes[2], sizes[3]));
        }
        setWidgetProps(component, nodeMap);
    }

    public static void setAbstractButtonProps(QAbstractButton component, NamedNodeMap nodeMap){
        String prop;
        if (Utils.check("checkable", "true", nodeMap)) component.setCheckable(true);
        else if (Utils.check("checkable", "false", nodeMap)) component.setCheckable(false);
        if (Utils.check("checked", "true", nodeMap)) component.setChecked(true);
        else if (Utils.check("checked", "false", nodeMap)) component.setChecked(false);
        if (Utils.check("pressed", "true", nodeMap)) component.setDown(true);
        else if (Utils.check("pressed", "false", nodeMap)) component.setDown(false);
        if (Utils.check("repeatable", "true", nodeMap)) component.setAutoRepeat(true);
        else if (Utils.check("repeatable", "false", nodeMap)) component.setAutoRepeat(false);
        if (Utils.check("exclusive", "true", nodeMap)) component.setAutoExclusive(true);
        else if (Utils.check("exclusive", "false", nodeMap)) component.setAutoExclusive(false);
        if (!(prop = Utils.check("text", nodeMap)).isEmpty()) component.setText(prop);
        if (!(prop = Utils.check("short-cut", nodeMap)).isEmpty()) component.setShortcut(prop);
        if (Utils.tryValue((prop = Utils.check("repeatable-delay", nodeMap)))) component.setAutoRepeatDelay(Integer.parseInt(prop));
        if (Utils.tryValue((prop = Utils.check("repeatable-interval", nodeMap)))) component.setAutoRepeatInterval(Integer.parseInt(prop));
        setWidgetProps(component, nodeMap);
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
