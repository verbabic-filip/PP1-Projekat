package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticAnalyzer extends VisitorAdaptor {
	
	private boolean errorDetected = false;
	Logger log = Logger.getLogger(getClass());
	private Obj currentProgram;
	private Struct currentType;
	private int constValue;
	private Struct constantType;
	private Struct boolType = Tab.find("bool").getType();
	private Struct setType = Tab.find("set").getType();
	private boolean mainHappened = false;
	private Obj currentMethod;
	private List<Struct> list = new ArrayList<>();
	int nVars;

	/* LOG MESSAGES */
	public void report_error(String message, SyntaxNode info) {
		errorDetected  = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	public boolean passed() {
		return !errorDetected;
	}
	
	
	/* Program */
	
	@Override
	public void visit(ProgramName pName) {
		currentProgram = Tab.insert(Obj.Prog, pName.getI1(), Tab.noType);
		Tab.openScope();
	}
	
	@Override
	public void visit(Program program) {
		nVars = Tab.currentScope().getnVars();
		Tab.chainLocalSymbols(currentProgram);
		Tab.closeScope();
		currentProgram = null;
		if(!mainHappened) {
			report_error("Program nema main metodu!", program);
		}
	}
	
	//Konstante
	
	@Override
	public void visit(Type t) {
		Obj tObj = Tab.find(t.getI1());
		if(tObj == Tab.noObj) {
			report_error("Nepostojeci tip podatka: " + t.getI1(), t);
			t.struct = currentType = Tab.noType;
		}
		else if(tObj.getKind() != Obj.Type) {
			report_error("Neadekvatan tip podatka: " + t.getI1(), t);
			t.struct = currentType = Tab.noType;
		}
		else {
			t.struct = currentType = tObj.getType();
		}
	}
	
	@Override
	public void visit(Constant_num number) {
		constValue = number.getN1();
		constantType = Tab.intType;
	}
	
	@Override
	public void visit(Constant_char character) {
		constValue = character.getC1();
		constantType = Tab.charType;
	}
	
	@Override
	public void visit(Constant_bool bool) {
		constValue = bool.getB1();
		constantType = boolType;
	}
	
	@Override
	public void visit(ConstDecl constD) {
		Obj constObj = Tab.find(constD.getI1());
		if(constObj != Tab.noObj) {
			report_error("Konstanta " + constD.getI1() + " je vec definisana", constD);
		}
		else {
			if(constantType.assignableTo(currentType)) {
				constObj = Tab.insert(Obj.Con, constD.getI1(), currentType);
				constObj.setAdr(constValue);
			}
			else {
				report_error("Pogresan tip za dodelu konstanti: " + constD.getI1(), constD);
			}
		}
	}
	
	// Varijable
	
	@Override
	public void visit(VarDecl_one varDecl_one) {
		Obj variableObj = null;
		if(currentMethod == null) { 
			variableObj = Tab.find(varDecl_one.getI1());
		}
		else {
			variableObj = Tab.currentScope().findSymbol(varDecl_one.getI1());
		}
		if(variableObj == null || variableObj == Tab.noObj) {
			variableObj = Tab.insert(Obj.Var, varDecl_one.getI1(), currentType);
		}
		else {
			report_error("Varijabla " + varDecl_one.getI1() + " je vec definisana", varDecl_one);
		}
	}
	
	@Override
	public void visit(VarDecl_more varDecl_more) {
		Obj variableObj = null;
		if(currentMethod == null) { 
			variableObj = Tab.find(varDecl_more.getI1());
		}
		else {
			variableObj = Tab.currentScope().findSymbol(varDecl_more.getI1());
		}
		if(variableObj == null || variableObj == Tab.noObj) {
			variableObj = Tab.insert(Obj.Var, varDecl_more.getI1(), new Struct(Struct.Array, currentType));
		}
		else {
			report_error("Varijabla " + varDecl_more.getI1() + " je vec definisana", varDecl_more);
		}
	}
	
	
	// Metode
	
	@Override
	public void visit(MethodName mn) {
		if(mn.getI1().equals("main")) {
			mainHappened = true;
		}
		mn.obj = currentMethod = Tab.insert(Obj.Meth, mn.getI1(), Tab.noType);
		Tab.openScope();
	}
	
	@Override
	public void visit(MethodDecl md) {
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		currentMethod = null;
	}
	
	// Kontekstni uslovi
	
	@Override
	public void visit(FactorSign_c factorSign_c) {
		factorSign_c.struct = Tab.charType;
	}
	
	@Override
	public void visit(FactorSign_n factorSign_n) {
		factorSign_n.struct = Tab.intType;
	}
	
	@Override
	public void visit(FactorSign_b factorSign_b) {
		factorSign_b.struct = boolType;
	}
	
	@Override
	public void visit(Factor_minus factor_minus) {
		if(factor_minus.getFactorSign().struct.equals(Tab.intType)) {
			factor_minus.struct = Tab.intType;
		}
		else {
			report_error("Tip podatka ne moze biti negiran: ", factor_minus);
			factor_minus.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(Factor_plus factor_plus) {
		factor_plus.struct = factor_plus.getFactorSign().struct;
	}
	
	@Override
	public void visit(FactorSign_desiNoActPars factorSign_desiNoActPars) {
		factorSign_desiNoActPars.struct = factorSign_desiNoActPars.getDesignator().obj.getType();
	}
	
	@Override
	public void visit(FactorSign_desiActPars factorSign_desiActPars) {
		factorSign_desiActPars.struct = factorSign_desiActPars.getDesignator().obj.getType();
		if(factorSign_desiActPars.getDesignator().obj.getKind() != Obj.Meth) {
			report_error("Poziv neadekvatne metode: " + factorSign_desiActPars.getDesignator().obj.getName(), factorSign_desiActPars);
		}
		else if(factorSign_desiActPars.getDesignator().obj.getLevel() != list.size()) {
			report_error("Broj prosledjenih parametara nije validan: " + factorSign_desiActPars.getDesignator().obj.getName(), factorSign_desiActPars);
		}
		else {
			int i = 0;
			for(Obj o : factorSign_desiActPars.getDesignator().obj.getLocalSymbols()) {
				if(!list.get(i).assignableTo(o.getType())) {
					report_error("Tip prosledjenog parametra se ne poklapa:", factorSign_desiActPars);
					break;
				}
				i++;
			}
		}
		list.clear();
	}
	
	@Override
	public void visit(Designator_one designator_one) {
		Obj p = Tab.find(designator_one.getI1());
		if(p == Tab.noObj) {
			report_error("Promenljiva nije definisana: " + designator_one.getI1(), designator_one);
			designator_one.obj = Tab.noObj;
		}
		else if(p.getKind() != Obj.Var && p.getKind() != Obj.Con && p.getKind() != Obj.Elem && p.getKind() != Obj.Meth){
			report_error("Neadekvatna promenljiva: " + designator_one.getI1(), designator_one);
			designator_one.obj = Tab.noObj;
		}
		else {
			designator_one.obj = p;
		}
	}
	
	
	@Override
	public void visit(DesignatorName dn) {
		Obj p = Tab.find(dn.getI1());
		if(p == Tab.noObj) {
			report_error("Promenljiva nije definisana: " + dn.getI1(), dn);
			dn.obj = Tab.noObj;
		}
		else if(p.getKind() != Obj.Var && p.getType().getKind() != Struct.Array){
			report_error("Neadekvatna promenljiva niza: " + dn.getI1(), dn);
			dn.obj = Tab.noObj;
		}
		else {
			dn.obj = p;
		}
	}
	
	@Override
	public void visit(Designator_list designator_list) {
		Obj d = designator_list.getDesignatorName().obj;
		if(d == Tab.noObj) {
			designator_list.obj = Tab.noObj;
		}
		else if(!designator_list.getExpr().struct.equals(Tab.intType)) {
			report_error("Indeksiranje vrednoscu koja nije int: ", designator_list);
			designator_list.obj = Tab.noObj;
		}
		else {
			designator_list.obj = new Obj(Obj.Elem, d.getName() + "[*]", d.getType().getElemType());
		}
	}
	
	
	//Factor
	
	@Override
	public void visit(FactorSign_newT factorSign_newT) {
		if(!factorSign_newT.getExpr().struct.equals(Tab.intType)) {
			report_error("Velicina niza nije int: ", factorSign_newT);
			factorSign_newT.struct = Tab.noType;
		}
		else {
			factorSign_newT.struct = new Struct(Struct.Array, currentType);
		}
	}
	
	
	@Override
	public void visit(FactorSign_exp factorSign_exp) {
		factorSign_exp.struct = factorSign_exp.getExpr().struct;
	}
	
	
	@Override
	public void visit(MulopFactorList_more mulopFactorList_more) {
		if(mulopFactorList_more.getMulopFactorList().struct.equals(Tab.intType) && mulopFactorList_more.getFactor().struct.equals(Tab.intType)) {
			mulopFactorList_more.struct = Tab.intType;
		}
		else {
			report_error("Tipovi nisu int za operaciju mulop: ", mulopFactorList_more);
			mulopFactorList_more.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(MulopFactorList_one mulopFactorList_one) {
		mulopFactorList_one.struct = mulopFactorList_one.getFactor().struct;
	}
	
	@Override
	public void visit(Term t) {
		t.struct = t.getMulopFactorList().struct;
	}
	
	@Override
	public void visit(AddopTermList_more addopTermList_more) {
		if(addopTermList_more.getAddopTermList().struct.equals(Tab.intType) && addopTermList_more.getTerm().struct.equals(Tab.intType)) {
			addopTermList_more.struct = Tab.intType;
		}
		else {
			report_error("Tipovi nisu int za operaciju mulop: ", addopTermList_more);
			addopTermList_more.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(AddopTermList_one addopTermList_one) {
		addopTermList_one.struct = addopTermList_one.getTerm().struct;
	}
	
	@Override
	public void visit(Expr e) {
		e.struct = e.getAddopTermList().struct;
	}
	
	
	// Designator Statements
	@Override
	public void visit(DesignatorStatement_setop designatorStatement_setop) {
		Struct d1 = designatorStatement_setop.getDesignator().obj.getType();
		Struct d2 = designatorStatement_setop.getDesignator1().obj.getType();
		Struct d3 = designatorStatement_setop.getDesignator2().obj.getType();
		if(d1 != setType && d2 != setType && d3 != setType) {
			report_error("Sve promenljive moraju biti tipa set: ", designatorStatement_setop);
		}
	}
	
	@Override
	public void visit(DesignatorStatement_exp designatorStatement_exp) {
		if(designatorStatement_exp.getDesignator().obj.getKind() != Obj.Var && designatorStatement_exp.getDesignator().obj.getKind() != Obj.Elem) {
			report_error("Promenljiva je neadekvatnog tipa: " + designatorStatement_exp.getDesignator().obj.getName(), designatorStatement_exp);
		}
		else if(designatorStatement_exp.getExpr().struct.getKind() != designatorStatement_exp.getDesignator().obj.getType().getKind()) {
			report_error("Ova promenljiva ne prihvata taj tip podatka: " + designatorStatement_exp.getDesignator().obj.getName(), designatorStatement_exp);
		}
	}
	
	@Override
	public void visit(DesignatorStatement_inc designatorStatement_inc) {
		if(designatorStatement_inc.getDesignator().obj.getKind() != Obj.Var && designatorStatement_inc.getDesignator().obj.getKind() != Obj.Elem) {
			report_error("Greska: Inkrement promenljive koja nije tipa int: " + designatorStatement_inc.getDesignator().obj.getName(), designatorStatement_inc);
		}
		else if(!designatorStatement_inc.getDesignator().obj.getType().equals(Tab.intType)) {
			report_error("Greska: Inkrement promenljive koja nije tipa int: " + designatorStatement_inc.getDesignator().obj.getName(), designatorStatement_inc);
		}
	}
	
	@Override
	public void visit(DesignatorStatement_dec designatorStatement_dec) {
		if(designatorStatement_dec.getDesignator().obj.getKind() != Obj.Var && designatorStatement_dec.getDesignator().obj.getKind() != Obj.Elem) {
			report_error("Greska: Dekrement promenljive koja nije tipa int: " + designatorStatement_dec.getDesignator().obj.getName(), designatorStatement_dec);
		}
		else if(!designatorStatement_dec.getDesignator().obj.getType().equals(Tab.intType)) {
			report_error("Greska: Dekrement promenljive koja nije tipa int: " + designatorStatement_dec.getDesignator().obj.getName(), designatorStatement_dec);
		}
	}
	
	@Override
	public void visit(DesignatorStatement_actpars designatorStatement_actpars) {
		if(designatorStatement_actpars.getDesignator().obj.getKind() != Obj.Meth) {
			report_error("Poziv neadekvatne metode: " + designatorStatement_actpars.getDesignator().obj.getName(), designatorStatement_actpars);
		}
		else if(designatorStatement_actpars.getDesignator().obj.getLevel() != list.size()) {
			report_error("Broj prosledjenih parametara nije validan: " + designatorStatement_actpars.getDesignator().obj.getName(), designatorStatement_actpars);
		}
		else {
			int i = 0;
			for(Obj o : designatorStatement_actpars.getDesignator().obj.getLocalSymbols()) {
				if(!list.get(i).assignableTo(o.getType())) {
					report_error("Tip prosledjenog parametra se ne poklapa:", designatorStatement_actpars);
					break;
				}
				i++;
			}
		}
		list.clear();
	}
	
	
	//Statement
	@Override
	public void visit(Statement_read statement_read) {
		Struct t = statement_read.getDesignator().obj.getType();
		if(statement_read.getDesignator().obj.getKind() != Obj.Var && statement_read.getDesignator().obj.getKind() != Obj.Elem) {
			report_error("Greska: Read neadekvatne promenljive: " + statement_read.getDesignator().obj.getName(), statement_read);
		}
		else if(!t.equals(Tab.intType) && !t.equals(Tab.charType) && !t.equals(boolType)) {
			report_error("Greska: Read promenljive koja nije tipa int, char ili bool: " + statement_read.getDesignator().obj.getName(), statement_read);
		}
	}
	
	@Override
	public void visit(Statement_printConst statement_printConst) {
		Struct t = statement_printConst.getExpr().struct;
		if(!t.equals(Tab.intType) && !t.equals(Tab.charType) && !t.equals(boolType) && !t.equals(setType)) {
			report_error("Greska: Print promenljive koja nije tipa int, char, bool ili set", statement_printConst);
		}
	}
	
	@Override
	public void visit(Statement_printNoConst statement_printNoConst) {
		Struct t = statement_printNoConst.getExpr().struct;
		if(!t.equals(Tab.intType) && !t.equals(Tab.charType) && !t.equals(boolType) && !t.equals(setType)) {
			report_error("Greska: Print promenljive koja nije tipa int, char, bool ili set", statement_printNoConst);
		}
	}
	
	
	// ActPars
	
	@Override
	public void visit(ActPars actPars) {
		list.add(actPars.getExpr().struct);
	}
	
	
	
}
