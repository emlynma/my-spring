package com.emlyn.spring.data.repository;

import com.emlyn.spring.data.domain.entity.Exchange;

import java.util.List;

public interface ExchangeRepository {

    void insert(Exchange entity);

    void delete(Exchange entity);

    void update(Exchange entity, Exchange update);

    List<Exchange> select(Exchange condition);

}
