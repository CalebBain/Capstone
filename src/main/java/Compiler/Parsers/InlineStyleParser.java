package Compiler.Parsers;

import Compiler.Utils;
import org.w3c.dom.NamedNodeMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class InlineStyleParser {
    private String prop;
    private String value;
    private String p;

    private Map<String, String> cursors = new HashMap<String, String>(){{
        put("arrow", "Qt.CursorShape.ArrowCursor");
        put("up-arrow", "Qt.CursorShape.UpArrowCursor");
        put("cross", "Qt.CursorShape.CrossCursor");
        put("wait", "Qt.CursorShape.WaitCursor");
        put("vertical-beam", "Qt.CursorShape.IBeamCursor");
        put("size-vertical", "Qt.CursorShape.SizeVerCursor");
        put("size-horizontal", "Qt.CursorShape.SizeHorCursor");
        put("size-major-diagonal", "Qt.CursorShape.SizeFDragCursor");
        put("size-minor-diagonal", "Qt.CursorShape.SizeBDragCursor");
        put("size-all", "Qt.CursorShape.SizeAllCursor");
        put("blank", "Qt.CursorShape.BlankCursor");
        put("vertical-split", "Qt.CursorShape.SplitVCursor");
        put("horizontal-split", "Qt.CursorShape.SplitHCursor");
        put("hand-pointer", "Qt.CursorShape.PointingHandCursor");
        put("forbidden", "Qt.CursorShape.ForbiddenCursor");
        put("open-hand", "Qt.CursorShape.OpenHandCursor");
        put("closed-hand", "Qt.CursorShape.OpenHandCursor");
        put("question-mark", "Qt.CursorShape.WhatsThisCursor");
        put("busy", "Qt.CursorShape.BusyCursor");
        put("drag-move", "Qt.CursorShape.DragMoveCursor");
        put("drag-copy", "Qt.CursorShape.DragMoveCursor");
        put("drag-link", "Qt.CursorShape.DragLinkCursor");
        put("bitmap", "Qt.CursorShape.BitmapCursor");
    }};

    private Map<String, String> TextFormatTypes = new HashMap<String, String>(){{
        put("none", "0");
        put("type", "1");
        put("table", "2");
        put("cell", "3");
        put("user", "0x1000");
    }};

    private Map<String, String> LayoutDirections = new HashMap<String, String>(){{
        put("left-to-right", "Qt.LayoutDirection.LeftToRight");
        put("right-to-left", "Qt.LayoutDirection.RightToLeft");
        put("auto", "Qt.LayoutDirection.LayoutDirectionAuto");
    }};

    private Map<String, String> Alignments = new HashMap<String, String>(){{
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

    private Map<String, String> ListMovements = new HashMap<String, String>(){{
        put("static", "QListView.Movement.Static");
        put("free", "QListView.Movement.Free");
        put("snap", "QListView.Movement.Snap");
    }};

    private Map<String, String> TabTypes = new HashMap<String, String>(){{
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

    private Map<String, String> ListLayoutModes = new HashMap<String, String>(){{
        put("single-pass", "QListView.LayoutMode.SinglePass");
        put("batched", "QListView.LayoutMode.Batched");
    }};

    private Map<String, String> ListFlows = new HashMap<String, String>(){{
        put("horizontal", "QListView.Flow.LeftToRight");
        put("vertical", "QListView.Flow.TopToBottom");
    }};

    private Map<String, String> ListViewModes = new HashMap<String, String>(){{
        put("list", "QListView.ViewMode.ListMode");
        put("icon", "QListView.ViewMode.IconMode");
    }};

    private Map<String, String> ListResizeModes = new HashMap<String, String>(){{
        put("adjust", "QListView.ResizeMode.Adjust");
        put("fixed", "QListView.ResizeMode.Fixed");
    }};

    private Map<String, String> AbstractItemViewDragDropModes = new HashMap<String, String>(){{
        put("no-drag-drop", "QAbstractItemView.DragDropMode.NoDragDrop");
        put("drop-only", "QAbstractItemView.DragDropMode.DropOnly");
        put("drag-only", "QAbstractItemView.DragDropMode.DragOnly");
        put("drag-drop", "QAbstractItemView.DragDropMode.DragDrop");
        put("internal-move", "QAbstractItemView.DragDropMode.InternalMove");
    }};

    private Map<String, String> AbstractItemViewEditTriggers = new HashMap<String, String>(){{
        put("no-edit-triggers", "QAbstractItemView.EditTrigger.NoEditTriggers");
        put("current-change", "QAbstractItemView.EditTrigger.CurrentChanged");
        put("double-click", "QAbstractItemView.EditTrigger.DoubleClicked");
        put("selected-click", "QAbstractItemView.EditTrigger.SelectedClicked");
        put("edit-key-press", "QAbstractItemView.EditTrigger.EditKeyPressed");
        put("any-key-press", "QAbstractItemView.EditTrigger.AnyKeyPressed");
        put("all-edit-triggers", "QAbstractItemView.EditTrigger.AllEditTriggers");
    }};

    private Map<String, String> AbstractItemViewScrollModes = new HashMap<String, String>(){{
        put("per-item", "QAbstractItemView.ScrollMode.ScrollPerItem");
        put("per-pixel", "QAbstractItemView.ScrollMode.ScrollPerPixel");
    }};

    private Map<String, String> AbstractItemViewSelectionBehaviors = new HashMap<String, String>(){{
        put("items", "QAbstractItemView.SelectionBehavior.SelectItems");
        put("rows", "QAbstractItemView.SelectionBehavior.SelectRows");
        put("columns", "QAbstractItemView.SelectionBehavior.SelectColumns");
    }};

    private Map<String, String> AbstractItemViewTextElideModes = new HashMap<String, String>(){{
        put("left", "Qt.TextElideMode.ElideLeft");
        put("right", "Qt.TextElideMode.ElideRight");
        put("middle", "Qt.TextElideMode.ElideMiddle");
        put("none", "Qt.TextElideMode.ElideNone");
    }};

    private Map<String, String> PenCapStyles = new HashMap<String, String>(){{
        put("square", "Qt.PenCapStyle.SquareCap");
        put("flat", "Qt.PenCapStyle.FlatCap");
        put("round", "Qt.PenCapStyle.RoundCap");
    }};

    private Map<String, String> PenJoinStyles = new HashMap<String, String>(){{
        put("bevel", "Qt.PenJoinStyle.BevelJoin");
        put("miter", "Qt.PenJoinStyle.MiterJoin");
        put("round", "Qt.PenJoinStyle.RoundJoin");
    }};

    private Map<String, String> PenStyles = new HashMap<String, String>(){{
        put("solid", "Qt.PenStyle.SolidLine");
        put("dash", "Qt.PenStyle.DashLine");
        put("dot", "Qt.PenStyle.DotLine");
        put("dash-dot", "Qt.PenStyle.DashDotLine");
        put("dash-double-dot", "Qt.PenStyle.DashDotDotLine");
    }};

    private Map<String, String> GradientTypes = new HashMap<String, String>(){{
        put("conical", "QConicalGradient");
        put("linear", "QLinearGradient");
        put("radial", "QRadialGradient");
    }};

    private Map<String, String> GradientSpreads = new HashMap<String, String>(){{
        put("pad", "QGradient.Spread.PadSpread");
        put("repeat", "QGradient.Spread.RepeatSpread");
        put("reflect", "QGradient.Spread.ReflectSpread");
    }};

    private Map<String, String> GradientCoordinateModes = new HashMap<String, String>(){{
        put("logical", "QGradient.CoordinateMode.LogicalMode");
        put("stretch", "QGradient.CoordinateMode.StretchToDeviceMode");
        put("bounding", "QGradient.CoordinateMode.ObjectBoundingMode");
    }};

    private Map<String, String> Colors = new HashMap<String, String>(){{
        put("white", "#ffffff");
        put("light-gray", "#d3d3d3");
        put("gray", "#808080");
        put("dark-gray", "#a9a9a9");
        put("black", "#000000");
        put("pink", "#ff8b8b");
        put("red", "#ff0000");
        put("dark-red", "#8b0000");
        put("light-orange", "#ffd700");
        put("orange", "#ffa500");
        put("dark-orange", "#ff8b00");
        put("yellow", "#ffff00");
        put("dark-yellow", "#8b8b00");
        put("light-green", "#8bff8b");
        put("green", "#00ff00");
        put("dark-green", "#008b00");
        put("light-cyan", "#8bffff");
        put("cyan", "#00ffff");
        put("dark-cyan", "#008b8b");
        put("light-blue", "#8b8bff");
        put("blue", "#0000ff");
        put("dark-blue", "#00008b");
        put("light-magenta", "#ff8bff");
        put("magenta", "#ff00ff");
        put("purple", "#8b008b");
    }};

    private Map<String, String> Patterns = new HashMap<String, String>(){{
        put("solid", "Qt.BrushStyle.SolidPattern");
        put("extremely-dense", "Qt.BrushStyle.Dense1Pattern");
        put("very-dense", "Qt.BrushStyle.Dense2Pattern");
        put("somewhat-dense", "Qt.BrushStyle.Dense3Pattern");
        put("half-dense", "Qt.BrushStyle.Dense4Pattern");
        put("somewhat-sparse", "Qt.BrushStyle.Dense5Pattern");
        put("very-sparse", "Qt.BrushStyle.Dense6Pattern");
        put("extremely-sparse", "Qt.BrushStyle.Dense7Pattern");
        put("empty", "Qt.BrushStyle.NoBrush");
        put("horizontal-lines", "Qt.BrushStyle.HorPattern");
        put("vertical-lines", "Qt.BrushStyle.VerPattern");
        put("cross-lines", "Qt.BrushStyle.CrossPattern");
        put("backward-diagonal-lines", "Qt.BrushStyle.BDiagPattern");
        put("forward-diagonal-lines", "Qt.BrushStyle.FDiagPattern");
        put("diagonal-cross-lines", "Qt.BrushStyle.DiagCrossPattern");
    }};

    private Map<String, String> ScrollBarPolicies = new HashMap<String, String>(){{
        put("always-on", "Qt.ScrollBarPolicy.ScrollBarAlwaysOn");
        put("as-needed", "Qt.ScrollBarPolicy.ScrollBarAsNeeded");
        put("always-off", "Qt.ScrollBarPolicy.ScrollBarAlwaysOff");
    }};

    private Map<String, String> TextFormats = new HashMap<String, String>(){{
        put("auto", "Qt.TextFormat.AutoText");
        put("plain", "Qt.TextFormat.PlainText");
        put("rich", "Qt.TextFormat.RichText");
        put("log", "Qt.TextFormat.LogText");
    }};

    private Map<String, String> TextInteractionFlags = new HashMap<String, String>(){{
        put("none", "Qt.TextInteractionFlag.NoTextInteraction");
        put("mouse-select", "Qt.TextInteractionFlag.TextSelectableByMouse");
        put("key-select", "Qt.TextInteractionFlag.TextSelectableByKeyboard");
        put("mouse-access", "Qt.TextInteractionFlag.LinksAccessibleByMouse");
        put("key-access", "Qt.TextInteractionFlag.LinksAccessibleByKeyboard");
        put("editable", "Qt.TextInteractionFlag.TextEditable");
        put("editor", "Qt.TextInteractionFlag.TextEditorInteraction");
        put("browser", "Qt.TextInteractionFlag.TextBrowserInteraction");
    }};

    private Map<String, String> Orientations = new HashMap<String, String>(){{
        put("vertical", "Qt.Orientation.Vertical");
        put("horizontal", "Qt.Orientation.Horizontal");
    }};

    private Map<String, String> FrameShadows = new HashMap<String, String>(){{
        put("sunken", "QFrame.Shadow.Sunken");
        put("plain", "QFrame.Shadow.Plain");
        put("always-off", "QFrame.Shadow.Raised");
    }};

    private Map<String, String> FrameShapes = new HashMap<String, String>(){{
        put("no-frame", "QFrame.Shape.NoFrame");
        put("box", "QFrame.Shape.Box");
        put("panel", "QFrame.Shape.Panel");
        put("styled-panel", "QFrame.Shape.StyledPanel");
        put("horizontal-line", "QFrame.Shape.HLine");
        put("vertical-line", "QFrame.Shape.VLine");
        put("window-panel", "QFrame.Shape.WinPanel");
    }};

    private Map<String, String> SliderTickPositions = new HashMap<String, String>(){{
        put("both", "QSlider.TickPosition.TicksBothSides");
        put("above", "QSlider.TickPosition.TicksAbove");
        put("left", "QSlider.TickPosition.TicksAbove");
        put("below", "QSlider.TickPosition.TicksBelow");
        put("right", "QSlider.TickPosition.TicksBelow");
        put("no-ticks", "QSlider.TickPosition.NoTicks");
    }};

    private Map<String, String> CheckStates = new HashMap<String, String>(){{
        put("unchecked", "Qt.CheckState.Unchecked");
        put("partially-checked", "Qt.CheckState.PartiallyChecked");
        put("checked", "Qt.CheckState.Checked");
    }};

    private Map<String, String> DockOptions = new HashMap<String, String>(){{
        put("animated-docks", "DockOption.AnimatedDocks");
        put("allow-nested-docks", "DockOption.AllowNestedDocks");
        put("allow-tabbed-docks", "DockOption.AllowTabbedDocks");
        put("force-tabbed-docks", "DockOption.ForceTabbedDocks");
        put("vertical-tabs", "DockOption.VerticalTabs");
    }};

    private Map<String, String> TabShapes = new HashMap<String, String>(){{
        put("rounded", "QTabWidget.TabShape.Rounded");
        put("triangular", "QTabWidget.TabShape.Triangular");
    }};

    private Map<String, String> ToolButtonStyles = new HashMap<String, String>(){{
        put("icon-only", "Qt.ToolButtonStyle.ToolButtonIconOnly");
        put("text-only", "Qt.ToolButtonStyle.ToolButtonTextOnly");
        put("text-beside-icon", "Qt.ToolButtonStyle.ToolButtonTextBesideIcon");
        put("text-under-icon", "Qt.ToolButtonStyle.ToolButtonTextUnderIcon");
        put("follow-style", "Qt.ToolButtonStyle.ToolButtonFollowStyle");
    }};

    private Map<String, String> EchoModes = new HashMap<String, String>(){{
        put("normal", "QLineEdit.EchoMode.Normal");
        put("none", "QLineEdit.EchoMode.NoEcho");
        put("password", "QLineEdit.EchoMode.Password");
        put("echo-on-edit", "QLineEdit.EchoMode.PasswordEchoOnEdit");
    }};

    private Map<String, String> CursorMoveStyles = new HashMap<String, String>(){{
        put("logical", "Qt.CursorMoveStyle.LogicalMoveStyle");
        put("visual", "Qt.CursorMoveStyle.VisualMoveStyle");
    }};

    private Map<String, String> TextAlignments = new HashMap<String, String>(){{
        put("left", "1");
        put("right", "2");
        put("horizontal-center", "4");
        put("justify", "8");
        put("absolute", "10");
        put("top", "20");
        put("bottom", "40");
        put("vertical-center", "80");
        put("center", "84");
    }};

    private Map<String, String> ActionMenuRoles = new HashMap<String, String>(){{
        put("none", "QAction.MenuRole.NoRole");
        put("text-heuristic", "QAction.MenuRole.ApplicationSpecificRole");
        put("application-specific", "Qt.CheckState.Checked");
        put("about-qt", "QAction.MenuRole.AboutQtRole");
        put("about", "QAction.MenuRole.AboutRole");
        put("preferences", "QAction.MenuRole.PreferencesRole");
        put("quit", "QAction.MenuRole.QuitRole");
    }};

    private Map<String, String> ActionPriorities = new HashMap<String, String>(){{
        put("high", "QAction.Priority.HighPriority");
        put("normal", "QAction.Priority.NormalPriority");
        put("low", "QAction.Priority.LowPriority");
    }};

    private Map<String, String> ActionSoftKeyRoles = new HashMap<String, String>(){{
        put("none", "QAction.SoftKeyRole.NoSoftKey");
        put("positive", "QAction.SoftKeyRole.PositiveSoftKey");
        put("negative", "QAction.SoftKeyRole.NegativeSoftKey");
        put("select", "QAction.SoftKeyRole.SelectSoftKey");
    }};

    private Map<String, String> ShortcutContexts = new HashMap<String, String>(){{
        put("widget", "Qt.ShortcutContext.WidgetShortcut");
        put("widget-children", "Qt.ShortcutContext.WidgetWithChildrenShortcut");
        put("window", "Qt.ShortcutContext.WindowShortcut");
        put("application", "Qt.ShortcutContext.ApplicationShortcut");
    }};

    private Map<String, String> TextFormatPageBreakFlags = new HashMap<String, String>(){{
        put("auto", "QTextFormat.PageBreakFlag.PageBreak_Auto");
        put("always-before", "QTextFormat.PageBreakFlag.PageBreak_AlwaysBefore");
        put("always-after", "QTextFormat.PageBreakFlag.PageBreak_AlwaysAfter");
    }};

    private Map<String, String> TextListFormatStyles = new HashMap<String, String>(){{
        put("disc", "QTextListFormat.Style.ListDisc");
        put("circle", "QTextListFormat.Style.ListCircle");
        put("square", "QTextListFormat.Style.ListSquare");
        put("decimal", "QTextListFormat.Style.ListDecimal");
        put("lower-alpha", "QTextListFormat.Style.ListLowerAlpha");
        put("upper-alpha", "QTextListFormat.Style.ListUpperAlpha");
        put("lower-roman", "QTextListFormat.Style.ListLowerRoman");
        put("upper-roman", "QTextListFormat.Style.ListUpperRoman");
    }};

    private Map<String, String> TextCharFormatVerticalAlignments = new HashMap<String, String>(){{
        put("super", "QTextCharFormat.VerticalAlignment.AlignSuperScript");
        put("normal", "QTextCharFormat.VerticalAlignment.AlignNormal");
        put("sub", "QTextCharFormat.VerticalAlignment.AlignSubScript");
        put("top", "QTextCharFormat.VerticalAlignment.AlignTop");
        put("middle", "QTextCharFormat.VerticalAlignment.AlignMiddle");
        put("bottom", "QTextCharFormat.VerticalAlignment.AlignBottom");
        put("baseline", "QTextCharFormat.VerticalAlignment.AlignBaseline");
    }};

    private Map<String, String> TextCharFormatUnderlineStyles = new HashMap<String, String>(){{
        put("none", "QTextCharFormat.UnderlineStyle.NoUnderline");
        put("single", "QTextCharFormat.UnderlineStyle.SingleUnderline");
        put("dashed", "QTextCharFormat.UnderlineStyle.DashUnderline");
        put("dotted", "QTextCharFormat.UnderlineStyle.DotLine");
        put("dot-dashed", "QTextCharFormat.UnderlineStyle.DashDotLine");
        put("double-dot-dashed", "QTextCharFormat.UnderlineStyle.DashDotDotLine");
        put("wavy", "QTextCharFormat.UnderlineStyle.WaveUnderline");
        put("spell-check", "QTextCharFormat.UnderlineStyle.SpellCheckUnderline");
    }};

    private Map<String, String> FontHintingPreferences = new HashMap<String, String>(){{
        put("Default", "QFont.HintingPreference.PreferDefaultHinting");
        put("none", "QFont.HintingPreference.PreferNoHinting");
        put("vertical", "QFont.HintingPreference.PreferVerticalHinting");
        put("full", "QFont.HintingPreference.PreferFullHinting");
    }};

    private Map<String, String> FontStyleHints = new HashMap<String, String>(){{
        put("any", "QFont.StyleHint.AnyStyle");
        put("sans-serif", "QFont.StyleHint.SansSerif");
        put("helvetica", "QFont.StyleHint.Helvetica");
        put("serif", "QFont.StyleHint.Serif");
        put("times", "QFont.StyleHint.Times");
        put("type-writer", "QFont.StyleHint.TypeWriter");
        put("courier", "QFont.StyleHint.Courier");
        put("old-english", "QFont.StyleHint.OldEnglish");
        put("decorative", "QFont.StyleHint.Decorative");
        put("monospace", "QFont.StyleHint.Monospace");
        put("fantasy", "QFont.StyleHint.Fantasy");
        put("cursive", "QFont.StyleHint.Cursive");
        put("system", "QFont.StyleHint.System");
    }};

    private Map<String, String> TextFrameFormatBorderStyles = new HashMap<String, String>(){{
        put("none", "QTextFrameFormat.BorderStyle.BorderStyle_None");
        put("dotted", "QTextFrameFormat.BorderStyle.BorderStyle_Dotted");
        put("dashed", "QTextFrameFormat.BorderStyle.BorderStyle_Dashed");
        put("solid", "QTextFrameFormat.BorderStyle.BorderStyle_Solid");
        put("double", "QTextFrameFormat.BorderStyle.BorderStyle_Double");
        put("dot-dash", "QTextFrameFormat.BorderStyle.BorderStyle_DotDash");
        put("double-dot-dash", "QTextFrameFormat.BorderStyle.BorderStyle_DotDotDash");
        put("groove", "QTextFrameFormat.BorderStyle.BorderStyle_Groove");
        put("ridge", "QTextFrameFormat.BorderStyle.BorderStyle_Ridge");
        put("inset", "QTextFrameFormat.BorderStyle.BorderStyle_Inset");
        put("outset", "QTextFrameFormat.BorderStyle.BorderStyle_Outset");
    }};

    private Map<String, String> FontStyleStrategies = new HashMap<String, String>(){{
        put("prefer-default", "QFont.StyleStrategy.PreferDefault");
        put("prefer-bitmap", "QFont.StyleStrategy.PreferBitmap");
        put("prefer-device", "QFont.StyleStrategy.PreferDevice");
        put("prefer-outline", "QFont.StyleStrategy.PreferOutline");
        put("force-outline", "QFont.StyleStrategy.ForceOutline");
        put("no-antialias", "QFont.StyleStrategy.NoAntialias");
        put("prefer-antialias", "QFont.StyleStrategy.PreferAntialias");
        put("opengl-compatible", "QFont.StyleStrategy.OpenGLCompatible");
        put("no-font-merging", "QFont.StyleStrategy.NoFontMerging");
        put("prefer-match", "QFont.StyleStrategy.PreferMatch");
        put("prefer-quality", "QFont.StyleStrategy.PreferQuality");
        put("force-integer-metrics", "QFont.StyleStrategy.ForceIntegerMetrics");
    }};

    private Map<String, String> Properties = new HashMap<String, String>(){{
        put("object-index", "0x0:1");
        put("css-float", "0x800:1");
        put("layout-direction", "0x801:3");
        put("background-image", "0x823:2");
        put("block-align", "0x1010:4");
        put("block-top-margin", "0x1030:1");
        put("block-bottom-margin", "0x1031:1");
        put("block-left-margin", "0x1032:1");
        put("block-right-margin", "0x1033:1");
        put("text-indent", "0x1034:1");
        put("block-indent", "0x1040:1");
        put("line-height", "0x1048:1");
        put("horizontal-ruler-width", "0x1060:1");
        put("font-family", "0x2000:2");
        put("font-point-size", "0x2001:1");
        put("font-size-adjust", "0x2002:1");
        put("font-weight", "0x2003:1");
        put("italic", "0x2004:1");
        put("overline", "0x2006:1");
        put("strike-out", "0x2007:1");
        put("font-fixed-pitch", "0x2008:1");
        put("font-pixel-size", "0x2009:1");
        put("letter-spacing", "0x1FE1:1");
        put("word-spacing", "0x1FE2:1");
        put("style-hint", "0x1FE3:5");
        put("style-strategy", "0x1FE4:6");
        put("kerning", "0x1FE5:1");
        put("hinting-preference", "0x1FE6:7");
        put("underline-color", "0x2010:8");
        put("text-vertical-align", "0x2021:9");
        put("underline-style", "0x2023:a");
        put("tool-tip", "0x2024:2");
        put("is-anchor", "0x2030:1");
        put("anchor-href", "0x2031:2");
        put("anchor-name", "0x2032:2");
        put("object-type", "0x2f00:e");
        put("list-style", "0x3000:b");
        put("list-indent", "0x3001:1");
        put("list-number-prefix", "0x3002:1");
        put("list-number-suffix", "0x3003:1");
        put("frame-border", "0x4000:1");
        put("frame-margin", "0x4001:1");
        put("frame-padding", "0x4002:1");
        put("frame-width", "0x4003:1");
        put("frame-height", "0x4004:1");
        put("frame-top-margin", "0x4005:1");
        put("frame-bottom-margin", "0x4006:1");
        put("frame-left-margin", "0x4007:1");
        put("frame-right-margin", "0x4008:1");
        put("frame-border-style", "0x4010:d");
        put("table-columns", "0x4100:1");
        put("table-column-width", "04101:1");
        put("table-cell-spacing", "0x4102:1");
        put("table-cell-padding", "0x4103:1");
        put("table-header-row-count", "0x4104:1");
        put("table-cell-row-span", "0x4810:1");
        put("table-cell-column-span", "0x4811:1");
        put("table-cell-top-padding", "0x4812:1");
        put("table-cell-bottom-padding", "0x4813:1");
        put("table-cell-left-padding", "0x4814:1");
        put("table-cell-right-padding", "0x4815:1");
        put("image-name", "0z5000:2");
        put("image-width", "0x5010:1");
        put("image-height", "0x5011:1");
        put("page-break-policy", "0x7000:c");
    }};

    protected void List(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QListWidget");
        setName(n, sb);
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
        tryList(n, "movement", "%s.setMovement(%s);\n", ListMovements, sb, nodeMap);
        tryList(n, "layout-mode", "%s.setLayoutMode(%s);\n", ListLayoutModes, sb, nodeMap);
        tryList(n, "orientation", "%s.setFlow(%s);\n", ListFlows, sb, nodeMap);
        tryList(n, "view-mode", "%s.setViewMode(%s);\n", ListViewModes, sb, nodeMap);
        tryList(n, "resize-mode", "%s.setResizeMode(%s);\n", ListResizeModes, sb, nodeMap);
        tryBoolean(n, "selection-rectangle-visible", "%s.setSelectionRectVisible(%s);\n", sb, nodeMap);
        tryBoolean(n, "uniform-sizes", "%s.setUniformItemSizes(%s);\n", sb, nodeMap);
        tryBoolean(n, "word-wrap", "%s.setWordWrap(%s);\n", sb, nodeMap);
        tryBoolean(n, "wrapping", "%s.setWrapping(%s);\n", sb, nodeMap);
        tryValue(n, "batch-size", "%s.setBatchSize(%s);\n", sb, nodeMap);
        tryValue(n, "model-column", "%s.setModelColumn(%s);\n", sb, nodeMap);
        tryValue(n, "spacing", "%s.setSpacing(%s);\n", sb, nodeMap);
        AbstractItemView(n, sb, nodeMap);
    }

    protected void AbstractItemView(String n, StringBuilder sb, NamedNodeMap nodeMap){
        tryList(n, "drag-drop-mode", "%s.setDragDropMode(%s);\n", AbstractItemViewDragDropModes, sb, nodeMap);
        tryList(n, "edit-trigger", "%s.setEditTriggers(%s);\n", AbstractItemViewEditTriggers, sb, nodeMap);
        tryList(n, "horizontal-scroll-mode", "%s.setHorizontalScrollMode(%s);\n", AbstractItemViewScrollModes, sb, nodeMap);
        tryList(n, "selection-behavior", "%s.setSelectionBehavior(%s);\n", AbstractItemViewSelectionBehaviors, sb, nodeMap);
        tryList(n, "text-elide-mode", "%s.setTextElideMode(%s);\n", AbstractItemViewTextElideModes, sb, nodeMap);
        tryList(n, "vertical-scroll-mode", "%s.setVerticalScrollMode(%s);\n", AbstractItemViewScrollModes, sb, nodeMap);
        tryBoolean(n, "alt-row-color", "%s.setAlternatingRowColors(%s);\n", sb, nodeMap);
        tryBoolean(n, "draggable", "%s.setDragEnabled(%s);\n", sb, nodeMap);
        tryBoolean(n, "auto-scroll", "%s.setAutoScroll(%s);\n", sb, nodeMap);
        tryBoolean(n, "drop-indicator", "%s.setDropIndicatorShown(%s);\n", sb, nodeMap);
        tryBoolean(n, "tab-key-navigation", "%s.TabKeyNavigation(%s);\n", sb, nodeMap);
        tryValue(n, "auto-scroll-margin", "%s.setAutoScrollMargin(%s);\n", sb, nodeMap);
        AbstractScrollArea(n, sb, nodeMap);
    }
    // need todo
    protected void TextEdit(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QListWidget");
        setName(n, sb);

        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QListView", style);
        AbstractScrollArea(n, sb, nodeMap);
    }

    private void TextCharFormat(String n, StringBuilder sb, NamedNodeMap nodeMap){
        setName(n, sb);
        tryBoolean(n, "anchor", "%s.setAnchor(%s);\n", sb, nodeMap);
    }
    ///end of todo

    private void PropertiesParser(String n, StringBuilder sb, NamedNodeMap nodeMap){
        if (!(prop = Utils.check("properties", nodeMap)).isEmpty()) {
            String[] props = prop.split(" ");
            for(String p : props){
                String[] parts = p.split(":");
                String[] key = Properties.get(parts[0]).split(":");
                switch (key[1]) {
                    case "1": value = parts[1]; break;
                    case "2": value = String.format("\"%s\"", parts[1]); break;
                    case "3": value = LayoutDirections.get(parts[1]); break;
                    case "4": value = Alignments.get(parts[1]); break;
                    case "5": value = FontStyleHints.get(parts[1]); break;
                    case "6": value = FontStyleStrategies.get(parts[1]); break;
                    case "7": value = FontHintingPreferences.get(parts[1]); break;
                    case "8": value = (Colors.containsKey(parts[1])) ? Colors.get(parts[1]) : String.format("\"%s\"", parts[1]); break;
                    case "9": value = TextCharFormatVerticalAlignments.get(parts[1]); break;
                    case "a": value = TextCharFormatUnderlineStyles.get(parts[1]); break;
                    case "b": value = TextListFormatStyles.get(parts[1]); break;
                    case "c": value = TextFormatPageBreakFlags.get(parts[1]); break;
                    case "d": value = TextFrameFormatBorderStyles.get(parts[1]); break;
                    case "e": value = TextFormatTypes.get(parts[1]); break;
                }
                Utils.tryEmptyAppend(n, value, "%s.setProperty(" + key +", %s);\n", sb);
            }
        }
    }

    private void TextFormat(String n, StringBuilder sb, NamedNodeMap nodeMap){
        PropertiesParser(n, sb, nodeMap);
        tryList(n, "direction", "%s.setLayoutDirection(%s);\n", LayoutDirections, sb, nodeMap);
        tryList(n, "type", "%s.setObjectType(%s);\n", TextFormatTypes, sb, nodeMap);
        tryValue(n, "index", "%s.setObjectIndex(%s);\n", sb, nodeMap);
    }

    protected void Tab(String n, StringBuilder sb, NamedNodeMap nodeMap){
        setName(n, sb);
        tryList(n, "type", "%s.setType(%s);\n", TabTypes, sb, nodeMap);
        tryCheck(n, "delimiter", "%s.setText('%s');\n", sb, nodeMap);
        tryValue(n, "position", "%s.setPosition(%s);\n", sb, nodeMap);
    }

    private void Pen(String n, StringBuilder sb, NamedNodeMap nodeMap){
        setName(n, sb);
        tryList(n, "cap-style", "%s.setCapStyle(%s);\n", PenCapStyles, sb, nodeMap);
        tryList(n, "joint", "%s.setJoinStyle(%s);\n", PenJoinStyles, sb, nodeMap);
        tryList(n, "style", "%s.setStyle(%s);\n", PenStyles, sb, nodeMap);
        tryBoolean(n, "pen-cosmetic", "%s.setCosmetic(%s);\n", sb, nodeMap);
        tryValue(n, "pen-dash-offset", "%s.setDashOffset(%s);\n", sb, nodeMap);
        tryValue(n, "pen-miter-limit", "%s.setMiterLimit(%s);\n", sb, nodeMap);
        tryValue(n, "pen-width", "%s.setWidth(%s);\n", sb, nodeMap);
    }

    private void Brush(String n, StringBuilder sb, NamedNodeMap nodeMap) {
        boolean brush = false;
        String name;
        if(!(prop = Utils.check("gradient", nodeMap)).isEmpty()) {
            setName(n, sb);
            name = trySetName("Gradient");
            String[] parts = prop.split(" ");
            sb.append(String.format("QGradient %s = new %s(%s);\n", name, GradientTypes.get(parts[0]), parts[1]));
            sb.append(String.format("%s.setSpread(%s);\n", name, GradientSpreads.get(parts[2])));
            sb.append(String.format("%s.setCoordinateMode(%s);\n", name, GradientCoordinateModes.get(parts[3])));
            sb.append(String.format("QBrush %s = new QBrush(%s);\n", n, name));
        } else {
            setName(n, sb);
            if (!(prop = Utils.check("color", nodeMap)).isEmpty()) {
                sb.append(String.format("QBrush %s = new QBrush(", n));
                brush = true;
                if(Colors.containsKey(prop)) sb.append(Colors.get(prop));
                else sb.append(prop);
            }
            if (!(prop = Utils.check("pattern", nodeMap)).isEmpty()) {
                if (!brush) {
                    sb.append(String.format("QBrush %s = new QBrush(", n));
                } else sb.append(", ");
                brush = true;
                sb.append(Patterns.get(prop));
            }
            if(brush) sb.append(")");
        }
    }

    private void AbstractScrollArea(String n, StringBuilder sb, NamedNodeMap nodeMap){
        tryList(n, "horizontal-scroll-bar-policy", "%s.setHorizontalScrollBarPolicy(%s);\n", ScrollBarPolicies, sb, nodeMap);
        tryList(n, "vertical-scroll-bar-policy", "%s.setVerticalScrollBarPolicy(%s);\n", ScrollBarPolicies, sb, nodeMap);
        Frame(n, sb, nodeMap);
    }

    protected void LCDNumber(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QLCDNumber");
        setName(n, sb);
        tryCapitalize(n, "segment-style", "%s.setSegmentStyle(QLCDNumber.SegmentStyle.%s);\n", sb, nodeMap);
        tryCapitalize(n, "mode", "%s.setMode(QLCDNumber.Mode.%s);\n", sb, nodeMap);
        tryBoolean(n, "small-decimal-point", "%s.setSmallDecimalPoint(%s);\n", sb, nodeMap);
        tryValue(n, "digit-count", "%s.setDigitCount(%s);\n", sb, nodeMap);
        tryValue(n, "value", "%s.display(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QLCDNumber", style);
        Frame(n, sb, nodeMap);
    }

    protected void Label(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QLabel");
        setName(n, sb);
        tryList(n, "align", "%s.setAlignment(%s);\n", Alignments, sb, nodeMap);
        tryList(n, "text-format", "%s.setTextFormat(%s);\n", TextFormats, sb, nodeMap);
        tryList(n, "text-interaction", "%s.setTextInteractionFlags(%s);\n", TextInteractionFlags, sb, nodeMap);
        tryCheck(n, "text", "%s.setText(tr(\"%s\"));\n", sb, nodeMap);
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
        setName(n, sb);
        tryList(n, "orientation", "%s.setOrientation(%s);\n", Orientations, sb, nodeMap);
        tryBoolean(n, "resizeable", "%s.setOpaqueResize(%s);\n", sb, nodeMap);
        tryValue(n, "handle-width", "%s.setHandleWidth(%s);\n", sb, nodeMap);
        tryValue(n, "rubber-band", "%s.setRubberBand(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QLCDNumber", style);
        Frame(n, sb, nodeMap);
    }

    protected void Frame(String n, StringBuilder sb, NamedNodeMap nodeMap){
        tryList(n, "shadow", "%s.setFrameShadow(%s);\n", FrameShadows, sb, nodeMap);
        tryList(n, "shape", "%s.setFrameShape(%s);\n", FrameShapes, sb, nodeMap);
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
        setName(n, sb);
        tryList(n, "tick-position", "%s.setTickPosition(%s);\n", SliderTickPositions, sb, nodeMap);
        tryValue(n, "interval", "%s.setTickInterval(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QSlider", style);
        AbstractSlider(n, sb, nodeMap);
    }

    private void AbstractSlider(String n, StringBuilder sb, NamedNodeMap nodeMap){
        String[] range = Utils.check("range", nodeMap).split(" ");
        if (range.length > 1) sb.append(String.format("%s.setRange(%s, %s);\n", n, range[0], range[1]));
        tryList(n, "orientation", "%s.setOrientation(%s);\n", Orientations, sb, nodeMap);
        tryList(n, "invert-numbers", "%s.setInvertedControls(%s);\n", Orientations, sb, nodeMap);
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
        setName(n, sb);
        tryCheck(n, "shortcut", "%s.setShortcut(new QKeySequence(tr(\"%s\"));\n", sb, nodeMap);
        tryBoolean(n, "default", "%s.setDefault(%s);\n", sb, nodeMap);
        tryBoolean(n, "flat", "%s.setFlat(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QPushButton", style);
        AbstractButton(n, sb, nodeMap);
    }

    protected void RadioButton(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QRadioButton");
        setName(n, sb);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QRadioButton", style);
        AbstractButton(n, sb, nodeMap);
    }

    protected void CheckBox(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QCheckBox");
        setName(n, sb);
        tryList(n, "check-state", "%s.setCheckState(%s);\n", CheckStates, sb, nodeMap);
        tryBoolean(n, "checkable", "%s.setTristate(%s);\n", sb, nodeMap);
        tryBoolean(n, "default", "%s.setDefaultUp(%s);\n", sb, nodeMap);
        tryBoolean(n, "native-menubar", "%s.setNativeMenuBar(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QCheckBox", style);
        AbstractButton(n, sb, nodeMap);
    }

    private void AbstractButton(String n, StringBuilder sb, NamedNodeMap nodeMap){
        tryCheck(n, "text", "%s.setText(\"%s\");\n", sb, nodeMap);
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
        tryList(n, "dock-option", "%s.setDockOptions(%s);\n", DockOptions, sb, nodeMap);
        tryList(n, "tab-shape", "%s.setTabShape(%s);\n", TabShapes, sb, nodeMap);
        tryList(n, "tool-button-style", "%s.setToolButtonStyle(%s);\n", ToolButtonStyles, sb, nodeMap);
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
        setName(n, sb);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QMenuBar", style);
        Widget(n, sb, nodeMap);
    }

    protected void Menu(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QMenu");
        setName(n, sb);
        tryCheck(n, "icon", "%s.setIcon(\"%s\");\n", sb, nodeMap);
        tryCheck(n, "title", "%s.setTitle(\"%s\");\n", "menu", sb, nodeMap);
        tryBoolean(n, "tear-off", "%s.setTearOffEnabled(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QMenu", style);
        Widget(n, sb, nodeMap);
    }

    protected void Section(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QWidget");
        setName(n, sb);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QGridLayout", style);
        Widget(n, sb, nodeMap);
    }

    protected void LineEdit(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap) {
        Style style = new Style(n, "QLineEdit");
        setName(n, sb);
        tryList(n, "align", "%s.setAlignment(%s);\n", Alignments, sb, nodeMap);
        tryList(n, "echo-mode", "%s.setEchoMode(%s);\n", EchoModes, sb, nodeMap);
        tryList(n, "cursor-move-style", "%s.setCursorMoveStyle(%s);\n", CursorMoveStyles, sb, nodeMap);
        tryCheck(n, "placeholder", "%s.setPlaceholderText(\"%s\");\n", sb, nodeMap);
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
        setName(n, sb);
        tryList(n, "align", "%s.setTextAlignment(%s);\n", TextAlignments, sb, nodeMap);
        tryCheck(n, "title", "%s.setTitle(\"%s\");\n", sb, nodeMap);
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
            Utils.tryEmptyAppend(n, String.format("%s, %s, %s, %s", v1, v2, v3, v4), "%s.setContentsMargins(%s));\n", sb);
        }
        tryList(n, "cursor", "%s.setCursor(new QCursor(%s));\n", cursors, sb, nodeMap);
        tryCheck(n, "tool-tip", "%s.setToolTip(\"%s\");\n", sb, nodeMap);
        tryBoolean(n, "mouse-tracking", "%s.setMouseTracking(%s);\n", sb, nodeMap);
        tryBoolean(n, "visibility", "%s.setHidden(%s);\n", sb, nodeMap);
        tryBoolean(n, "update", "%s.setUpdatesEnabled(%s);\n", sb, nodeMap);
    }

    protected void Grid(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QGridLayout");
        setName(n, sb);
        tryList(n, "tool-button-style", "%s.setToolButtonStyle(%s);\n", ToolButtonStyles, sb, nodeMap);
        tryValue(n, "spacing", "%s.setSpacing(%s);\n", sb, nodeMap);
        tryValue(n, "row-spacing", "%s.setVerticalSpacing(%s);\n", sb, nodeMap);
        tryValue(n, "column-spacing", "%s.setHorizontalSpacing(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QGridLayout", style);
    }

    private boolean setFlag(boolean hasFlags, String n, String prop, String Flag, StringBuilder sb, NamedNodeMap nodeMap){
        if(!Utils.check(prop, nodeMap).isEmpty()) {
            if(!hasFlags) sb.append(String.format("%s.setFlags(Qt.ItemFlag.%s", n, Flag));
            else sb.append(String.format(", Qt.ItemFlag.%s", Flag));
            hasFlags = true;
        }
        return hasFlags;
    }

    protected void Item(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap) {
        Style style = new Style(n, "QListWidgetItem");
        setName(n, sb);
        boolean hasFlags = setFlag(false, n, "selectable", "ItemIsSelectable", sb, nodeMap);
        hasFlags = setFlag(hasFlags, n, "editable", "ItemIsEditable", sb, nodeMap);
        hasFlags = setFlag(hasFlags, n, "editable", "ItemIsEditable", sb, nodeMap);
        hasFlags = setFlag(hasFlags, n, "draggable", "ItemIsDragEnabled", sb, nodeMap);
        hasFlags = setFlag(hasFlags, n, "droppable", "ItemIsDropEnabled", sb, nodeMap);
        hasFlags = setFlag(hasFlags, n, "checkable", "ItemIsCheckable", sb, nodeMap);
        hasFlags = setFlag(hasFlags, n, "checked", "ItemIsEnabled", sb, nodeMap);
        hasFlags = setFlag(hasFlags, n, "tri-state", "ItemIsTristate", sb, nodeMap);
        if(hasFlags) sb.append(");\n");
        tryList(n, "align", "%s.setTextAlignment(%s);\n", TextAlignments, sb, nodeMap);
        tryCheck(n, "status-tip", "%s.setStatusTip(\"%s\");\n", sb, nodeMap);
        tryCheck(n, "text", "%s.setText(\"%s\");\n", sb, nodeMap);
        tryCheck(n, "icon", "%s.setIcon(new QIcon(\"%s\"));\n", sb, nodeMap);
        tryCheck(n, "tool-tip", "%s.setToolTip(\"%s\");\n", sb, nodeMap);
        tryCheck(n, "what-is-this", "%s.setWhatsThis(\"%s\");\n", sb, nodeMap);
        tryBoolean(n, "hidden", "%s.setHidden(%s);\n", sb, nodeMap);
        tryBoolean(n, "selected", "%s.setSelected(%s);\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QGridLayout", style);
    }

    protected void Action(String n, Map<String, Style> stylesSheet, StringBuilder sb, NamedNodeMap nodeMap){
        Style style = new Style(n, "QAction");
        tryList(n, "menu-role", "%s.setMenuRole(%s);\n", ActionMenuRoles, sb, nodeMap);
        tryList(n, "priority", "%s.setPriority(%s);\n", ActionPriorities, sb, nodeMap);
        tryList(n, "soft-key-role", "%s.setSoftKeyRole(%s);\n", ActionSoftKeyRoles, sb, nodeMap);
        tryList(n, "shortcut-context", "%s.setShortcutContext(%s);\n", ShortcutContexts, sb, nodeMap);
        tryBoolean(n, "repeatable", "%s.setAutoRepeat(%s);\n", sb, nodeMap);
        tryBoolean(n, "checkable", "%s.setCheckable(%s);\n", sb, nodeMap);
        tryBoolean(n, "icon-visible", "%s.setIconVisibleMenu(%s);\n", sb, nodeMap);
        tryBoolean(n, "is-separator", "%s.setSeparator(%s);\n", sb, nodeMap);
        tryCheck(n, "shortcut", "%s.setShortcut(new QKeySequence(tr(\"%s\")));\n", sb, nodeMap);
        tryCheck(n, "status-tip", "%s.setStatusTip(\"%s\");\n", sb, nodeMap);
        tryCheck(n, "tool-tip", "%s.setToolTip(\"%s\");\n", sb, nodeMap);
        tryCheck(n, "what-is-this", "%s.setWhatsThis(\"%s\");\n", sb, nodeMap);
        tryCheck(n, "icon", "%s.setIcon(new QIcon(\"%s\"));\n", sb, nodeMap);
        tryCheck(n, "icon-Text", "%s.setIconText(\"%s\");\n", sb, nodeMap);
        setStyle(style, nodeMap);
        if(!style.isEmpty()) stylesSheet.put((!n.isEmpty()) ? n : "QAction", style);
    }

    private void setStyle(Style style, NamedNodeMap nodeMap) {
        for (Map.Entry<String, String> entry : Styles.entrySet())
            if (!(value = Utils.check(entry.getKey(), nodeMap)).isEmpty()) style.addAttribute(entry.getValue(), value);
    }

    private void tryList(String n, String prop, String command, Map<String, String> map, StringBuilder sb, NamedNodeMap nodeMap){
        if (!(p = Utils.check(prop, nodeMap)).isEmpty())  Utils.tryEmptyAppend(n, map.get(p), command, sb);
    }

    private void tryBoolean(String name, String prop, String command, StringBuilder sb, NamedNodeMap nodeMap){
        if (Utils.tryBoolean(p = Utils.check(prop, nodeMap))) sb.append(String.format(command, name, p));
    }

    private void tryValue(String name, String prop, String command, StringBuilder sb, NamedNodeMap nodeMap){
        if (Utils.tryInteger(p = Utils.check(prop, nodeMap))) sb.append(String.format(command, name, p));
        else if(Utils.tryDouble(p = Utils.check(prop, nodeMap))) sb.append(String.format(command, name, p));
    }

    private void setName(String n, StringBuilder sb){
        if(!Utils.components.containsKey(n.replaceAll("\\d", ""))) sb.append(String.format("%s.setObjectName(\"%s\");\n", n, n));
    }

    private void tryCheck(String name, String prop, String command, StringBuilder sb, NamedNodeMap nodeMap){
        if (!(p = Utils.check(prop, nodeMap)).isEmpty()) sb.append(String.format(command, name, p));
    }

    private void tryCheck(String name, String prop, String command, String replacement, StringBuilder sb, NamedNodeMap nodeMap){
        if (!(p = Utils.check(prop, nodeMap)).isEmpty()) sb.append(String.format(command, name, p));
        else sb.append(String.format(command, name, replacement));
    }

    private void tryCapitalize(String name, String prop, String command, StringBuilder sb, NamedNodeMap nodeMap){
        String p;
        if(!(p = Utils.check(prop, nodeMap)).isEmpty()) sb.append(String.format(command, name, Utils.capitalize(p)));
    }

    private String trySetName(String prop){
        List<String> comps = ComponentParser.getComponents();
        if(!comps.contains(prop)) comps.add(prop);
        else{
            int count = 0;
            for(String comp : comps) if(comp.startsWith(prop)) count++;
            prop += count;
            comps.add(prop);
        }
        return prop;
    }
}