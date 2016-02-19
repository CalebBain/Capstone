package Compiler.Parsers;

import Compiler.Utils;
import org.w3c.dom.NamedNodeMap;

import java.util.HashMap;
import java.util.Map;

final class InlineStyleParser {
    private String prop;
    private String value = "";

    private Map<String, String> cursors = new HashMap<String, String>(){{
        put("arrow", "ArrowCursor");
        put("up-arrow", "UpArrowCursor");
        put("cross", "CrossCursor");
        put("wait", "WaitCursor");
        put("vertical-beam", "IBeamCursor");
        put("size-vertical", "SizeVerCursor");
        put("size-horizontal", "SizeHorCursor");
        put("size-major-diagonal", "SizeFDragCursor");
        put("size-minor-diagonal", "SizeBDragCursor");
        put("size-all", "SizeAllCursor");
        put("blank", "BlankCursor");
        put("vertical-split", "SplitVCursor");
        put("horizontal-split", "SplitHCursor");
        put("hand-pointer", "PointingHandCursor");
        put("forbidden", "ForbiddenCursor");
        put("open-hand", "OpenHandCursor");
        put("closed-hand", "OpenHandCursor");
        put("question-mark", "WhatsThisCursor");
        put("busy", "BusyCursor");
        put("drag-move", "DragMoveCursor");
        put("drag-copy", "DragMoveCursor");
        put("drag-link", "DragLinkCursor");
        put("bitmap", "BitmapCursor");
    }};

    private Map<String, String> StyleStrategy = new HashMap<String, String>(){{
        put("prefer-default", "PreferDefault");
        put("prefer-bitmap", "PreferBitmap");
        put("prefer-device", "PreferDevice");
        put("prefer-outline", "PreferOutline");
        put("force-outline", "ForceOutline");
        put("no-antialias", "NoAntialias");
        put("prefer-antialias", "PreferAntialias");
        put("opengl-compatible", "OpenGLCompatible");
        put("no-font-merging", "NoFontMerging");
        put("prefer-match", "PreferMatch");
        put("prefer-quality", "PreferQuality");
        put("force-integer-metrics", "ForceIntegerMetrics");
    }};

    private Map<String, String> TextFormatType = new HashMap<String, String>(){{
        put("none", "0");
        put("type", "1");
        put("table", "2");
        put("cell", "3");
        put("user", "0x1000");
    }};

    private Map<String, String> TextFormatDirection = new HashMap<String, String>(){{
        put("left-to-right", "Qt.LayoutDirection.LeftToRight");
        put("right-to-left", "Qt.LayoutDirection.RightToLeft");
        put("auto", "Qt.LayoutDirection.LayoutDirectionAuto");
    }};

    private Map<String, String> Alignment = new HashMap<String, String>(){{
        put("absolute", "Qt.AlignmentFlag.AlignAbsolute");
        put("bottom", "Qt.AlignmentFlag.AlignBottom");
        put("center", "Qt.AlignmentFlag.AlignCenter");
        put("horizontal-center", "Qt.AlignmentFlag.AlignHCenter");
        put("justify", "Qt.AlignmentFlag.AlignJustify");
        put("left", "Qt.AlignmentFlag.AlignLeft");
        put("right", "Qt.AlignmentFlag.AlignRight");
        put("top", "Qt.AlignmentFlag.AlignTop");
        put("vertical-center", "Qt.AlignmentFlag.AlignVCenter");
    }};

    private Map<String, String> ListMovement = new HashMap<String, String>(){{
        put("static", "QListView.Movement.Static");
        put("free", "QListView.Movement.Free");
        put("snap", "QListView.Movement.Snap");
    }};

    private Map<String, String> TabType = new HashMap<String, String>(){{
        put("center", "QTextOption.TabType.CenterTab");
        put("left", "QTextOption.TabType.LeftTab");
        put("right", "QTextOption.TabType.RightTab");
        put("delimiter", "QTextOption.TabType.DelimiterTab");
    }};

    private Map<String, String> Styles = new HashMap<String, String>(){{
        put("alt-background-color", "alt-background-color");      put("top", "top");      put("border", "border");
        put("background", "background");         put("border-right", "border-right");         put("left", "left");
        put("background-color", "background-color");                  put("background-image", "background-image");
        put("background-repeat", "background-repeat");     put("padding", "padding");     put("bottom", "bottom");
        put("background-attachment", "background-attachment");  put("right", "right");  put("opacity", "opacity");
        put("border-right-color", "border-right-color");                    put("border-bottom", "border-bottom");
        put("background-position", "background-position"); put("border-top", "border-top"); put("width", "width");
        put("selection-background-color", "selection-background-color");        put("border-left", "border-left");
        put("border-top-color", "border-top-color");   put("border-color", "border-color");   put("font", "font");
        put("border-image", "border-image");     put("font-weight", "font-weight");     put("spacing", "spacing");
        put("alt-empty-row-color", "paint-alternating-row-colors-for-empty-area");        put("height", "height");
        put("border-bottom-right-radius", "border-bottom-right-radius");        put("margin-left", "margin-left");
        put("border-top-style", "border-top-style");                put("border-left-style", "border-left-style");
        put("border-bottom-left-radius", "border-bottom-left-radius");      put("border-radius", "border-radius");
        put("background-origin", "background-origin");  put("border-top-right-radius", "border-top-right-radius");
        put("border-bottom-color", "border-bottom-color");      put("border-bottom-width", "border-bottom-width");
        put("dialogbuttons-have-icons", "dialogbuttonbox-buttons-have-icons");        put("position", "position");
        put("textbox-interaction", "messagebox-text-interaction-flags");                    put("color", "color");
        put("border-top-width", "border-top-width"); put("padding-top", "padding-top");     put("right", "right");
        put("border-bottom-style", "border-bottom-style");                    put("margin-right", "margin-right");
        put("border-top-left-radius", "border-top-left-radius");  put("image", "image"); put("icon", "file-icon");
        put("selection-decoration", "show-decoration-selected");        put("text-decoration", "text-decoration");
        put("subcontrol-position", "subcontrol-position");                    put("padding-left", "padding-left");
        put("border-right-style", "border-right-style");                 put("grid-line-color", "gridline-color");
        put("margin-bottom", "margin-bottom"); put("font-family", "font-family");   put("font-size", "font-size");
        put("max-width", "max-width");       put("icon-size", "icon-size");       put("font-style", "font-style");
        put("min-width", "min-width");   put("padding-right", "padding-right");   put("text-align", "text-align");
        put("border-right-width", "border-right-width");            put("border-left-color", "border-left-color");
        put("image-position", "image-position"); put("max-height", "max-height"); put("min-height", "min-height");
        put("background-clip", "background-clip");                  put("subcontrol-origin", "subcontrol-origin");
        put("border-style", "border-style"); put("border-width", "border-width"); put("margin-top", "margin-top");
        put("button-layout", "button-layout");                          put("selection-color", "selection-color");
        put("padding-bottom", "padding-bottom");                    put("border-left-width", "border-left-width");
    }};

    private Map<String, String> ListLayoutMode = new HashMap<String, String>(){{
        put("single-pass", "QListView.LayoutMode.SinglePass");
        put("batched", "QListView.LayoutMode.Batched");
    }};

    private Map<String, String> ListFlow = new HashMap<String, String>(){{
        put("horizontal", "QListView.Flow.LeftToRight");
        put("vertical", "QListView.Flow.TopToBottom");
    }};

    private Map<String, String> ListViewMode = new HashMap<String, String>(){{
        put("list", "QListView.ViewMode.ListMode");
        put("icon", "QListView.ViewMode.IconMode");
    }};

    private Map<String, String> ListResizeMode = new HashMap<String, String>(){{
        put("adjust", "QListView.ResizeMode.Adjust");
        put("fixed", "QListView.ResizeMode.Fixed");
    }};

    protected void List(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QListWidget");
        Utils.setName(n, sb);
        tryBoolean(n, "sorting", "%s.setSortingEnabled(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QListView", style);
        ListView(n, sb, nodeMap);
    }

    protected void ListView(String n, StringBuilder sb, NamedNodeMap nodeMap){
        if (!(prop = Utils.check("grid-size", nodeMap)).isEmpty()){
            String[] parts = prop.split(" ");
            sb.append(String.format("%s.setGridSize(new QSize(%s, %s);\n", n, parts[0], parts[1]));
        }
        TryList(n, "movement", "%s.setMovement(%s);\n", ListMovement, sb, nodeMap);
        TryList(n, "layout-mode", "%s.setLayoutMode(%s);\n", ListMovement, sb, nodeMap);
        TryList(n, "orientation", "%s.setFlow(%s);\n", ListMovement, sb, nodeMap);
        TryList(n, "view-mode", "%s.setViewMode(%s);\n", ListMovement, sb, nodeMap);
        TryList(n, "resize-mode", "%s.setResizeMode(%s);\n", ListMovement, sb, nodeMap);
        tryBoolean(n, "selection-rectangle-visible", "%s.setSelectionRectVisible(%s);\n", sb, nodeMap);
        tryBoolean(n, "uniform-sizes", "%s.setUniformItemSizes(%s);\n", sb, nodeMap);
        tryBoolean(n, "word-wrap", "%s.setWordWrap(%s);\n", sb, nodeMap);
        tryBoolean(n, "wrapping", "%s.setWrapping(%s);\n", sb, nodeMap);
        tryValue(n, "batch-size", "%s.setBatchSize(%s);\n", sb, nodeMap);
        tryValue(n, "model-column", "%s.setModelColumn(%s);\n", sb, nodeMap);
        tryValue(n, "spacing", "%s.setSpacing(%s);\n", sb, nodeMap);
        AbstractItemView(n, sb, nodeMap);
    }

    private Map<String, String> AbstractItemViewDragDropMode = new HashMap<String, String>(){{
        put("no-drag-drop", "QAbstractItemView.DragDropMode.NoDragDrop");
        put("drop-only", "QAbstractItemView.DragDropMode.DropOnly");
        put("drag-only", "QAbstractItemView.DragDropMode.DragOnly");
        put("drag-drop", "QAbstractItemView.DragDropMode.DragDrop");
        put("internal-move", "QAbstractItemView.DragDropMode.InternalMove");
    }};

    protected void AbstractItemView(String n, StringBuilder sb, NamedNodeMap nodeMap){
        if (!(prop = Utils.check("drag-drop-mode", nodeMap)).isEmpty())
            Utils.tryEmptyAppend(n, AbstractItemViewDragDropMode.get(value), "%s.setDragDropMode(%s);\n",sb);
        if (!(prop = Utils.check("edit-trigger", nodeMap)).isEmpty()){
            switch (prop) {
                case "no-edit-triggers": value = "NoEditTriggers"; break;
                case "current-change": value = "CurrentChanged"; break;
                case "double-click": value = "DoubleClicked"; break;
                case "selected-click": value = "SelectedClicked"; break;
                case "edit-key-press": value = "EditKeyPressed"; break;
                case "any-key-press": value = "AnyKeyPressed"; break;
                case "all-edit-triggers": value = "AllEditTriggers"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setEditTriggers(QAbstractItemView.EditTrigger.%s);\n",sb);
        }
        if (!(prop = Utils.check("horizontal-scroll-mode", nodeMap)).isEmpty()) {
            switch (prop) {
                case "per-item": value = "ScrollPerItem"; break;
                case "per-pixel": value = "ScrollPerPixel"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setHorizontalScrollMode(QAbstractItemView.ScrollMode.%s);\n",sb);
        }
        if (!(prop = Utils.check("selection-behavior", nodeMap)).isEmpty()) {
            switch (prop) {
                case "items": value = "SelectItems"; break;
                case "rows": value = "SelectRows"; break;
                case "columns": value = "SelectColumns"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setSelectionBehavior(QAbstractItemView.SelectionBehavior.%s);\n",sb);
        }
        if (!(prop = Utils.check("text-elide-mode", nodeMap)).isEmpty()) {
            switch (prop) {
                case "left": value = "ElideLeft"; break;
                case "right": value = "ElideRight"; break;
                case "middle": value = "ElideMiddle"; break;
                case "none": value = "ElideNone"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setTextElideMode(Qt.TextElideMode.%s);\n",sb);
        }
        if (!(prop = Utils.check("vertical-scroll-mode", nodeMap)).isEmpty()) {
            switch (prop) {
                case "per-item": value = "ScrollPerItem"; break;
                case "per-pixel": value = "ScrollPerPixel"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setVerticalScrollMode(QAbstractItemView.ScrollMode.%s);\n",sb);
        }
        tryBoolean(n, "alt-row-color", "%s.setAlternatingRowColors(%s);\n", sb, nodeMap);
        tryBoolean(n, "draggable", "%s.setDragEnabled(%s);\n", sb, nodeMap);
        tryBoolean(n, "auto-scroll", "%s.setAutoScroll(%s);\n", sb, nodeMap);
        tryBoolean(n, "drop-indicator", "%s.setDropIndicatorShown(%s);\n", sb, nodeMap);
        tryBoolean(n, "tab-key-navigation", "%s.TabKeyNavigation(%s);\n", sb, nodeMap);
        tryValue(n, "auto-scroll-margin", "%s.setAutoScrollMargin(%s);\n", sb, nodeMap);
        AbstractScrollArea(n, sb, nodeMap);
    }

    protected void TextEdit(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QListWidget");
        Utils.setName(n, sb);

        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QListView", style);
        AbstractScrollArea(n, sb, nodeMap);
    }

    private void TextCharFormat(String n, StringBuilder sb, NamedNodeMap nodeMap){
        Utils.setName(n, sb);
        tryBoolean(n, "anchor", "%s.setAnchor(%s);\n", sb, nodeMap);
    }

    private void TextFormat(String n, StringBuilder sb, NamedNodeMap nodeMap){
        if (!(prop = Utils.check("direction", nodeMap)).isEmpty())
            Utils.tryEmptyAppend(n, TextFormatDirection.get(prop), "%s.setLayoutDirection(%s);\n",sb);
        if (!(prop = Utils.check("type", nodeMap)).isEmpty())
            Utils.tryEmptyAppend(n, TextFormatType.get(prop), "%s.setObjectType(%s);\n",sb);
        if (!(prop = Utils.check("properties", nodeMap)).isEmpty()) {
            String[] props = prop.split(" ");
            for(String p : props){
                String[] parts = p.split(":");
                String key = "";
                switch (parts[0]) {
                    case "object-index": key = "0x0"; value = parts[1]; break;
                    case "css-float": key = "0x800"; value = parts[1]; break;
                    case "layout-direction": key = "0x801"; value = String.format("\"%s\"", parts[1]); break;
                    case "background-image": key = "0x823"; value = String.format("\"%s\"", parts[1]); break;
                    case "block-alignment": key = "0x1010"; value = String.format("%s", Alignment.get(parts[1])); break;
                    case "block-top-margin": key = "0x1030"; value = parts[1]; break;
                    case "block-bottom-margin": key = "0x1031"; value = parts[1]; break;
                    case "block-left-margin": key = "0x1032"; value = parts[1]; break;
                    case "block-right-margin": key = "0x1033"; value = parts[1]; break;
                    case "text-indent": key = "0x1034"; value = parts[1]; break;

                    case "block-indent": key = "0x1040"; value = parts[1]; break;

                    case "line-height": key = "0x1048"; value = parts[1]; break;

                    case "horizontal-ruler-width": key = "0x1060"; value = parts[1]; break;

                    case "font-family": key = "0x2000"; value = String.format("\"%s\"", parts[1]); break;
                    case "font-point-size": key = "0x2001"; value = parts[1]; break;
                    case "font-size-adjust": key = "0x2002"; value = parts[1]; break;
                    case "font-weight": key = "0x2003"; value = parts[1]; break;
                    case "italic": key = "0x2004"; value = parts[1]; break;

                    case "overline": key = "0x2006"; value = parts[1]; break;
                    case "strike-out": key = "0x2007"; value = parts[1]; break;
                    case "font-pitch": key = "0x2008"; value = parts[1]; break;
                    case "font-pixel-size": key = "0x2009"; value = parts[1]; break;

                    case "letter-spacing": key = "0x1FE1"; value = parts[1]; break;
                    case "word-spacing": key = "0x1FE2"; value = parts[1]; break;
                    case "style-hint": key = "0x1FE3"; value = parts[1]; break;
                    case "style-strategy": key = "0x1FE4"; value = StyleStrategy.get(parts[1]); break;
                    case "kerning": value = "0x1FE5"; break;
                    case "hinting-preference": value = "0x1FE6"; break;

                    case "underline-color": value = "0x2010"; break;

                    case "text-vertical-align": value = "0x2021"; break;
                    case "outline": value = "0x2022"; break;
                    case "underline-style": value = "0x2023"; break;
                    case "tool-tip": value = "0x2024"; break;

                    case "is-anchor": value = "0x2030"; break;
                    case "anchor-href": value = "0x2031"; break;
                    case "anchor-name": value = "0x2032"; break;

                    case "object-type": value = "0x2F00"; break;

                    case "list-style": value = "0x3000"; break;
                    case "list-indent": value = "0x3001"; break;
                    case "list-number-prefix": value = "0x3002"; break;
                    case "list-number-suffix": value = "0x3003"; break;

                    case "frame-border": value = "0x4000"; break;
                    case "frame-margin": value = "0x4001"; break;
                    case "frame-padding": value = "0x4002"; break;
                    case "frame-width": value = "0x4003"; break;
                    case "frame-height": value = "0x4004"; break;
                    case "frame-top-margin": value = "0x4005"; break;
                    case "frame-bottom-margin": value = "0x4006"; break;
                    case "frame-left-margin": value = "0x4007"; break;
                    case "frame-right-margin": value = "0x4008"; break;
                    case "frame-border-brush": value = "0x4009"; break;
                    case "light-cyan": value = "0x"; break;
                    case "cyan": value = "0x"; break;
                    /*case "dark-cyan": value = "0x"; break;
                    case "light-blue": value = "0x"; break;
                    case "blue": value = "0x"; break;
                    case "dark-blue": value = "0x"; break;
                    case "light-purple": value = "0x"; break;
                    case "purple": value = "0x"; break;
                    case "dark-purple": value = "0x"; break;
                    case "light-magenta": value = "0x"; break;
                    case "magenta": value = "0x"; break;
                    case "dark-magenta": value = "0x"; break;
                    case "dark-cyan": value = "0x"; break;
                    case "light-blue": value = "0x"; break;
                    case "blue": value = "0x"; break;
                    case "dark-blue": value = "0x"; break;
                    case "light-purple": value = "0x"; break;
                    case "purple": value = "0x"; break;
                    case "dark-purple": value = "0x"; break;
                    case "light-magenta": value = "0x"; break;
                    case "magenta": value = "0x"; break;
                    case "dark-magenta": value = "0x"; break;*/
                    default: value = String.format("%s", prop);
                }
                Utils.tryEmptyAppend(n, value, "%s.setProperty(" + key +", %s);\n", sb);
            }
        }
        tryValue(n, "index", "%s.setObjectIndex(%s);\n", sb, nodeMap);
    }

    private String Tab(StringBuilder sb, NamedNodeMap nodeMap){
        String name = Utils.trySetName("tab");
        sb.append(String.format("QTextOption_Tab %s = new QTextOption_Tab();\n", name));
        Utils.tryCheck(name, "delimiter", "%s.setText('%s');\n", sb, nodeMap);
        tryValue(name, "position", "%s.setPosition(%s);\n", sb, nodeMap);
        if(!(prop = Utils.check("type", nodeMap)).isEmpty())
            Utils.tryEmptyAppend(name, TabType.get(prop), "%s.setType(%s);\n",sb);
        return "";
    }

    private String Pen(StringBuilder sb, NamedNodeMap nodeMap){
        String name = Utils.trySetName("pen");
        sb.append(String.format("QPen %s = new QPen();\n", name));
        if(!(prop = Utils.check("cap-style", nodeMap)).isEmpty()) {
            switch (prop) {
                case "square": value = "SquareCap"; break;
                case "flat": value = "FlatCap"; break;
                case "round": value = "RoundCap"; break;
            }
            Utils.tryEmptyAppend(name, value, "%s.setCapStyle(Qt.PenCapStyle.%s);\n",sb);
        }
        if(!(prop = Utils.check("joint", nodeMap)).isEmpty()) {
            switch (prop) {
                case "bevel": value = "BevelJoin"; break;
                case "miter": value = "MiterJoin"; break;
                case "round": value = "RoundJoin"; break;
            }
            Utils.tryEmptyAppend(name, value, "%s.setJoinStyle(Qt.PenJoinStyle.%s);\n",sb);
        }
        if(!(prop = Utils.check("style", nodeMap)).isEmpty()) {
            switch (prop) {
                case "solid": value = "SolidLine"; break;
                case "dash": value = "DashLine"; break;
                case "dot": value = "DotLine"; break;
                case "dash-dot": value = "DashDotLine"; break;
                case "dash-double-dot": value = "DashDotDotLine"; break;
            }
            Utils.tryEmptyAppend(name, value, "%s.setStyle(Qt.PenStyle.%s);\n", sb);
        }
        tryBoolean(name, "pen-cosmetic", "%s.setCosmetic(%s);\n", sb, nodeMap);
        tryValue(name, "pen-dash-offset", "%s.setDashOffset(%s);\n", sb, nodeMap);
        tryValue(name, "pen-miter-limit", "%s.setMiterLimit(%s);\n", sb, nodeMap);
        tryValue(name, "pen-width", "%s.setWidth(%s);\n", sb, nodeMap);
        return name;
    }

    private String Brush(StringBuilder sb, NamedNodeMap nodeMap) {
        boolean brush = false;
        String name = "";
        if(!(prop = Utils.check("gradient", nodeMap)).isEmpty()) {
            String bname = Utils.trySetName("gradient");
            sb.append(String.format("QGradient %s = ", name));
            String[] parts = prop.split(" ");
            switch (parts[0]) {
                case "conical": sb.append(String.format("new QConicalGradient(%s);\n", parts[1])); break;
                case "linear": sb.append(String.format("new QLinearGradient(%s);\n", parts[1])); break;
                case "radial": sb.append(String.format("new QRadialGradient(%s);\n", parts[1])); break;
            }
            switch (parts[2]) {
                case "pad": sb.append(String.format("%s.setSpread(QGradient.Spread.PadSpread);\n", bname)); break;
                case "repeat": sb.append(String.format("%s.setSpread(QGradient.Spread.RepeatSpread);\n", bname)); break;
                case "reflect": sb.append(String.format("%s.setSpread(QGradient.Spread.ReflectSpread);\n", bname)); break;
            }
            switch (parts[3]) {
                case "logical": sb.append(String.format("%s.setCoordinateMode(QGradient.CoordinateMode.LogicalMode);\n", bname)); break;
                case "stretch": sb.append(String.format("%s.setCoordinateMode(QGradient.CoordinateMode.StretchToDeviceMode);\n", bname)); break;
                case "bounding": sb.append(String.format("%s.setCoordinateMode(QGradient.CoordinateMode.ObjectBoundingMode);\n", bname)); break;
            }
            name = Utils.trySetName("brush");
            sb.append(String.format("QBrush %s = new QBrush(%s);\n", name, bname));
        } else {
            if (!(prop = Utils.check("color", nodeMap)).isEmpty()) {
                name = Utils.trySetName("brush");
                sb.append(String.format("QBrush %s = new QBrush(", name));
                brush = true;
                switch (prop) {
                    case "white": value = "#FFFFFF"; break;
                    case "light-gray": value = "#D3D3D3"; break;
                    case "gray": value = "#808080"; break;
                    case "dark-gray": value = "#A9A9A9"; break;
                    case "black": value = "#000000"; break;
                    case "light-red": value = "#FFC0CB"; break;
                    case "red": value = "#FF0000"; break;
                    case "dark-red": value = "#8B0000"; break;
                    case "light-orange": value = "#FFD700"; break;
                    case "orange": value = "#FFA500"; break;
                    case "dark-orange": value = "#FF8C00"; break;
                    case "light-gold": value = "#FFEC8B"; break;
                    case "gold": value = "#FFD700"; break;
                    case "dark-gold": value = "#FFB90F"; break;
                    case "light-yellow": value = "#FAFAD2"; break;
                    case "yellow": value = "#FFFF00"; break;
                    case "dark-yellow": value = "#CDCD00"; break;
                    case "light-green": value = "#80FF80"; break;
                    case "green": value = "#00FF00"; break;
                    case "dark-green": value = "#008000"; break;
                    case "light-cyan": value = "#E0FFFF"; break;
                    case "cyan": value = "#00FFFF"; break;
                    case "dark-cyan": value = "#008B8B"; break;
                    case "light-blue": value = "#ADD8E6"; break;
                    case "blue": value = "#0000FF"; break;
                    case "dark-blue": value = "#00008B"; break;
                    case "light-purple": value = "#EE82EE"; break;
                    case "purple": value = "#800080"; break;
                    case "dark-purple": value = "#9400D3"; break;
                    case "light-magenta": value = "#FFBBFF"; break;
                    case "magenta": value = "#FF00FF"; break;
                    case "dark-magenta": value = "#8B008B"; break;
                    default: value = String.format("%s", prop);
                }
                sb.append(value);
            }
            if (!(prop = Utils.check("pattern", nodeMap)).isEmpty()) {
                if (!brush) sb.append("new QBrush(");
                else sb.append(",");
                brush = true;
                switch (prop) {
                    case "solid": value = "SolidPattern"; break;
                    case "extremely-dense": value = "Dense1Pattern"; break;
                    case "very-dense": value = "Dense2Pattern"; break;
                    case "somewhat-dense": value = "Dense3Pattern"; break;
                    case "half-dense": value = "Dense4Pattern"; break;
                    case "somewhat-sparse": value = "Dense5Pattern"; break;
                    case "very-sparse": value = "Dense6Pattern"; break;
                    case "extremely-sparse": value = "Dense7Pattern"; break;
                    case "empty": value = "NoBrush"; break;
                    case "horizontal-lines": value = "HorPattern"; break;
                    case "vertical-lines": value = "VerPattern"; break;
                    case "cross-lines": value = "CrossPattern"; break;
                    case "backward-diagonal-lines": value = "BDiagPattern"; break;
                    case "forward-diagonal-lines": value = "FDiagPattern"; break;
                    case "diagonal-cross-lines": value = "DiagCrossPattern"; break;
                }
                sb.append("Qt.BrushStyle." + value);
            }
            if(brush) sb.append(")");
        }
        return name;
    }

    private void AbstractScrollArea(String n, StringBuilder sb, NamedNodeMap nodeMap){
        if(!(prop = Utils.check("horizontal-scroll-bar-policy", nodeMap)).isEmpty()){
            sb.append(String.format("%s.setHorizontalScrollBarPolicy(Qt.ScrollBarPolicy.", n));
            switch (prop) {
                case "always-on": value = "ScrollBarAlwaysOn"; break;
                case "as-needed": value = "ScrollBarAsNeeded"; break;
                case "always-off": value = "ScrollBarAlwaysOff"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setHorizontalScrollBarPolicy(Qt.ScrollBarPolicy.%s);\n",sb);
        }
        if(!(prop = Utils.check("vertical-scroll-bar-policy", nodeMap)).isEmpty()){
            sb.append(String.format("%s.setVerticalScrollBarPolicy(Qt.ScrollBarPolicy.", n));
            switch (prop) {
                case "always-on": value = "ScrollBarAlwaysOn"; break;
                case "as-needed": value = "ScrollBarAsNeeded"; break;
                case "always-off": value = "ScrollBarAlwaysOff"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setVerticalScrollBarPolicy(Qt.ScrollBarPolicy.%s);\n", sb);
        }
        Frame(n, sb, nodeMap);
    }

    protected void LCDNumber(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QLCDNumber");
        Utils.setName(n, sb);
        Utils.tryCapitalize(n, "segment-style", "%s.setSegmentStyle(QLCDNumber.SegmentStyle.%s);\n", sb, nodeMap);
        Utils.tryCapitalize(n, "mode", "%s.setMode(QLCDNumber.Mode.%s);\n", sb, nodeMap);
        tryBoolean(n, "small-decimal-point", "%s.setSmallDecimalPoint(%s);\n", sb, nodeMap);
        tryValue(n, "digit-count", "%s.setDigitCount(%s);\n", sb, nodeMap);
        tryValue(n, "value", "%s.display(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QLCDNumber", style);
        Frame(n, sb, nodeMap);
    }

    protected void Label(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QLabel");
        Utils.setName(n, sb);
        if (!(prop = Utils.check("align", nodeMap)).isEmpty()){
            switch (prop) {
                case "left": value = "AlignLeft"; break;
                case "right": value = "AlignRight"; break;
                case "horizontal-center": value = "AlignHCenter"; break;
                case "justify": value = "AlignJustify"; break;
                case "top": value = "AlignTop"; break;
                case "bottom": value = "AlignBottom"; break;
                case "vertical-center": value = "AlignVCenter"; break;
                case "center": value = "AlignCenter"; break;
                case "absolute": value = "AlignAbsolute"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setAlignment(Qt.AlignmentFlag.%s);\n",sb);
        }
        if (!(prop = Utils.check("text-format", nodeMap)).isEmpty()){
            switch (prop) {
                case "auto": value = "AutoText"; break;
                case "plain": value = "PlainText"; break;
                case "rich": value = "RichText"; break;
                case "log": value = "LogText"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setTextFormat(Qt.TextFormat.%s);\n",sb);
        }
        if (!(prop = Utils.check("text-interaction", nodeMap)).isEmpty()){
            String[] flags = prop.split(" ");
            for(int i = 0; i < flags.length;){
                switch (flags[i]) {
                    case "none": value += "Qt.TextInteractionFlag.NoTextInteraction"; break;
                    case "mouse-select": value += "Qt.TextInteractionFlag.TextSelectableByMouse"; break;
                    case "key-select": value += "Qt.TextInteractionFlag.TextSelectableByKeyboard"; break;
                    case "mouse-access": value += "Qt.TextInteractionFlag.LinksAccessibleByMouse"; break;
                    case "key-access": value += "Qt.TextInteractionFlag.LinksAccessibleByKeyboard"; break;
                    case "editable": value += "Qt.TextInteractionFlag.TextEditable"; break;
                    case "editor": value += "Qt.TextInteractionFlag.TextEditorInteraction"; break;
                    case "browser": value += "Qt.TextInteractionFlag.TextBrowserInteraction"; break;
                }
                if(++i < flags.length) value += ", ";
            }
            Utils.tryEmptyAppend(n, value, "%s.setAlignment(Qt.AlignmentFlag.%s);\n",sb);
        }
        Utils.tryCheck(n, "text", "%s.setText(tr(\"%s\"));\n", sb, nodeMap);
        tryBoolean(n, "open-external-links", "%s.setOpenExternalLinks(%s);\n", sb, nodeMap);
        tryBoolean(n, "word-warp", "%s.setWordWrap(%s);\n", sb, nodeMap);
        tryBoolean(n, "scaled-contents", "%s.setScaledContents(%s);\n", sb, nodeMap);
        tryValue(n, "indent", "%s.setIndent(%s);\n", sb, nodeMap);
        tryValue(n, "margin", "%s.setMargin(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QLCDNumber", style);
        Frame(n, sb, nodeMap);
    }

    protected void Splitter(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap) {
        Style style = new Style(n, "QSplitter");
        Utils.setName(n, sb);
        if(!(prop = Utils.check("orientation", nodeMap)).isEmpty()){
            switch(prop){
                case "vertical": value = "Vertical"; break;
                case "horizontal": value = "Horizontal"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setOrientation(Qt.Orientation.%s);\n", sb);
        }
        tryBoolean(n, "resizeable", "%s.setOpaqueResize(%s);\n", sb, nodeMap);
        tryValue(n, "handle-width", "%s.setHandleWidth(%s);\n", sb, nodeMap);
        tryValue(n, "rubber-band", "%s.setRubberBand(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QLCDNumber", style);
        Frame(n, sb, nodeMap);
    }

    protected void Frame(String n, StringBuilder sb, NamedNodeMap nodeMap){
        if(!(prop = Utils.check("shadow", nodeMap)).isEmpty()){
            switch (prop) {
                case "sunken": value = "Sunken"; break;
                case "plain": value = "Plain"; break;
                case "raised": value = "Raised"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setFrameShadow(QFrame.Shadow.%s);\n",sb);
        }
        if(!(prop = Utils.check("shape", nodeMap)).isEmpty()){
            switch (prop) {
                case "no-frame": value = "NoFrame"; break;
                case "box": value = "Box"; break;
                case "panel": value = "Panel"; break;
                case "styled-panel": value = "StyledPanel"; break;
                case "horizontal-line": value = "HLine"; break;
                case "vertical-line": value = "VLine"; break;
                case "window-panel": value = "WinPanel"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setFrameShape(QFrame.Shape.%s);\n",sb);
        }
        if(!(prop = Utils.check("size", nodeMap)).isEmpty()){
            String[] sizes = prop.split(" ");
            sb.append(String.format("%s.setFrameRect(new QRect(", n));

            String v1 = "", v2 = "", v3 = "", v4 = "";
            try {
                v1 = sizes[0]; v2 = sizes[1]; v3 = sizes[2]; v4 = sizes[3];
            }catch (NullPointerException ignored){
            }
            switch (sizes.length){
                case 4: sb.append(String.format("0, 0, %s, %s));\n", v1, v1)); break;
                case 3: sb.append(String.format("0, 0, %s, %s));\n", v1, v2)); break;
                case 2: sb.append(String.format("%s, %s, %3s, %4s));\n", v1, v1, v2, v3)); break;
                case 1: sb.append(String.format("%s, %s, %3s, %4s));\n", v1, v2, v3, v4)); break;
            }
        }
        Widget(n, sb, nodeMap);
    }

    protected void Slider(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QSlider");
        Utils.setName(n, sb);
        if(!(prop = Utils.check("tick-position", nodeMap)).isEmpty()){
            sb.append(String.format("%s.setTickPosition(QSlider.TickPosition.", n));
            switch(prop){
                case "both": value = "TicksBothSides"; break;
                case "above": case "left": value = "TicksAbove"; break;
                case "below": case "right": value = "TicksBelow"; break;
                case "no-ticks": value = "NoTicks"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setTickPosition(QSlider.TickPosition.%s);\n", sb);
        }
        tryValue(n, "interval", "%s.setTickInterval(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QSlider", style);
        AbstractSlider(n, sb, nodeMap);
    }

    private void AbstractSlider(String n, StringBuilder sb, NamedNodeMap nodeMap){
        String[] range = Utils.check("range", nodeMap).split(" ");
        if (range.length > 1) sb.append(String.format("%s.setRange(%s, %s);\n", n, range[0], range[1]));
        if(!(prop = Utils.check("orientation", nodeMap)).isEmpty()){
            switch(prop){
                case "vertical": value = "Vertical"; break;
                case "horizontal": value = "Horizontal"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setOrientation(Qt.Orientation.%s);\n", sb);
        }
        if(!(prop = Utils.check("invert-numbers", nodeMap)).isEmpty()){
            switch(prop){
                case "vertical": value = "Vertical"; break;
                case "horizontal": value = "Horizontal"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setInvertedControls(Qt.Orientation.%s);\n", sb);
        }
        tryBoolean(n, "invert-numbers", "%s.setInvertedControls(%s);\n", sb, nodeMap);
        tryBoolean(n, "invert-controls", "%s.setInvertedAppearance(%s);\n", sb, nodeMap);
        tryBoolean(n, "tracking", "%s.setTracking(%s);\n", sb, nodeMap);
        tryBoolean(n, "slide-down-tracking", "%s.setSliderPosition(%s);\n", sb, nodeMap);
        tryValue(n, "min-value", "%s.setMinimum(%s);\n", sb, nodeMap);
        tryValue(n, "max-value", "%s.setMaximum(%s);\n", sb, nodeMap);
        tryValue(n, "value", "%s.setValue(%s);\n", sb, nodeMap);
        tryValue(n, "page-steps", "%s.setPageStep(%s);\n", sb, nodeMap);
        tryValue(n, "single-steps", "%s.setSingleStep(%s);\n", sb, nodeMap);
        tryValue(n, "slide-position", "%s.setSliderPosition(%s);\n", sb, nodeMap);
        Widget(n, sb, nodeMap);
    }

    protected void PushButton(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QPushButton");
        Utils.setName(n, sb);
        Utils.tryCheck(n, "shortcut", "%s.setShortcut(new QKeySequence(tr(\"%s\"));\n", sb, nodeMap);
        tryBoolean(n, "default", "%s.setDefault(%s);\n", sb, nodeMap);
        tryBoolean(n, "flat", "%s.setFlat(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QPushButton", style);
        AbstractButton(n, sb, nodeMap);
    }

    protected void RadioButton(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QRadioButton");
        Utils.setName(n, sb);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QRadioButton", style);
        AbstractButton(n, sb, nodeMap);
    }

    protected void CheckBox(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QCheckBox");
        Utils.setName(n, sb);
        if(!(prop = Utils.check("check-state", nodeMap)).isEmpty()){
            switch(prop){
                case "unchecked": value = "Unchecked"; break;
                case "partially-checked": value = "PartiallyChecked"; break;
                case "checked": value = "Checked"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setCheckState(Qt.CheckState.%s);\n", sb);
        }
        tryBoolean(n, "checkable", "%s.setTristate(%s);\n", sb, nodeMap);
        tryBoolean(n, "default", "%s.setDefaultUp(%s);\n", sb, nodeMap);
        tryBoolean(n, "native-menubar", "%s.setNativeMenuBar(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QCheckBox", style);
        AbstractButton(n, sb, nodeMap);
    }

    private void AbstractButton(String n, StringBuilder sb, NamedNodeMap nodeMap){
        Utils.tryCheck(n, "text", "%s.setText(\"%s\");\n", sb, nodeMap);
        tryBoolean(n, "checkable", "%s.setCheckable(%s);\n", sb, nodeMap);
        tryBoolean(n, "checked", "%s.setChecked(%s);\n", sb, nodeMap);
        tryBoolean(n, "pressed", "%s.setDown(%s);\n", sb, nodeMap);
        tryBoolean(n, "repeatable", "%s.setAutoRepeat(%s);\n", sb, nodeMap);
        tryBoolean(n, "exclusive", "%s.setAutoExclusive(%s);\n", sb, nodeMap);
        tryValue(n, "repeatable-delay", "%s.setAutoRepeatDelay(%s);\n", sb, nodeMap);
        tryValue(n, "repeatable-interval", "%s.setAutoRepeatInterval(%s);\n", sb, nodeMap);
        Widget(n, sb, nodeMap);
    }



    protected void MainWindow(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QMainWindow");
        sb.append("QWidget centerWidget = new QWidget();\n");
        sb.append(String.format("%s.setCentralWidget(centerWidget);\n", n));
        sb.append(String.format("%s.setWindowTitle(tr(\"%s\"));\n", n, Utils.tryEmpty("title", "JAML Applicaiton", nodeMap)));
        if(!(prop = Utils.check("dock-option", nodeMap)).isEmpty()){
            switch(prop){
                case "animated-docks": value = "AnimatedDocks"; break;
                case "allow-nested-docks": value = "AllowNestedDocks"; break;
                case "allow-tabbed-docks": value = "AllowTabbedDocks"; break;
                case "force-tabbed-docks": value = "ForceTabbedDocks"; break;
                case "vertical-tabs": value = "VerticalTabs"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setDockOptions(DockOption.%s);\n", sb);
        }
        if(!(prop = Utils.check("tab-shape", nodeMap)).isEmpty()){
            switch(prop){
                case "rounded": value = "Rounded"; break;
                case "triangular": value = "Triangular"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setTabShape(QTabWidget.TabShape.%s);\n", sb);
        }
        if(!(prop = Utils.check("tool-button-style", nodeMap)).isEmpty()){
            switch(prop){
                case "icon-only": value = "ToolButtonIconOnly"; break;
                case "text-only": value = "ToolButtonTextOnly"; break;
                case "text-beside-icon": value = "ToolButtonTextBesideIcon"; break;
                case "text-under-icon": value = "ToolButtonTextUnderIcon"; break;
                case "follow-style": value = "ToolButtonFollowStyle"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setToolButtonStyle(Qt.ToolButtonStyle.%s);\n", sb);
        }
        tryBoolean(n, "dock-animation", "%s.setAnimated(%s);\n", sb, nodeMap);
        tryBoolean(n, "dock-nesting", "%s.setDockNestingEnabled(%s);\n", sb, nodeMap);
        tryBoolean(n, "document-mode", "%s.setDocumentMode(%s);\n", sb, nodeMap);
        tryBoolean(n, "unified-mac-title-toolbar", "%s.setUnifiedTitleAndToolBarOnMac(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put(n, style);
        Widget(n, sb, nodeMap);
    }

    protected void MenuBar(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QMenuBar");
        Utils.setName(n, sb);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QMenuBar", style);
        Widget(n, sb, nodeMap);
    }

    protected void Menu(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QMenu");
        Utils.setName(n, sb);
        Utils.tryCheck(n, "icon", "%s.setIcon(\"%s\");\n", sb, nodeMap);
        Utils.tryCheck(n, "title", "%s.setTitle(\"%s\");\n", "menu", sb, nodeMap);
        tryBoolean(n, "tear-off", "%s.setTearOffEnabled(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QMenu", style);
        Widget(n, sb, nodeMap);
    }

    protected void Section(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QWidget");
        Utils.setName(n, sb);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QGridLayout", style);
        Widget(n, sb, nodeMap);
    }

    protected void LineEdit(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap) {
        Style style = new Style(n, "QLineEdit");
        Utils.setName(n, sb);
        if (!(prop = Utils.check("align", nodeMap)).isEmpty()) {
            switch (prop) {
                case "left": value = "AlignLeft"; break;
                case "right": value = "AlignRight"; break;
                case "horizontal-center": value = "AlignHCenter"; break;
                case "justify": value = "AlignJustify"; break;
                case "top": value = "AlignTop"; break;
                case "bottom": value = "AlignBottom"; break;
                case "vertical-center": value = "AlignVCenter"; break;
                case "center": value = "AlignCenter"; break;
                case "absolute": value = "AlignAbsolute"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setAlignment(Qt.AlignmentFlag.%s);\n", sb);
        }
        if (!(prop = Utils.check("echo-mode ", nodeMap)).isEmpty()) {
            switch (prop) {
                case "normal": value = "Normal"; break;
                case "none": value = "NoEcho"; break;
                case "password": value = "Password"; break;
                case "echo-on-edit": value = "PasswordEchoOnEdit"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setEchoMode(QLineEdit.EchoMode.%s);\n", sb);
        }
        if (!(prop = Utils.check("cursor-move-style", nodeMap)).isEmpty()) {
            switch (prop) {
                case "logical": value = "LogicalMoveStyle"; break;
                case "visual": value = "VisualMoveStyle"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setCursorMoveStyle(Qt.CursorMoveStyle.%s);\n", sb);
        }
        Utils.tryCheck(n, "placeholder", "%s.setPlaceholderText(\"%s\");\n", sb, nodeMap);
        tryBoolean(n, "draggable", "%s.setDragEnabled(%s);\n", sb, nodeMap);
        tryBoolean(n, "frame", "%s.setFrame(%s);\n", sb, nodeMap);
        tryBoolean(n, "modified", "%s.setModified(%s);\n", sb, nodeMap);
        tryBoolean(n, "read-only", "%s.setReadOnly(%s);\n", sb, nodeMap);
        tryValue(n, "max-length", "%s.setMaxLength(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if (!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QAction", style);
        Widget(n, sb, nodeMap);
    }

    protected void Group(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap) {
        Style style = new Style(n, "QGroupBox");
        Utils.setName(n, sb);
        if (!(prop = Utils.check("align", nodeMap)).isEmpty()){
            switch (prop) {
                case "left": value = "1"; break;
                case "right": value = "2"; break;
                case "horizontal-center": value = "4"; break;
                case "justify": value = "8"; break;
                case "absolute": value = "10"; break;
                case "top": value = "20"; break;
                case "bottom": value = "40"; break;
                case "vertical-center": value = "80"; break;
                case "center": value = "84"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setTextAlignment(%s);\n",sb);
        }
        Utils.tryCheck(n, "title", "%s.setTitle(\"%s\");\n", sb, nodeMap);
        tryBoolean(n, "flat", "%s.setFlat(%s);\n", sb, nodeMap);
        tryBoolean(n, "checkable", "%s.setCheckable(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if (!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QAction", style);
        Widget(n, sb, nodeMap);
    }

    protected void Widget(String n, StringBuilder sb, NamedNodeMap nodeMap){
        if(!(prop = Utils.check("margin", nodeMap)).isEmpty()) {
            String[] margins = prop.replaceAll("px", "").split(" ");
            sb.append(String.format("%s.setContentsMargins(", n));
            String v1 = "", v2 = "", v3 = "", v4 = "";
            switch (margins.length){
                case 4: v1 = margins[0]; v2 = margins[1]; v3 = margins[2]; v4 = margins[3]; break;
                case 3: v1 = margins[0]; v2 = margins[1]; v3 = margins[2]; v4 = margins[1]; break;
                case 2: v1 = margins[0]; v2 = margins[1]; v3 = margins[0]; v4 = margins[1]; break;
                case 1: v1 = margins[0]; v2 = margins[0]; v3 = margins[0]; v4 = margins[0]; break;
            }
            String value = String.format("%s, %s, %s, %s", v1, v2, v3, v4);
            Utils.tryEmptyAppend(n, value, "%s.setContentsMargins(%s));\n", sb);
        }
        if(!(prop = Utils.check("cursor", nodeMap)).isEmpty()){
            Utils.tryEmptyAppend(n, cursors.get(prop), "%s.setCursor(new QCursor(Qt.CursorShape.%s));\n", sb);
        }
        Utils.tryCheck(n, "tool-tip", "%s.setToolTip(\"%s\");\n", sb, nodeMap);
        tryBoolean(n, "mouse-tracking", "%s.setMouseTracking(%s);\n", sb, nodeMap);
        tryBoolean(n, "visibility", "%s.setHidden(%s);\n", sb, nodeMap);
        tryBoolean(n, "update", "%s.setUpdatesEnabled(%s);\n", sb, nodeMap);
    }

    protected void Grid(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QGridLayout");
        Utils.setName(n, sb);
        if(!(prop = Utils.check("tool-button-style", nodeMap)).isEmpty()){
            switch(prop){
                case "bottom-right": value = "BottomRightCorner"; break;
                case "bottom-left": value = "BottomLeftCorner"; break;
                case "top-right": value = "TopRightCorner"; break;
                case "top-left": value = "TopLeftCorner"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setToolButtonStyle(Qt.ToolButtonStyle.%s);\n", sb);
        }
        tryValue(n, "spacing", "%s.setSpacing(%s);\n", sb, nodeMap);
        tryValue(n, "row-spacing", "%s.setVerticalSpacing(%s);\n", sb, nodeMap);
        tryValue(n, "column-spacing", "%s.setHorizontalSpacing(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QGridLayout", style);
    }

    protected void Item(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap) {
        Style style = new Style(n, "QListWidgetItem");
        Utils.setName(n, sb);
        boolean hasFlags = false;
        if(!(prop = Utils.check("selectable", nodeMap)).isEmpty()) {
            sb.append(String.format("%s.setFlags(Qt.ItemFlag.ItemIsSelectable", n));
            hasFlags = true;
        }
        if(!(prop = Utils.check("editable", nodeMap)).isEmpty()) {
            if(!hasFlags) sb.append(String.format("%s.setFlags(Qt.ItemFlag.ItemIsEditable", n));
            else sb.append(", Qt.ItemFlag.ItemIsEditable");
            hasFlags = true;
        }
        if(!(prop = Utils.check("draggable", nodeMap)).isEmpty()) {
            if(!hasFlags) sb.append(String.format("%s.setFlags(Qt.ItemFlag.ItemIsDragEnabled", n));
            else sb.append(", Qt.ItemFlag.ItemIsEditable");
            hasFlags = true;
        }
        if(!(prop = Utils.check("droppable", nodeMap)).isEmpty()) {
            if(!hasFlags) sb.append(String.format("%s.setFlags(Qt.ItemFlag.ItemIsDropEnabled", n));
            else sb.append(", Qt.ItemFlag.ItemIsDropEnabled");
            hasFlags = true;
        }
        if(!(prop = Utils.check("checkable", nodeMap)).isEmpty()) {
            if(!hasFlags) sb.append(String.format("%s.setFlags(Qt.ItemFlag.ItemIsCheckable", n));
            else sb.append(", Qt.ItemFlag.ItemIsCheckable");
            hasFlags = true;
        }
        if(!(prop = Utils.check("checked", nodeMap)).isEmpty()) {
            if(!hasFlags) sb.append(String.format("%s.setFlags(Qt.ItemFlag.ItemIsEnabled", n));
            else sb.append(", Qt.ItemFlag.ItemIsEnabled");
            hasFlags = true;
        }
        if(!(prop = Utils.check("tri-state", nodeMap)).isEmpty()) {
            if(!hasFlags) sb.append(String.format("%s.setFlags(Qt.ItemFlag.ItemIsTristate", n));
            else sb.append(", Qt.ItemFlag.ItemIsTristate");
            hasFlags = true;
        }
        if(hasFlags) sb.append("\");\n");
        if (!(prop = Utils.check("align", nodeMap)).isEmpty()){
            switch (prop) {
                case "left": value = "1"; break;
                case "right": value = "2"; break;
                case "horizontal-center": value = "4"; break;
                case "justify": value = "8"; break;
                case "absolute": value = "10"; break;
                case "top": value = "20"; break;
                case "bottom": value = "40"; break;
                case "vertical-center": value = "80"; break;
                case "center": value = "84"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setTextAlignment(%s);\n",sb);
        }
        tryBoolean(n, "hidden", "%s.setHidden(%s);\n", sb, nodeMap);
        tryBoolean(n, "selected", "%s.setSelected(%s);\n", sb, nodeMap);
        Utils.tryCheck(n, "status-tip", "%s.setStatusTip(\"%s\");\n", sb, nodeMap);
        Utils.tryCheck(n, "text", "%s.setText(\"%s\");\n", sb, nodeMap);
        Utils.tryCheck(n, "icon", "%s.setIcon(new QIcon(\"%s\"));\n", sb, nodeMap);
        Utils.tryCheck(n, "tool-tip", "%s.setToolTip(\"%s\");\n", sb, nodeMap);
        Utils.tryCheck(n, "what-is-this", "%s.setWhatsThis(\"%s\");\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QGridLayout", style);
    }

    protected void Action(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QAction");
        if(!(prop = Utils.check("menu-role", nodeMap)).isEmpty()){
            switch(prop){
                case "none": value = "NoRole"; break;
                case "text-heuristic": value = "TextHeuristicRole"; break;
                case "application-specific": value = "ApplicationSpecificRole"; break;
                case "about-qt": value = "AboutQtRole"; break;
                case "about": value = "AboutRole"; break;
                case "preferences": value = "PreferencesRole"; break;
                case "quit": value = "QuitRole"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setMenuRole(QAction.MenuRole.%s);\n", sb);
        }
        if(!(prop = Utils.check("priority", nodeMap)).isEmpty()){
            sb.append(String.format("%s.setPriority(QAction.Priority.", n));
            switch(prop){
                case "high": value = "HighPriority"; break;
                case "normal": value = "NormalPriority"; break;
                case "low": value = "LowPriority"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setPriority(QAction.Priority.%s);\n", sb);
        }
        if(!(prop = Utils.check("soft-key-role", nodeMap)).isEmpty()){
            switch(prop){
                case "none": value = "NoSoftKey"; break;
                case "positive": value = "PositiveSoftKey"; break;
                case "negative": value = "NegativeSoftKey"; break;
                case "select": value = "SelectSoftKey"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setSoftKeyRole(QAction.SoftKeyRole.%s);\n", sb);
        }
        if(!(prop = Utils.check("shortcut-context", nodeMap)).isEmpty()){
            switch(prop){
                case "widget": value = "WidgetShortcut"; break;
                case "Widget-children": value = "WidgetWithChildrenShortcut"; break;
                case "Window": value = "WindowShortcut"; break;
                case "Application": value = "ApplicationShortcut"; break;
            }
            Utils.tryEmptyAppend(n, value, "%s.setShortcutContext(Qt.ShortcutContext.%s);\n", sb);
        }
        Utils.tryCheck(n, "shortcut", "%s.setShortcut(new QKeySequence(tr(\"%s\")));\n", sb, nodeMap);
        Utils.tryCheck(n, "status-tip", "%s.setStatusTip(\"%s\");\n", sb, nodeMap);
        Utils.tryCheck(n, "tool-tip", "%s.setToolTip(\"%s\");\n", sb, nodeMap);
        Utils.tryCheck(n, "what-is-this", "%s.setWhatsThis(\"%s\");\n", sb, nodeMap);
        Utils.tryCheck(n, "icon", "%s.setIcon(new QIcon(\"%s\"));\n", sb, nodeMap);
        Utils.tryCheck(n, "icon-Text", "%s.setIconText(\"%s\");\n", sb, nodeMap);
        tryBoolean(n, "repeatable", "%s.setAutoRepeat(%s);\n", sb, nodeMap);
        tryBoolean(n, "checkable", "%s.setCheckable(%s);\n", sb, nodeMap);
        tryBoolean(n, "icon-visible", "%s.setIconVisibleMenu(%s);\n", sb, nodeMap);
        tryBoolean(n, "is-separator", "%s.setSeparator(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QAction", style);
    }

    private void setStyle(Style style, NamedNodeMap nodeMap) {
        for (Map.Entry<String, String> entry : Styles.entrySet())
            if (!(value = Utils.check(entry.getKey(), nodeMap)).isEmpty())
                style.addAttribute(entry.getValue(), value);
    }

    public void TryList(String n, String prop, String command, Map<String, String> map, StringBuilder sb, NamedNodeMap nodeMap){
        String p;
        if (!(p = Utils.check(prop, nodeMap)).isEmpty())  Utils.tryEmptyAppend(n, map.get(p), command, sb);
    }

    public void tryBoolean(String name, String prop, String command, StringBuilder sb, NamedNodeMap nodeMap){
        String p;
        if (Utils.tryBoolean(p = Utils.check(prop, nodeMap))) sb.append(String.format(command, name, p));
    }

    public void tryValue(String name, String prop, String command, StringBuilder sb, NamedNodeMap nodeMap){
        String p;
        if (Utils.tryInteger(p = Utils.check(prop, nodeMap))) sb.append(String.format(command, name, p));
        else if(Utils.tryDouble(p = Utils.check(prop, nodeMap))) sb.append(String.format(command, name, p));
    }

    public void setName(String n, StringBuilder sb){
        if(!Utils.components.containsKey(n.replaceAll("\\d", ""))) sb.append(String.format("%s.setObjectName(\"%s\");\n", n, n));
    }
}
