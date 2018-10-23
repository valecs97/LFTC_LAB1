package repo;

import model.Statement;
import model.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StatementRepo implements RepoInter<Tuple> {
    private List<Tuple> repo = new ArrayList<>();

    @Override
    public void add(Tuple tuple) {
        repo.add(tuple);
    }

    @Override
    public Tuple get(Long id) {
        Optional<Tuple> t = repo.stream().filter(tuple -> tuple.getId() == id).findFirst();
        return t.orElse(null);
    }

    @Override
    public Tuple get(Tuple tuple) {
        Optional<Tuple> t = repo.stream().filter(item -> item.x == tuple.x).findFirst();
        return t.orElse(null);
    }

    @Override
    public List<Tuple> getAll(){
        return repo;
    }

    @Override
    public Integer getSize() {
        return repo.size();
    }
}
