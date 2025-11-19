// generated with ast extension for cup
// version 0.8
// 26/8/2025 20:43:23


package rs.ac.bg.etf.pp1.ast;

public class MethodVarList_more extends MethodVarList {

    private MethodVarList MethodVarList;
    private VarList VarList;

    public MethodVarList_more (MethodVarList MethodVarList, VarList VarList) {
        this.MethodVarList=MethodVarList;
        if(MethodVarList!=null) MethodVarList.setParent(this);
        this.VarList=VarList;
        if(VarList!=null) VarList.setParent(this);
    }

    public MethodVarList getMethodVarList() {
        return MethodVarList;
    }

    public void setMethodVarList(MethodVarList MethodVarList) {
        this.MethodVarList=MethodVarList;
    }

    public VarList getVarList() {
        return VarList;
    }

    public void setVarList(VarList VarList) {
        this.VarList=VarList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodVarList!=null) MethodVarList.accept(visitor);
        if(VarList!=null) VarList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodVarList!=null) MethodVarList.traverseTopDown(visitor);
        if(VarList!=null) VarList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodVarList!=null) MethodVarList.traverseBottomUp(visitor);
        if(VarList!=null) VarList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodVarList_more(\n");

        if(MethodVarList!=null)
            buffer.append(MethodVarList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarList!=null)
            buffer.append(VarList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodVarList_more]");
        return buffer.toString();
    }
}
