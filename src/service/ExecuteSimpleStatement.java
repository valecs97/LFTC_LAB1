package service;

import exceptions.ExecutionException;
import model.Codification;
import model.Entity;
import model.Statement;
import model.Tuple;
import repo.RepoInter;

public class ExecuteSimpleStatement {

    RepoInter<Tuple> stmtRepo;
    RepoInter<Tuple> pif,st,st_c;



    public ExecuteSimpleStatement(RepoInter<Tuple> stmtRepo, RepoInter<Tuple> pif, RepoInter<Tuple> st, RepoInter<Tuple> st_c) {
        this.stmtRepo = stmtRepo;
        this.pif = pif;
        this.st = st;
        this.st_c = st_c;
    }

    public Integer executeStatement(Integer i) throws ExecutionException {
        if (stmtRepo.get((long) i).x == Statement.DECLARATIONSTART)
            i = executeDeclaration(i);
        else if (stmtRepo.get((long) i).x == Statement.ASSIGNEMNT)
            executeAssignment(stmtRepo.get((long) i));
        else if (stmtRepo.get((long) i).x == Statement.READ)
            executeRead(stmtRepo.get((long) i));
        else if (stmtRepo.get((long) i).x == Statement.WRITE)
            executeWrite(stmtRepo.get((long) i));
        else
            throw new ExecutionException("Invalid statement !");
        return i;
    }

    private Integer executeDeclaration(Integer i){
        pif.add(new Tuple<>(Codification.var,-1));
        i++;
        boolean ok = false;
        while (stmtRepo.get((long)i).x != Statement.DECLARATIONEND){
            if (ok)
                pif.add(new Tuple<>(Codification.comma,-1));
            Tuple dec = (Tuple) stmtRepo.get((long)i).y;
            pif.add(new Tuple<>(Codification.identifier,ExecuteProgram.pifCount.get()));
            st.add(new Tuple<>(dec.x,ExecuteProgram.pifCount.incrementAndGet()));
            switch ((String) dec.y){
                case "INT":
                    pif.add(new Tuple<>(Codification.intWord,-1));
                case "BOOLEAN":
                    pif.add(new Tuple<>(Codification.booleanWord,-1));
                case "DAY":
                    pif.add(new Tuple<>(Codification.day,-1));
            }
            i++;
            ok = true;
        }

        pif.add(new Tuple<>(Codification.pointComma,-1));
        return i;
    }

    private void executeAssignment(Tuple stmt){

        pif.add(new Tuple<>(Codification.equal,-1));

        pif.add(new Tuple<>(Codification.pointComma,-1));
    }

    private void executeRead(Tuple stmt){

    }

    private void executeWrite(Tuple stmt){

    }
}
