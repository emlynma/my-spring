package com.emlyn.spring.data.repository;

import com.emlyn.spring.data.domain.entity.Payment;

import java.util.List;

public interface PaymentRepository {

    void insert(Payment entity);

    void delete(Payment entity);

    void update(Payment entity, Payment update);

    List<Payment> select(Payment condition);

}
