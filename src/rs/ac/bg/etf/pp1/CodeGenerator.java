package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

public class CodeGenerator extends VisitorAdaptor {
	
	private int mainPc;
	
	public int getMainPc() {
		return this.mainPc;
	}
	
	public CodeGenerator() {
		
		Tab.find("add").setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(2);
		Code.put(4);
		//setAddr, elemToAdd, BrElemUSetu, indexDekrementirajuci
		Code.put(Code.load_n);   // add(set, int);
		Code.put(Code.arraylength);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.put(Code.load_n);
		Code.loadConst(0);
		Code.put(Code.aload);  //arraylen-1, brElemUSetu
		Code.put(Code.store_2);  // reg_2 = len(a)
		Code.put(Code.load_2);
		Code.putFalseJump(Code.ne, 0);
		int iskociPunSet = Code.pc - 2;
		
		//ima mesta provera da li je u setu vec
		
		
		Code.put(Code.load_2);
		Code.put(Code.store_3);
		
		// pocetak petlje
		int start = Code.pc;

		Code.put(Code.load_3);
		Code.loadConst(0);
		Code.putFalseJump(Code.ne, 0);
		int krajFora = Code.pc - 2;
		
		Code.put(Code.load_n);
		Code.put(Code.load_3);
		Code.put(Code.aload); //elemZaProveru
		Code.put(Code.load_1);//elemZaProveru, elemZaDodavanje
		
		Code.putFalseJump(Code.ne, 0);
		int iskociIsti = Code.pc-2;
		
		Code.put(Code.load_3);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.put(Code.store_3);
		Code.putJump(start);
		
		Code.fixup(krajFora);
		
		//azuriranje nultog elem
		
		Code.put(Code.load_n);
		Code.loadConst(0);
		Code.put(Code.load_2);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.put(Code.astore);
		
		//dodavanje u set
		Code.put(Code.load_n);
		Code.put(Code.load_n);
		Code.loadConst(0);
		Code.put(Code.aload);
		Code.put(Code.load_1);
		Code.put(Code.astore);
		

		
		Code.fixup(iskociPunSet);
		Code.fixup(iskociIsti);
		
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		
		
		// Addall
		Tab.find("addAll").setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(2);
		Code.put(4);
		
		// 0: set
		// 1: array
		// 2: len(arr)
		// 3: i

		Code.put(Code.load_n + 1);
		Code.put(Code.arraylength);
		Code.put(Code.store_n + 2); // reg_2 = len(b)
		Code.loadConst(0);
		Code.put(Code.store_n + 3); // reg_3 = i (indeks)
		
		
		int loopStartAll = Code.pc;

		// if (i >= len(b)) goto END
		Code.put(Code.load_n + 3);
		Code.put(Code.load_n + 2);
		Code.putFalseJump(Code.lt, 0);
		int jmpEndAll = Code.pc - 2;
		
		Code.put(Code.load_n + 0); // set a

		// učitaj b[i]
		Code.put(Code.load_n + 1);
		Code.put(Code.load_n + 3);
		Code.put(Code.aload); // b[i]

		// pozovi add
		Code.put(Code.call);
		Code.put2(Tab.find("add").getAdr() - Code.pc + 1);

		// i++ ; goto LOOP
		Code.put(Code.load_n + 3);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.put(Code.store_n + 3);
		Code.putJump(loopStartAll);

		// END
		Code.fixup(jmpEndAll);
		Code.put(Code.exit);
		Code.put(Code.return_);
		

		Code.put(Code.exit);
		Code.put(Code.return_);
		
		Tab.find("addAllSet").setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(2);
		Code.put(4);
		
		// 0: set
		// 1: array
		// 2: len(arr)
		// 3: i

		Code.put(Code.load_n + 1);
		Code.loadConst(0);
		Code.put(Code.aload);
		Code.put(Code.store_n + 2); // reg_2 = len(b)
		Code.loadConst(1);
		Code.put(Code.store_n + 3); // reg_3 = i (indeks)
		
		
		int loopStartAll2 = Code.pc;

		// if (i >= len(b)) goto END
		Code.put(Code.load_n + 3);
		Code.put(Code.load_n + 2);
		Code.putFalseJump(Code.le, 0);
		int jmpEndAll2 = Code.pc - 2;
		
		Code.put(Code.load_n + 0); // set a

		// učitaj b[i]
		Code.put(Code.load_n + 1);
		Code.put(Code.load_n + 3);
		Code.put(Code.aload); // b[i]

		// pozovi add
		Code.put(Code.call);
		Code.put2(Tab.find("add").getAdr() - Code.pc + 1);

		// i++ ; goto LOOP
		Code.put(Code.load_n + 3);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.put(Code.store_n + 3);
		Code.putJump(loopStartAll2);

		// END
		Code.fixup(jmpEndAll2);
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		
		
		Tab.find("overlaps").setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(2);
		Code.put(4);
		
		Code.loadConst(1);
		Code.put(Code.store_n + 2);
		
		int loopOutter = Code.pc;
		
		Code.put(Code.load_n + 2);
		Code.put(Code.load_n + 0);
		Code.loadConst(0);
		Code.put(Code.aload);
		Code.putFalseJump(Code.le, 0);
		int jumpEndOutter = Code.pc - 2;
		
		Code.loadConst(1);
		Code.put(Code.store_n + 3);
		
		int loopInner = Code.pc;
		
		Code.put(Code.load_n + 3);
		Code.put(Code.load_n + 1);
		Code.loadConst(0);
		Code.put(Code.aload);
		Code.putFalseJump(Code.le, 0);
		int jumpEndInner = Code.pc - 2;
		
		Code.put(Code.load_n + 0);
		Code.put(Code.load_n + 2);
		Code.put(Code.aload);
		Code.put(Code.load_n + 1);
		Code.put(Code.load_n + 3);
		Code.put(Code.aload);
		Code.putFalseJump(Code.ne, 0);
		int jumpFound = Code.pc - 2;
		
		Code.put(Code.load_n + 3);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.put(Code.store_n + 3);
		
		Code.fixup(jumpEndInner);
		
		Code.put(Code.load_n + 2);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.put(Code.store_n + 2);
		
		Code.putJump(loopOutter);
		
		Code.fixup(jumpFound);
		Code.loadConst(1);
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		
		Code.fixup(jumpEndOutter);
		Code.loadConst(0);
		Code.put(Code.exit);
		Code.put(Code.return_);
		

	}
	
	
	@Override
	public void visit(MethodName mn) {
		mn.obj.setAdr(Code.pc);
		if(mn.getI1().equals("main")) {
			this.mainPc = Code.pc;
		}
		Code.put(Code.enter);
		Code.put(mn.obj.getLevel());
		Code.put(mn.obj.getLocalSymbols().size());
	}
	
	@Override
	public void visit(MethodDecl md) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(Statement_printNoConst statement_printNoConst) {
		
		if(statement_printNoConst.getExpr().struct.equals(Tab.find("set").getType())) {
			
			Code.put(Code.store_n + 0); // reg_0 = Adr(array);
			Code.put(Code.load_n + 0);
	        
	        Code.loadConst(0);
	        Code.put(Code.aload);
	        Code.put(Code.store_n + 1); // reg_1 = arraylength;
	        
	        Code.loadConst(1);
	        Code.put(Code.store_n + 2); // reg_2 = i;

	        // Loop
	        int loopStart = Code.pc;

	        // if (i >= len) goto END
	        Code.put(Code.load_n + 2);
	        Code.put(Code.load_n + 1);
	        Code.putFalseJump(Code.le, 0);
	        int jmpEnd = Code.pc - 2;

	        // ucitavanje arr[i]
	        Code.put(Code.load_n + 0);
	        Code.put(Code.load_n + 2);
	        Code.put(Code.aload);     // aload: adr, index

	        
	        Code.loadConst(3);  // width za print
	        Code.put(Code.print);
	        Code.loadConst(' ');
	        Code.loadConst(0);
	        Code.put(Code.bprint);

	        // i++
	        Code.put(Code.load_n + 2);
	        Code.loadConst(1);
	        Code.put(Code.add);
	        Code.put(Code.store_n + 2);

	        // goto LOOP
	        Code.putJump(loopStart);

	        // END:
	        Code.fixup(jmpEnd);

		} 
		else if(statement_printNoConst.getExpr().struct.equals(Tab.charType)) {
			Code.loadConst(0);
			Code.put(Code.bprint);
		}
		else if(statement_printNoConst.getExpr().struct.equals(Tab.find("bool").getType())) {
			Code.loadConst(0);
			Code.putFalseJump(Code.ne, 0);
			int jmpPrintFalse = Code.pc - 2;
			Code.loadConst(84);
			Code.loadConst(0);
			Code.put(Code.bprint);
			Code.loadConst(114);
			Code.loadConst(0);
			Code.put(Code.bprint);
			Code.loadConst(117);
			Code.loadConst(0);
			Code.put(Code.bprint);
			Code.loadConst(101);
			Code.loadConst(0);
			Code.put(Code.bprint);
			
			Code.put(Code.exit);
			Code.put(Code.return_);
			
			Code.fixup(jmpPrintFalse);
			Code.loadConst(70);
			Code.loadConst(0);
			Code.put(Code.bprint);
			Code.loadConst(97);
			Code.loadConst(0);
			Code.put(Code.bprint);
			Code.loadConst(108);
			Code.loadConst(0);
			Code.put(Code.bprint);
			Code.loadConst(115);
			Code.loadConst(0);
			Code.put(Code.bprint);
			Code.loadConst(101);
			Code.loadConst(0);
			Code.put(Code.bprint);
		}
		else {
			Code.loadConst(0);
			Code.put(Code.print);
		}
	}
	
	@Override
	public void visit(Statement_printConst statement_printConst) {
		Code.loadConst(statement_printConst.getN2());
		if(statement_printConst.getExpr().struct.equals(Tab.charType)) {
			Code.put(Code.bprint);
		}
		else {
			Code.put(Code.print);
		}
	}
	
	
	@Override
	public void visit(AddopTermList_more addopTermList_more) {
		if(addopTermList_more.getAddop() instanceof Addop_plus) {
			Code.put(Code.add);
		}
		else {
			Code.put(Code.sub);
		}
	}
	
	@Override
	public void visit(FactorSign_n factorSign_n) {
		Code.loadConst(factorSign_n.getN1());
	}
	
	@Override
	public void visit(FactorSign_c factorSign_c) {
		Code.loadConst(factorSign_c.getC1());
	}
	
	@Override
	public void visit(FactorSign_b factorSign_b) {
		Code.loadConst(factorSign_b.getB1());
	}
	
	
	@Override
	public void visit(MulopFactorList_more mulopFactorList_more) {
		if(mulopFactorList_more.getMulop() instanceof Mulop_div) {
			Code.put(Code.div);
		}
		else if(mulopFactorList_more.getMulop() instanceof Mulop_mul) {
			Code.put(Code.mul);
		}
		else {
			Code.put(Code.rem);
		}
	}
	
	@Override
	public void visit(FactorSign_desiNoActPars factorSign_desiNoActPars) {
		Code.load(factorSign_desiNoActPars.getDesignator().obj);
	}
	
	@Override
	public void visit(DesignatorName designatorName) {
		Code.load(designatorName.obj);
	}
	
	@Override
	public void visit(DesignatorStatement_exp designatorStatement_exp) {
		Code.store(designatorStatement_exp.getDesignator().obj);
	}
	
	@Override
	public void visit(Factor_minus factor_minus) {
		Code.put(Code.neg); 
	}
	
	@Override
	public void visit(FactorSign_newT factorSign_newT) {
		if(factorSign_newT.getType().getI1().equals("set")) {
			Code.loadConst(1);
			Code.put(Code.add);
		}
		Code.put(Code.newarray);
		if(factorSign_newT.getType().struct.equals(Tab.charType)) {
			Code.put(0);
		}
		else {
			Code.put(1);
		}
	}
	
	@Override
	public void visit(DesignatorStatement_inc designatorStatement_inc) {
		if(designatorStatement_inc.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(designatorStatement_inc.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(designatorStatement_inc.getDesignator().obj);
	}
	
	@Override
	public void visit(DesignatorStatement_dec designatorStatement_dec) {
		if(designatorStatement_dec.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(designatorStatement_dec.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(designatorStatement_dec.getDesignator().obj);
	}
	
	@Override
	public void visit(Statement_read statement_read) {
		if(statement_read.getDesignator().obj.getType().equals(Tab.charType)) {
			Code.put(Code.bread);
		}
		else {
			Code.put(Code.read);
		}
		Code.store(statement_read.getDesignator().obj);
	}
	
	// Setovi
	
	@Override
	public void visit(DesignatorStatement_setop designatorStatement_setop) {
		Code.load(designatorStatement_setop.getDesignator().obj);
		Code.load(designatorStatement_setop.getDesignator1().obj);
		Code.put(Code.call);
		Code.put2(Tab.find("addAllSet").getAdr() - Code.pc + 1);
		Code.load(designatorStatement_setop.getDesignator().obj);
		Code.load(designatorStatement_setop.getDesignator2().obj);
		Code.put(Code.call);
		Code.put2(Tab.find("addAllSet").getAdr() - Code.pc + 1);
	}
	
	
	@Override
	public void visit(DesignatorStatement_actpars designatorStatement_actpars) {
		Code.put(Code.call);
		Code.put2(designatorStatement_actpars.getDesignator().obj.getAdr() - Code.pc + 1);
	}
	
	@Override
	public void visit(FactorSign_desiActPars factorSign_desiActPars) {
		Code.put(Code.call);
		Code.put2(factorSign_desiActPars.getDesignator().obj.getAdr() - Code.pc + 1);
	}
}
