package service;

import model.Entity;
import model.Tuple;
import repo.RepoInter;

public class ExecuteStructStatement {

    RepoInter<Tuple> stmtRepo;
    RepoInter<Entity> st,st_c;

    public ExecuteStructStatement(RepoInter<Tuple> stmtRepo, RepoInter<Entity> st, RepoInter<Entity> st_c) {
        this.stmtRepo = stmtRepo;
        this.st = st;
        this.st_c = st_c;
    }

    public Integer executeStatement(Integer start){


        return start;
    }
}
