// generated with ast extension for cup
// version 0.8
// 26/8/2025 20:43:23


package rs.ac.bg.etf.pp1.ast;

public class MethodDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private MethodSignature MethodSignature;
    private MethodVarList MethodVarList;
    private MethodStatementList MethodStatementList;

    public MethodDecl (MethodSignature MethodSignature, MethodVarList MethodVarList, MethodStatementList MethodStatementList) {
        this.MethodSignature=MethodSignature;
        if(MethodSignature!=null) MethodSignature.setParent(this);
        this.MethodVarList=MethodVarList;
        if(MethodVarList!=null) MethodVarList.setParent(this);
        this.MethodStatementList=MethodStatementList;
        if(MethodStatementList!=null) MethodStatementList.setParent(this);
    }

    public MethodSignature getMethodSignature() {
        return MethodSignature;
    }

    public void setMethodSignature(MethodSignature MethodSignature) {
        this.MethodSignature=MethodSignature;
    }

    public MethodVarList getMethodVarList() {
        return MethodVarList;
    }

    public void setMethodVarList(MethodVarList MethodVarList) {
        this.MethodVarList=MethodVarList;
    }

    public MethodStatementList getMethodStatementList() {
        return MethodStatementList;
    }

    public void setMethodStatementList(MethodStatementList MethodStatementList) {
        this.MethodStatementList=MethodStatementList;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodSignature!=null) MethodSignature.accept(visitor);
        if(MethodVarList!=null) MethodVarList.accept(visitor);
        if(MethodStatementList!=null) MethodStatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodSignature!=null) MethodSignature.traverseTopDown(visitor);
        if(MethodVarList!=null) MethodVarList.traverseTopDown(visitor);
        if(MethodStatementList!=null) MethodStatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodSignature!=null) MethodSignature.traverseBottomUp(visitor);
        if(MethodVarList!=null) MethodVarList.traverseBottomUp(visitor);
        if(MethodStatementList!=null) MethodStatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDecl(\n");

        if(MethodSignature!=null)
            buffer.append(MethodSignature.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodVarList!=null)
            buffer.append(MethodVarList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodStatementList!=null)
            buffer.append(MethodStatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDecl]");
        return buffer.toString();
    }
}
