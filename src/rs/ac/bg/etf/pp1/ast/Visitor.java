// generated with ast extension for cup
// version 0.8
// 26/8/2025 20:43:23


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(Mulop Mulop);
    public void visit(Constant Constant);
    public void visit(MethodVarList MethodVarList);
    public void visit(FactorSign FactorSign);
    public void visit(MulopFactorList MulopFactorList);
    public void visit(MethodStatementList MethodStatementList);
    public void visit(Addop Addop);
    public void visit(Factor Factor);
    public void visit(Designator Designator);
    public void visit(ActParsList ActParsList);
    public void visit(MethodList MethodList);
    public void visit(ConstDeclMore ConstDeclMore);
    public void visit(ActParsMore ActParsMore);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(Statement Statement);
    public void visit(VarDecl VarDecl);
    public void visit(ConstVarList ConstVarList);
    public void visit(VarDeclMore VarDeclMore);
    public void visit(AddopTermList AddopTermList);
    public void visit(MethodStatementList_e MethodStatementList_e);
    public void visit(MethodStatementList_more MethodStatementList_more);
    public void visit(MethodVarList_e MethodVarList_e);
    public void visit(MethodVarList_more MethodVarList_more);
    public void visit(MethodDecl MethodDecl);
    public void visit(MethodList_e MethodList_e);
    public void visit(MethodList_more MethodList_more);
    public void visit(MethodName MethodName);
    public void visit(MethodSignature MethodSignature);
    public void visit(VarDeclMore_e VarDeclMore_e);
    public void visit(VarDeclMore_more VarDeclMore_more);
    public void visit(VarDecl_more VarDecl_more);
    public void visit(VarDecl_one VarDecl_one);
    public void visit(VarList VarList);
    public void visit(ConstDeclMore_e ConstDeclMore_e);
    public void visit(ConstDeclMore_more ConstDeclMore_more);
    public void visit(Constant_bool Constant_bool);
    public void visit(Constant_char Constant_char);
    public void visit(Constant_num Constant_num);
    public void visit(ConstDecl ConstDecl);
    public void visit(ConstList ConstList);
    public void visit(ConstVarList_e ConstVarList_e);
    public void visit(ConstVarList_var ConstVarList_var);
    public void visit(ConstVarList_const ConstVarList_const);
    public void visit(Statement_printNoConst Statement_printNoConst);
    public void visit(Statement_printConst Statement_printConst);
    public void visit(Statement_read Statement_read);
    public void visit(Stetement_desig Stetement_desig);
    public void visit(DesignatorStatement_error DesignatorStatement_error);
    public void visit(DesignatorStatement_actpars DesignatorStatement_actpars);
    public void visit(DesignatorStatement_dec DesignatorStatement_dec);
    public void visit(DesignatorStatement_inc DesignatorStatement_inc);
    public void visit(DesignatorStatement_setop DesignatorStatement_setop);
    public void visit(DesignatorStatement_exp DesignatorStatement_exp);
    public void visit(ActParsList_e ActParsList_e);
    public void visit(ActParsList_list ActParsList_list);
    public void visit(ActParsMore_e ActParsMore_e);
    public void visit(ActParsMore_more ActParsMore_more);
    public void visit(ActPars ActPars);
    public void visit(MulopFactorList_one MulopFactorList_one);
    public void visit(MulopFactorList_more MulopFactorList_more);
    public void visit(FactorSign_newT FactorSign_newT);
    public void visit(FactorSign_desiActPars FactorSign_desiActPars);
    public void visit(FactorSign_desiNoActPars FactorSign_desiNoActPars);
    public void visit(FactorSign_b FactorSign_b);
    public void visit(FactorSign_exp FactorSign_exp);
    public void visit(FactorSign_c FactorSign_c);
    public void visit(FactorSign_n FactorSign_n);
    public void visit(Factor_plus Factor_plus);
    public void visit(Factor_minus Factor_minus);
    public void visit(Term Term);
    public void visit(AddopTermList_one AddopTermList_one);
    public void visit(AddopTermList_more AddopTermList_more);
    public void visit(Expr Expr);
    public void visit(DesignatorName DesignatorName);
    public void visit(Designator_one Designator_one);
    public void visit(Designator_list Designator_list);
    public void visit(Setop Setop);
    public void visit(Mulop_mod Mulop_mod);
    public void visit(Mulop_div Mulop_div);
    public void visit(Mulop_mul Mulop_mul);
    public void visit(Addop_minus Addop_minus);
    public void visit(Addop_plus Addop_plus);
    public void visit(Assignop Assignop);
    public void visit(Label Label);
    public void visit(Type Type);
    public void visit(ProgramName ProgramName);
    public void visit(Program Program);

}
