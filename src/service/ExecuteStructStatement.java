package service;

import model.Entity;
import model.Tuple;
import repo.RepoInter;

public class ExecuteStructStatement {

    RepoInter<Tuple> stmtRepo;
    RepoInter<Tuple> pif,st,st_c;

    public ExecuteStructStatement(RepoInter<Tuple> stmtRepo, RepoInter<Tuple> pif, RepoInter<Tuple> st, RepoInter<Tuple> st_c) {
        this.stmtRepo = stmtRepo;
        this.pif = pif;
        this.st = st;
        this.st_c = st_c;
    }

    public Integer executeStatement(Integer start){


        return start;
    }
}
