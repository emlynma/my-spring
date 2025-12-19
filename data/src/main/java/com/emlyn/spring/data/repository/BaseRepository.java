package com.emlyn.spring.data.repository;

import java.util.List;

public interface BaseRepository<Entity> {

    void insert(Entity entity);

    void delete(Entity entity);

    void update(Entity entity, Entity update);

    List<Entity> select(Entity condition);

    Entity selectOne(Entity condition);

}
