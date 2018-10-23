package service;

import exceptions.CompilerException;
import exceptions.NoMatchException;
import model.Statement;
import model.Tuple;
import repo.RepoInter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StructStatement {

    private RepoInter<Tuple> repo;
    private SimpleStatement simplestmt;

    private final String RELATION = "(<|<=|==|!=|>=|>|AND|OR)";
    private final String COMP = "(AND|OR)";
    private final String MATHOP = "(\\+|-|\\*|/)";

    public StructStatement(RepoInter<Tuple> repo) {
        this.repo = repo;
        simplestmt = new SimpleStatement(repo);
    }

    public boolean checkStructStatement(String stmt) throws CompilerException {
        try{
            checkIFStatement(stmt);
            return true;
        } catch (NoMatchException ignored) {}
        try{
            checkELSEStatement(stmt);
            return true;
        } catch (NoMatchException ignored) {}
        try{
            checkENDIFStatement(stmt);
            return true;
        } catch (NoMatchException ignored) {}
        return false;
    }

    public void checkIFStatement(String stmt) throws NoMatchException, CompilerException {
        if (!stmt.matches(".*IF.*THEN.*"))
            throw new NoMatchException();
        repo.add(new Tuple<>(Statement.IF,null));
        String condition = stmt.replaceAll(".*IF(.*)THEN.*","$1");
        String simpleStmt = stmt.replaceAll(".*IF.*THEN(.*)","$1");

        checkCondition(condition);

        simplestmt.checkSimpleStatement(simpleStmt);
    }

    public void checkELSEStatement(String stmt) throws NoMatchException, CompilerException {
        if (!stmt.matches(".*ELSE.*"))
            throw new NoMatchException();

        repo.add(new Tuple<>(Statement.ELSE,null));
        String simpleStmt = stmt.replaceAll(".*ELSE(.*)","$1");

        simplestmt.checkSimpleStatement(simpleStmt);
    }

    public void checkENDIFStatement(String stmt) throws NoMatchException {
        if (!stmt.matches(".*END-IF.*"))
            throw new NoMatchException();
        repo.add(new Tuple<>(Statement.ENDIF,null));
    }

    public void checkCondition(String condition) throws CompilerException {
        if (!condition.matches(".*" + RELATION + ".*"))
            throw new CompilerException("Condition is invalid !");
        repo.add(new Tuple<>(Statement.CONDITION,null));
        String comparer = "(.*)" + RELATION + "(.*)";
        String expression1 = condition.replaceAll(comparer,"$1");
        String expression2 = condition.replaceAll(comparer,"$3");
        checkExpression(expression1);
        repo.add(new Tuple<>(Statement.CONDITIONPARAMETER,condition.replaceAll(".*" + RELATION + ".*","$1")));
        checkExpression(expression2);
        repo.add(new Tuple<>(Statement.ENDCONDITION,null));
    }

    public void checkExpression(String expression) throws CompilerException {
        List<String> numbers = Arrays.asList(simplestmt.delimiter(expression,COMP)).stream().filter(item -> !item.equals("")).collect(Collectors.toList());
        List<String> operations = Arrays.asList(simplestmt.delimiter(expression.replace(" ",""),"[^" + COMP + "]+")).stream().filter(item -> !item.equals("")).collect(Collectors.toList());

        for (String term : simplestmt.delimiter(expression,COMP)){
            checkTerm(term);
        }
    }

    public void checkTerm(String term) throws CompilerException {
        if (term.matches(".*\\(.*" + MATHOP + ".*\\).*"))
            checkMath(term.replaceAll(". *\\((.*)\\).*","$1"));
        else if (term.matches(".*\\((.*)\\).*"))
            checkExpression(term);
        else
            repo.add(new Tuple<>(Statement.CONDITIONPARAMETER,simplestmt.getIdentifier(term.replaceAll("[)(]",""))));
    }

    public void checkMath(String math) throws CompilerException {
        repo.add(new Tuple<>(Statement.MATH,null));
        List<String> numbers = Arrays.asList(simplestmt.delimiter(math.replace(" ",""),MATHOP)).stream().filter(item -> !item.equals("")).collect(Collectors.toList());
        List<String> operations = Arrays.asList(simplestmt.delimiter(math.replace(" ",""),simplestmt.identifier)).stream().filter(item -> !item.equals("")).collect(Collectors.toList());

        if (numbers.size() != operations.size()+1)
            throw new CompilerException("Math ecuation is incorrect !");

        for (int i=0;i<numbers.size()-1;i++){
            repo.add(new Tuple<>(Statement.MATHPARAMETER,numbers.get(i).replace(" ","")));
            repo.add(new Tuple<>(Statement.MATHPARAMETER,operations.get(i).replace(" ","")));
        }
        repo.add(new Tuple<>(Statement.MATHPARAMETER,numbers.get(numbers.size()-1).replace(" ","")));

        repo.add(new Tuple<>(Statement.ENDMATH,null));
    }
}
