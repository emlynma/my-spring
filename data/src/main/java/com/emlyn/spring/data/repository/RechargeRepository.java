package com.emlyn.spring.data.repository;

import com.emlyn.spring.data.domain.entity.Recharge;

import java.util.List;

public interface RechargeRepository {

    void insert(Recharge entity);

    void delete(Recharge entity);

    void update(Recharge entity, Recharge update);

    List<Recharge> select(Recharge condition);

}
