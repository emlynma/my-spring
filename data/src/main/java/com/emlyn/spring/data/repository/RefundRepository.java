package com.emlyn.spring.data.repository;

import com.emlyn.spring.data.domain.entity.Refund;

import java.util.List;

public interface RefundRepository {

    void insert(Refund entity);

    void delete(Refund entity);

    void update(Refund entity, Refund update);

    List<Refund> select(Refund condition);

}
