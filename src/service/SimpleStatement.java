package service;

import exceptions.CompilerException;
import exceptions.NoMatchException;
import model.Day;
import model.Statement;
import model.Tuple;
import model.Type;
import repo.RepoInter;

import java.util.ArrayList;
import java.util.List;

public class SimpleStatement {
    public final String identifier = "[a-zA-Z0-9]{1,8}";
    private final String antiIdentifier = "[^a-zA-Z0-9]";

    private RepoInter<Tuple> repo;

    public SimpleStatement(RepoInter<Tuple> repo) {
        this.repo = repo;
    }

    public boolean checkSimpleStatement(String stmt) throws CompilerException {
        try {
            checkDecList(stmt);
            return true;
        } catch (NoMatchException ignored) { }
        try {
            checkAssignment(stmt);
            return true;
        } catch (NoMatchException ignored) {}
        try {
            checkIOStatement(stmt);
            return true;
        } catch (NoMatchException ignored) {}
        return false;
    }

    public void checkIOStatement(String stmt) throws NoMatchException, CompilerException {
        if (!stmt.matches(".*READ.*") && !stmt.matches(".*WRITE.*"))
            throw new NoMatchException();
        if (stmt.matches(".*READ.*")) {
            stmt = stmt.replaceAll(".*(READ " + identifier + "+).*", "$1");
            if (delimiter(stmt," ").length !=2)
                throw new CompilerException("Compiler error ! Read command has to be followed by only one identifier/constant !");
            checkIOValue(delimiter(stmt," ")[1], Statement.READ,true);
        } else{
            stmt = stmt.replaceAll(".*(WRITE " + identifier + "+).*", "$1");
            if (delimiter(stmt," ").length !=2)
                throw new CompilerException("Compiler error ! Write command has to be followed by only one identifier/constant !");
            checkIOValue(delimiter(stmt," ")[1], Statement.WRITE, false);
        }
    }

    public void checkIOValue(String value,Statement statement,Boolean onlyIdentifier) throws CompilerException {
        if (value.toLowerCase().equals("true")) {
            if (onlyIdentifier) throw new CompilerException("Error ! Only identifier allowed when reading !");
            repo.add(new Tuple<>(statement,  true));
            return;
        }
        else if (value.toLowerCase().equals("false")) {
            if (onlyIdentifier) throw new CompilerException("Error ! Only identifier allowed when reading !");
            repo.add(new Tuple<>(statement,false));
            return;
        }
        else if (value.matches("\\d+")) {
            if (onlyIdentifier) throw new CompilerException("Error ! Only identifier allowed when reading !");
            repo.add(new Tuple<>(statement, Integer.parseInt(value)));
            return;
        }
        else {
            try {
                Day.valueOf(value);
                if (onlyIdentifier) throw new CompilerException("Error ! Only identifier allowed when reading !");
                repo.add(new Tuple<>(statement, Day.valueOf(value)));
                return;
            } catch (IllegalArgumentException ignored) {
            }
        }
        if (value.matches(identifier)){
            repo.add(new Tuple<>(statement, value));
            return;
        }
        throw new CompilerException("Invalid term assignment !");
    }

    public void checkValue(String value,String identifier,Statement statement) throws CompilerException {
        if (value.toLowerCase().equals("true")) {
            repo.add(new Tuple<>(statement, new Tuple<>(-1L, identifier, true)));
            return;
        }
        else if (value.toLowerCase().equals("false")) {
            repo.add(new Tuple<>(statement, new Tuple<>(-1L, identifier, false)));
            return;
        }
        else if (value.matches("\\d+")) {
            repo.add(new Tuple<>(statement, new Tuple<>(-1L, identifier, Integer.parseInt(value))));
            return;
        }
        else {
            try {
                repo.add(new Tuple<>(statement, new Tuple<>(-1L, identifier, Day.valueOf(value))));
                return;
            } catch (IllegalArgumentException ignored) {
            }
        }
        throw new CompilerException("Invalid term assignment !");
    }

    public void checkAssignment(String stmt) throws CompilerException, NoMatchException {
        if (!stmt.matches(antiIdentifier + "*" + identifier + "=" + identifier + "+" + antiIdentifier +"*"))
            throw new NoMatchException();
        String[] assignStr = delimiter(stmt, "=");
        String identifier = getIdentifier(assignStr[0]);
        String value = getIdentifier(assignStr[1]);
        checkValue(value,identifier,Statement.ASSIGNEMNT);
    }

    public void checkDecList(String stmt) throws CompilerException, NoMatchException {
        if (!stmt.matches(antiIdentifier + "*VAR.*"))
            throw new NoMatchException();
        List<Tuple<String,Type>> decList = new ArrayList<>();
        String[] decListStr = delimiter(stmt.replaceAll(antiIdentifier + "*VAR[^a-zA-Z0-9]",""),",");
        for (int i=0;i<decListStr.length;i++) {
            String[] decStr = delimiter(decListStr[i], " ");
            if (decStr.length != 2)
                throw new CompilerException("Declaration of variables is incorrect");
            String name; Type type;
            if (!decStr[0].matches(identifier))
                throw new CompilerException("Declaration of variables is incorrect");
            name = getIdentifier(decStr[0]);
            type = getType(decStr[1]);
            decList.add(new Tuple<>(-1L,name,type));
        }
        repo.add(new Tuple<>(Statement.DECLARATIONSTART,null));
        decList.forEach(t -> repo.add(new Tuple<>(Statement.DECLARATION,new Tuple<>(-1L,t.x,t.y))));
        repo.add(new Tuple<>(Statement.DECLARATIONEND,null));
    }

    public String replaceNewLine(String code){
        return code.replace("\n","").replace("\r","");
    }

    public String checkProgram(String code) throws CompilerException {
        if (!code.matches("(^START)(.+)(END$)"))
            throw new CompilerException("Code is not valid !");
        return code.replaceAll("(^START)(.+)(END$)","$2");
    }

    public String[] delimiter(String code, String delimiter){
        return code.split(delimiter);
    }

    public String getIdentifier(String identifier){
        return identifier.replaceAll( antiIdentifier + "*("+identifier+")"+antiIdentifier + "*","$1").replaceAll(" ","");
    }

    public Type getType(String type){
        return Type.valueOf(type.replaceAll(antiIdentifier + "*("+identifier+")"+antiIdentifier + "*","$1"));
    }
}
