package repo;

import model.Statement;
import model.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StatementRepo implements RepoInter<Tuple> {
    List<Tuple> repo = new ArrayList<>();

    @Override
    public void add(Tuple tuple) {
        repo.add(tuple);
    }

    @Override
    public Tuple get(Long id) {
        Optional<Tuple> t = repo.stream().filter(tuple -> tuple.getId() == id).findFirst();
        return t.isPresent() ? t.get() : null;
    }

    public List<Tuple> getAll(){
        return repo;
    }
}
