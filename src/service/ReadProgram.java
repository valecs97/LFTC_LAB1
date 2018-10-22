package service;

import exceptions.NoMatchException;
import exceptions.ProgramException;
import model.*;
import repo.RepoInter;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ReadProgram {

    private final String identifier = "[a-zA-Z0-9]";
    private final String antiIdentifier = "[^a-zA-Z0-9]";


    private RepoInter<Tuple> repo;

    public ReadProgram(RepoInter<Tuple> repo) {
        this.repo = repo;
    }

    public void readCode(String code) throws ProgramException {
        code = replaceNewLine(code);
        code = checkProgram(code);
        String[] stmtlist = delimiter(code,";");
        for (Integer i=0;i<stmtlist.length;i++) {
            evaluateStatement(stmtlist[i],i+1);
        }
    }

    private void evaluateStatement(String stmt,Integer line) throws ProgramException {
        try {
            checkDecList(stmt);
            return;
        } catch (NoMatchException ignored) { }
        try {
            checkAssignment(stmt);
            return;
        } catch (NoMatchException ignored) {}
        try {
            checkIOStatement(stmt);
            return;
        } catch (NoMatchException ignored) {}
        throw new ProgramException("Invalid statement at line " + (line+1) + " !");
    }

    private void checkIOStatement(String stmt) throws NoMatchException {
        if (!stmt.matches(".*READ.*") && !stmt.matches(".*WRITE.*"))
            throw new NoMatchException();
        if (stmt.matches(".*READ.*")) {
            stmt = stmt.replaceAll(".*(READ " + identifier + "+).*", "$1");
            checkIOStatement(delimiter(stmt," ")[1],Statement.READ);
        }
    }

    private void checkIOValue(String value,Statement statement) throws ProgramException {
        if (value.toLowerCase().equals("true")) {
            repo.add(new Tuple<>(statement,  true));
            return;
        }
        else if (value.toLowerCase().equals("false")) {
            repo.add(new Tuple<>(statement,false));
            return;
        }
        else if (value.matches("\\d+")) {
            repo.add(new Tuple<>(statement, Integer.parseInt(value)));
            return;
        }
        else {
            try {
                repo.add(new Tuple<>(statement, Day.valueOf(value))));
                return;
            } catch (IllegalArgumentException ignored) {
            }
        }
        if (value.matches(identifier + "*")){
            repo.add(new Tuple<>(statement, value));
            return;
        }
        throw new ProgramException("Invalid term assignment !");
    }

    private void checkValue(String value,String identifier,Statement statement) throws ProgramException {
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
        throw new ProgramException("Invalid term assignment !");
    }

    private void checkAssignment(String stmt) throws ProgramException, NoMatchException {
        if (!stmt.matches(antiIdentifier + "*" + identifier + "+=" + identifier + "+" + antiIdentifier +"*"))
            throw new NoMatchException();
        String[] assignStr = delimiter(stmt, "=");
        String identifier = getIdentifier(assignStr[0]);
        String value = getIdentifier(assignStr[1]);
        checkValue(value,identifier,Statement.ASSIGNEMNT);
    }

    private void checkDecList(String stmt) throws ProgramException, NoMatchException {
        if (!stmt.matches(antiIdentifier + "*VAR.*"))
            throw new NoMatchException();
        List<Tuple<String,Type>> decList = new ArrayList<>();
        String[] decListStr = delimiter(stmt.replaceAll(antiIdentifier + "*VAR[^a-zA-Z0-9]",""),",");
        for (int i=0;i<decListStr.length;i++) {
            String[] decStr = delimiter(decListStr[i], " ");
            if (decStr.length != 2)
                throw new ProgramException("Declaration of variables is incorrect");
            String name; Type type;
            if (!decStr[0].matches(identifier))
                throw new ProgramException("Declaration of variables is incorrect");
            name = getIdentifier(decStr[0]);
            type = getType(decStr[1]);
            decList.add(new Tuple<>(-1L,name,type));
        }
        decList.forEach(t -> repo.add(new Tuple<>(Statement.DECLARATION,new Tuple<>(-1L,t.x,t.y))));
    }

    private String replaceNewLine(String code){
        return code.replace("\n","").replace("\r","");
    }

    private String checkProgram(String code) throws ProgramException {
        if (!code.matches("(^START)(.+)(END$)"))
            throw new ProgramException("Code is not valid !");
        return code.replaceAll("(^START)(.+)(END$)","$2");
    }

    private String[] delimiter(String code, String delimiter){
        return code.split(delimiter);
    }

    private String getIdentifier(String identifier){
        return identifier.replaceAll( antiIdentifier + "*("+identifier+"+)"+antiIdentifier + "*","$1").replaceAll(" ","");
    }

    private Type getType(String type){
        return Type.valueOf(type.replaceAll(antiIdentifier + "*("+identifier+"+)"+antiIdentifier + "*","$1"));
    }
}
