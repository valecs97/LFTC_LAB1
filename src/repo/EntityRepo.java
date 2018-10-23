package repo;

import model.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EntityRepo implements RepoInter<Entity> {
    private List<Entity> repo = new ArrayList<>();

    @Override
    public void add(Entity entity) {
        repo.add(entity);
    }

    @Override
    public Entity get(Long id) {
        return repo.stream().filter(e -> e.getId()==id).findFirst().orElse(null);
    }



    @Override
    public Entity get(Entity entity) {
        return repo.stream().filter(e -> e.getKey()==entity.getKey()).findFirst().orElse(null);
    }

    @Override
    public List<Entity> getAll() {
        return repo;
    }

    @Override
    public Integer getSize() {
        return repo.size();
    }
}
