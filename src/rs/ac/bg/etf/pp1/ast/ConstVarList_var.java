// generated with ast extension for cup
// version 0.8
// 26/8/2025 20:43:23


package rs.ac.bg.etf.pp1.ast;

public class ConstVarList_var extends ConstVarList {

    private ConstVarList ConstVarList;
    private VarList VarList;

    public ConstVarList_var (ConstVarList ConstVarList, VarList VarList) {
        this.ConstVarList=ConstVarList;
        if(ConstVarList!=null) ConstVarList.setParent(this);
        this.VarList=VarList;
        if(VarList!=null) VarList.setParent(this);
    }

    public ConstVarList getConstVarList() {
        return ConstVarList;
    }

    public void setConstVarList(ConstVarList ConstVarList) {
        this.ConstVarList=ConstVarList;
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
        if(ConstVarList!=null) ConstVarList.accept(visitor);
        if(VarList!=null) VarList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstVarList!=null) ConstVarList.traverseTopDown(visitor);
        if(VarList!=null) VarList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstVarList!=null) ConstVarList.traverseBottomUp(visitor);
        if(VarList!=null) VarList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstVarList_var(\n");

        if(ConstVarList!=null)
            buffer.append(ConstVarList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarList!=null)
            buffer.append(VarList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstVarList_var]");
        return buffer.toString();
    }
}
