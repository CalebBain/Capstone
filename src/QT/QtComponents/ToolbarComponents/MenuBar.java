package QT.QtComponents.ToolbarComponents;

public class MenuBar{

    /*public MenuBar(Node node) {
        this.nodeMap = node.getAttributes();
        setIdentity(nodeMap);
    }

    private void setProps() {
        Utils.setWidgetProps(this, nodeMap);
        onFunction();
    }

    private void onFunction() {
        String[] callParts;
        if ((callParts = Utils.Func("on-hover", nodeMap)).length == 1) this.hovered.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) this.hovered.connect(QT.findComponent(callParts[0]), callParts[1]);
        if ((callParts = Utils.Func("on-trigger", nodeMap)).length == 1) this.triggered.connect(QApplication.instance(), callParts[0]);
        else if (callParts.length == 2) this.triggered.connect(QT.findComponent(callParts[0]), callParts[1]);
        Utils.onWidgetFunctions(this, nodeMap);
    }

    public String setStyle() {
        Utils.getStyleSheets("QMenuBar", styles, Name, Class, nodeMap);
        setProps();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Style> style : styles.entrySet()) sb.append(style.getValue().toString());
        return sb.toString();
    }

    public String Name() {
        return Name;
    }

    public String Class() {
        return Class;
    }

    public String Component() {
        return "menu-bar";
    }

    public QObject Widgit() {
        return this;
    }

    public void SetStylesheet(String sheet) {
        this.setStyleSheet(sheet);
    }

    public void addChild(Component child, Node node) {
        if(child instanceof QAction) this.addAction((QAction) child);
        if(child instanceof QMenu) this.addMenu((QMenu) child);
        if(child instanceof QWidget) this.setCornerWidget((QWidget) child);
        if(child instanceof Separator) this.addSeparator();
    }*/
}
