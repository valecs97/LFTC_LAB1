package repo;

import model.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class STRepo implements RepoInter<Entity> {
    private List<Entity> repo = new ArrayList<>();

    @Override
    public void add(Entity entity) {
        repo.add(entity);
    }

    @Override
    public Entity get(Long id) {
        Optional<Entity> res = repo.stream().filter(e -> e.getId()==id).findFirst();
        return res.isPresent() ? res.get() : null;
    }
}
