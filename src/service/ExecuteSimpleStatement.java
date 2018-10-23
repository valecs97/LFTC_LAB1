package service;

import exceptions.ExecutionException;
import model.Entity;
import model.Statement;
import model.Tuple;
import repo.RepoInter;

public class ExecuteSimpleStatement {

    RepoInter<Entity> st,st_c;

    public ExecuteSimpleStatement(RepoInter<Entity> st, RepoInter<Entity> st_c) {
        this.st = st;
        this.st_c = st_c;
    }

    public void executeStatement(Tuple stmt){
        if (stmt.x == Statement.DECLARATION)
            executeDeclaration(stmt);
        else if (stmt.x == Statement.ASSIGNEMNT)
            executeAssignment(stmt);
        else if (stmt.x == Statement.READ)
            executeRead(stmt);
        else if (stmt.x == Statement.WRITE)
            executeWrite(stmt);
        else
            throw new ExecutionException("Invalid statement !");
    }

    private void executeDeclaration(Tuple stmt){

    }

    private void executeAssignment(Tuple stmt){

    }

    private void executeRead(Tuple stmt){

    }

    private void executeWrite(Tuple stmt){

    }
}
