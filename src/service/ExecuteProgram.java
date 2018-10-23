package service;

import exceptions.ExecutionException;
import model.Entity;
import model.Statement;
import model.Tuple;
import repo.RepoInter;
import repo.StatementRepo;

import java.util.concurrent.atomic.AtomicLong;

public class ExecuteProgram {
    RepoInter<Tuple> stmtRepo;
    RepoInter<Tuple> pif,st,st_c;

    ExecuteSimpleStatement execSimple;
    ExecuteStructStatement execStruct;

    public static AtomicLong pifCount = new AtomicLong(0);

    public ExecuteProgram(RepoInter<Tuple> stmtRepo,RepoInter<Tuple> pif, RepoInter<Tuple> st, RepoInter<Tuple> st_c) {
        this.stmtRepo = stmtRepo;
        this.st = st;
        this.st_c = st_c;
        this.pif = pif;

        execSimple = new ExecuteSimpleStatement(stmtRepo,pif,st,st_c);
        execStruct = new ExecuteStructStatement(stmtRepo,pif,st,st_c);
    }

    public void startExecution() throws ExecutionException {
        for (int i=0;i<stmtRepo.getSize();i++){
            if (checkSimpleStatement(stmtRepo.get((long)i)))
                execSimple.executeStatement(i);
            else
                i=execStruct.executeStatement(i);
        }
    }

    private boolean checkSimpleStatement(Tuple stmt){
        return stmt.x == Statement.DECLARATIONSTART || stmt.x == Statement.ASSIGNEMNT || stmt.x == Statement.READ || stmt.x == Statement.WRITE;
    }
}
