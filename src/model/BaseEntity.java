package model;

import java.util.concurrent.atomic.AtomicLong;

public abstract class BaseEntity {
    Long id;
    private static final AtomicLong count = new AtomicLong(0);

    public BaseEntity() {
        this.id = count.incrementAndGet();
    }

    public BaseEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
