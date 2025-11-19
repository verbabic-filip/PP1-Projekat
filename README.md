# Mikrojava Compiler – PP1 Project

This repository contains a full compiler for the Mikrojava programming language, created as part of the course **Programski Prevodici 1 (PP1)** at the School of Electrical Engineering (ETF), University of Belgrade.

The compiler translates Mikrojava source code into `.obj` bytecode executable by the Mikrojava Virtual Machine (MJVM).

---

## Features

### Lexical Analysis (JFlex)
- Implemented in `mjlexer.flex`
- Tokenizes identifiers, keywords, numbers, operators, comments
- Skips whitespace and comments
- Reports lexical errors with line and column information

### Syntax Analysis (AST-CUP)
- Implemented in `mjparser.cup`
- LALR(1) grammar for Mikrojava
- Automatic AST generation with AST-CUP
- Syntax error detection and recovery

### Semantic Analysis
- Implemented in `SemanticAnalyzer` using Visitor pattern
- Integrated with official `symboltable-1.1.jar`
- Performs:
  - variable/constant lookup
  - type checking
  - checking function calls and arguments
  - array indexing verification
  - scope management
- Reports symbol usage and semantic violations

### Code Generation
- Implemented in `CodeGenerator`
- Generates MJ bytecode executable on MJVM
- Supports:
  - arithmetic expressions
  - assignments
  - arrays and indexing
  - print/read
  - function calls
  - control flow structures (depending on project level)
  - classes, inheritance, objects (Level C)
- Produces `.obj` output files

---

## Supported Levels

### Level A
- Basic expressions and statements
- Assignments, arithmetic, arrays
- `print`, `read`
- Function calls
- Minimal semantic checks and code generation

### Level B (includes Level A)
- `if`, `if/else`, `do-while`
- `break`, `continue`, `return`
- Logical conditions (`&&`, `||`, relational operators)
- Global function calls
- More complex expressions (`map`, etc.)

### Level C (includes Level B)
- Classes and inheritance
- Inner classes
- Virtual function tables
- Polymorphism
- Interfaces with default methods
- Arrays of objects and object creation

---

## Project Structure

