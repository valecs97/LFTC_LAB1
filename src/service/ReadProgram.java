package service;

import exceptions.NoMatchException;
import exceptions.CompilerException;
import model.*;
import repo.RepoInter;

import java.util.ArrayList;
import java.util.List;

public class ReadProgram {



    private RepoInter<Tuple> repo;
    private SimpleStatement simplestmt;
    private StructStatement structstmt;

    public ReadProgram(RepoInter<Tuple> repo) {
        this.repo = repo;
        simplestmt = new SimpleStatement(repo);
        structstmt = new StructStatement(repo);
    }

    public void readCode(String code) throws CompilerException {
        code = simplestmt.replaceNewLine(code);
        code = simplestmt.checkProgram(code).toUpperCase();
        String[] stmtlist = simplestmt.delimiter(code,";");
        for (Integer i=0;i<stmtlist.length;i++) {
            evaluateStatement(stmtlist[i],i+1);
        }
    }

    private void evaluateStatement(String stmt,Integer line) throws CompilerException {
        if (simplestmt.checkSimpleStatement(stmt))
            return;
        if (structstmt.checkStructStatement(stmt))
            return;
        throw new CompilerException("Invalid statement at line " + (line+1) + " !");
    }


}
