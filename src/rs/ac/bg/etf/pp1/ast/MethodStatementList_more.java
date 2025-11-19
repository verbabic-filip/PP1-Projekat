// generated with ast extension for cup
// version 0.8
// 26/8/2025 20:43:23


package rs.ac.bg.etf.pp1.ast;

public class MethodStatementList_more extends MethodStatementList {

    private MethodStatementList MethodStatementList;
    private Statement Statement;

    public MethodStatementList_more (MethodStatementList MethodStatementList, Statement Statement) {
        this.MethodStatementList=MethodStatementList;
        if(MethodStatementList!=null) MethodStatementList.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public MethodStatementList getMethodStatementList() {
        return MethodStatementList;
    }

    public void setMethodStatementList(MethodStatementList MethodStatementList) {
        this.MethodStatementList=MethodStatementList;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodStatementList!=null) MethodStatementList.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodStatementList!=null) MethodStatementList.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodStatementList!=null) MethodStatementList.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodStatementList_more(\n");

        if(MethodStatementList!=null)
            buffer.append(MethodStatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodStatementList_more]");
        return buffer.toString();
    }
}
