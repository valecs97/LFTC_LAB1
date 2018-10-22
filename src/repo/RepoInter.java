package repo;

import model.BaseEntity;

public interface RepoInter<T extends BaseEntity> {
    void add(T t);
    T get(Long id);
}
