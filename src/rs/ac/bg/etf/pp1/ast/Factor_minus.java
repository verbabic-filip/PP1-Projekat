// generated with ast extension for cup
// version 0.8
// 26/8/2025 20:43:23


package rs.ac.bg.etf.pp1.ast;

public class Factor_minus extends Factor {

    private FactorSign FactorSign;

    public Factor_minus (FactorSign FactorSign) {
        this.FactorSign=FactorSign;
        if(FactorSign!=null) FactorSign.setParent(this);
    }

    public FactorSign getFactorSign() {
        return FactorSign;
    }

    public void setFactorSign(FactorSign FactorSign) {
        this.FactorSign=FactorSign;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FactorSign!=null) FactorSign.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FactorSign!=null) FactorSign.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FactorSign!=null) FactorSign.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Factor_minus(\n");

        if(FactorSign!=null)
            buffer.append(FactorSign.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Factor_minus]");
        return buffer.toString();
    }
}
