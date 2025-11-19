package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;

public class Compiler {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void main(String[] args) throws Exception {
		
		Logger log = Logger.getLogger(Compiler.class);
		
		Reader br = null;
		try {
			File sourceCode = new File("test/program.mj");
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();  //formiranje AST
	        
	        Program prog = (Program)(s.value); 
	        
	        
			/* Ispis AST */
			log.info(prog.toString(""));
			log.info("=====================================================================");
			
			
			/* Inicijalizacija tabele simbola */
			Tab.init();
			Struct boolType = new Struct(Struct.Bool);
			Struct setType = new Struct(Struct.Array);
			setType.setElementType(Tab.intType);
			Obj boolObj = Tab.insert(Obj.Type, "bool", boolType);
			Obj setObj = Tab.insert(Obj.Type, "set", setType);
			boolObj.setAdr(-1);
			boolObj.setLevel(-1);
			setObj.setAdr(-1);
			setObj.setLevel(-1);
			
			List<String> uni_meth = new ArrayList<>();
			uni_meth.add("chr");
			uni_meth.add("ord");
			uni_meth.add("len");
			for(String meth : uni_meth) {
				for(Obj fp:Tab.find(meth).getLocalSymbols()) {
					fp.setFpPos(1);
				}
			}
			
			Obj ordMethod = Tab.find("ord");
			Obj chrMethod = Tab.find("chr");
			ordMethod.setAdr(Code.pc);
			chrMethod.setAdr(Code.pc);
			Code.put(Code.enter);
			Code.put(1);
			Code.put(1);
			Code.put(Code.load_n);
			Code.put(Code.exit);
			Code.put(Code.return_);
			
			Obj lenMethod = Tab.find("len");
			lenMethod.setAdr(Code.pc);
			Code.put(Code.enter);
			Code.put(1);
			Code.put(1);
			Code.put(Code.load_n);
			Code.put(Code.arraylength);
			Code.put(Code.exit);
			Code.put(Code.return_);
			
			
			Obj tmpObj1 = Tab.insert(Obj.Meth, "add", Tab.noType);
			Tab.openScope();
			Tab.insert(Obj.Var, "s", setType);
			Tab.insert(Obj.Var, "i", Tab.intType);
			Tab.chainLocalSymbols(tmpObj1);
			Tab.closeScope();
			tmpObj1.setAdr(0);
			tmpObj1.setLevel(2);
			
			Obj tmpObj2 = Tab.insert(Obj.Meth, "addAll", Tab.noType);
			Tab.openScope();
			Tab.insert(Obj.Var, "s", setType);
			Tab.insert(Obj.Var, "i", new Struct(Struct.Array, Tab.intType));
			Tab.chainLocalSymbols(tmpObj2);
			Tab.closeScope();
			tmpObj2.setAdr(0);
			tmpObj2.setLevel(2);
		
			Obj tmpObj3 = Tab.insert(Obj.Meth, "overlaps", boolType);
			Tab.openScope();
			Tab.insert(Obj.Var, "s1", setType);
			Tab.insert(Obj.Var, "s2", setType);
			Tab.chainLocalSymbols(tmpObj3);
			Tab.closeScope();
			tmpObj3.setAdr(0);
			tmpObj3.setLevel(2);
			
			
			/* Semanticka analiza */
			SemanticAnalyzer semA = new SemanticAnalyzer();
			prog.traverseBottomUp(semA);
			
			
			/* Ispis tabele simbola */
			log.info("=====================================================================");
			Tab.dump();
			
			if(!p.errorDetected && semA.passed()){
				/* Generisanje koda */
				File objFile = new File("test/program.obj");
				if(objFile.exists()) objFile.delete();
				
				CodeGenerator cd = new CodeGenerator();
				prog.traverseBottomUp(cd);
				Code.dataSize = semA.nVars;
				Code.mainPc = cd.getMainPc();
				Code.write(new FileOutputStream(objFile));
				
				log.info("Parsiranje uspesno zavrseno!");
			}else{
				log.error("Parsiranje NIJE uspesno zavrseno!");
			}
			
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}

	}
	
	
}
