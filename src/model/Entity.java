package model;

public class Entity extends BaseEntity {
    Integer key,value;

    public Entity(Integer key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public Entity(Long id, Integer key, Integer value) {
        super(id);
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }
}
