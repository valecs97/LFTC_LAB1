package repo;

import model.BaseEntity;

import java.util.List;

public interface RepoInter<T extends BaseEntity> {
    void add(T t);
    T get(Long id);
    T get(T t);
    List<T> getAll();
    Integer getSize();
}
