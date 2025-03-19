package com.emlynma.spring.data.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.emlynma.spring.core.BaseErrorCode;
import com.emlynma.spring.core.BaseException;
import com.emlynma.spring.core.util.CopyUtils;
import com.emlynma.spring.data.entity.Auth;
import com.emlynma.spring.data.entity.User;
import com.emlynma.spring.data.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class AuthRepository {

    private final AuthMapper authMapper;

    private LambdaUpdateWrapper<Auth> buildUpdateWrapper(Auth condition) {
        Assert.notNull(condition.getUid(), "sharding key(uid) must not be null");
        return new LambdaUpdateWrapper<Auth>()
                .eq(Objects.nonNull(condition.getId()), Auth::getId, condition.getId())
                .eq(Objects.nonNull(condition.getUid()), Auth::getUid, condition.getUid())
                ;
    }

    private LambdaQueryWrapper<Auth> buildQueryWrapper(Auth condition) {
        Assert.notNull(condition.getUid(), "sharding key(uid) must not be null");
        return new LambdaQueryWrapper<Auth>()
                .eq(Objects.nonNull(condition.getId()), Auth::getId, condition.getId())
                .eq(Objects.nonNull(condition.getUid()), Auth::getUid, condition.getUid())
                .eq(Objects.nonNull(condition.getType()), Auth::getType, condition.getType())
                .eq(Objects.nonNull(condition.getIdentifier()), Auth::getIdentifier, condition.getIdentifier())
                ;
    }

    public void insert(Auth entity) {
        int effect = authMapper.insert(entity);
        if (effect != 0) {
            throw new BaseException(BaseErrorCode.DATABASE_ERROR, "insert failed, entity: " + entity);
        }
    }

    public void delete(Auth entity) {
        int effect = authMapper.delete(buildQueryWrapper(entity));
        if (effect != 1) {
            throw new BaseException(BaseErrorCode.DATABASE_ERROR, "delete failed, entity: " + entity);
        }
    }

    public void update(Auth entity, Auth update) {
        int effect = authMapper.update(update, buildUpdateWrapper(entity));
        if (effect != 1) {
            throw new BaseException(BaseErrorCode.DATABASE_ERROR, "update failed, entity: " + entity + ", update: " + update);
        }
        if (entity != update) {
            CopyUtils.copyNonNullProperties(update, entity, User.class);
        }
    }

    public List<Auth> select(Auth condition) {
        return authMapper.selectList(buildQueryWrapper(condition));
    }

    public void save(Auth entity) {
        if (entity.getId() == null) {
            insert(entity);
        } else {
            update(entity, entity);
        }
    }

    public Auth findByUid(Long uid) {
        Auth condition = new Auth();
        condition.setUid(uid);
        return authMapper.selectOne(buildQueryWrapper(condition));
    }

}
