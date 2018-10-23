package service;

import model.Entity;
import model.Statement;
import model.Tuple;
import repo.RepoInter;
import repo.StatementRepo;

public class ExecuteProgram {
    RepoInter<Tuple> stmtRepo;
    RepoInter<Entity> st,st_c;

    ExecuteSimpleStatement execSimple;
    ExecuteStructStatement execStruct;

    public ExecuteProgram(RepoInter<Tuple> stmtRepo, RepoInter<Entity> st, RepoInter<Entity> st_c) {
        this.stmtRepo = stmtRepo;
        this.st = st;
        this.st_c = st_c;

        execSimple = new ExecuteSimpleStatement(st,st_c);
        execStruct = new ExecuteStructStatement(stmtRepo,st,st_c);
    }

    public void startExecution(){
        for (int i=0;i<stmtRepo.getSize();i++){
            if (checkSimpleStatement(stmtRepo.get((long)i)))
                execSimple.executeStatement(stmtRepo.get((long) i));
            else
                i=execStruct.executeStatement(i);
        }
    }

    private boolean checkSimpleStatement(Tuple stmt){
        return stmt.x == Statement.DECLARATION || stmt.x == Statement.ASSIGNEMNT || stmt.x == Statement.READ || stmt.x == Statement.WRITE;
    }
}
