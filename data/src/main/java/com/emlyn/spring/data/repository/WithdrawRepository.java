package com.emlyn.spring.data.repository;

import com.emlyn.spring.data.domain.entity.Withdraw;

import java.util.List;

public interface WithdrawRepository {

    void insert(Withdraw entity);

    void delete(Withdraw entity);

    void update(Withdraw entity, Withdraw update);

    List<Withdraw> select(Withdraw condition);

}
