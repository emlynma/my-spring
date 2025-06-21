package com.emlynma.spring.data.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.emlynma.spring.core.BaseErrorCode;
import com.emlynma.spring.core.BaseException;
import com.emlynma.spring.core.util.CopyUtils;
import com.emlynma.spring.data.entity.User;
import com.emlynma.spring.data.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserMapper userMapper;

    /**
     * update condition
     */
    private LambdaUpdateWrapper<User> buildUpdateWrapper(User condition) {
        Assert.notNull(condition.getUid(), "sharding key(uid) must not be null");
        return new LambdaUpdateWrapper<User>()
                .eq(Objects.nonNull(condition.getId()), User::getId, condition.getId())
                .eq(Objects.nonNull(condition.getUid()), User::getUid, condition.getUid())
                ;
    }

    /**
     * select condition
     */
    private LambdaQueryWrapper<User> buildQueryWrapper(User condition) {
        Assert.notNull(condition.getUid(), "sharding key(uid) must not be null");
        return new LambdaQueryWrapper<User>()
                .eq(Objects.nonNull(condition.getId()), User::getId, condition.getId())
                .eq(Objects.nonNull(condition.getUid()), User::getUid, condition.getUid())
                .eq(Objects.nonNull(condition.getUname()), User::getUname, condition.getUname())
                .eq(Objects.nonNull(condition.getEmail()), User::getEmail, condition.getEmail())
                ;
    }

    /**
     * standard insert
     */
    public void insert(User entity) {
        int effect = userMapper.insert(entity);
        if (effect != 1) {
            throw new BaseException(BaseErrorCode.DATABASE_ERROR, "insert failed, entity: " + entity);
        }
    }

    /**
     * standard delete
     */
    public void delete(User entity) {
        int effect = userMapper.delete(buildQueryWrapper(entity));
        if (effect != 1) {
            throw new BaseException(BaseErrorCode.DATABASE_ERROR, "delete failed, entity: " + entity);
        }
    }

    /**
     * standard update
     */
    public void update(User entity, User update) {
        int effect = userMapper.update(update, buildUpdateWrapper(entity));
        if (effect != 1) {
            throw new BaseException(BaseErrorCode.DATABASE_ERROR, "update failed, entity: " + entity + ", update: " + update);
        }
        if (entity != update) {
            CopyUtils.copyNonNullProperties(update, entity, User.class);
        }
    }

    /**
     * standard select
     */
    public List<User> select(User condition) {
        return userMapper.selectList(buildQueryWrapper(condition));
    }

    /**
     * insert or update
     */
    public void save(User entity) {
        if (entity.getId() == null) {
            insert(entity);
        } else {
            update(entity, entity);
        }
    }

    /**
     * find by uid
     */
    public User findByUid(Long uid) {
        User condition = new User();
        condition.setUid(uid);
        return userMapper.selectOne(buildQueryWrapper(condition));
    }

    public User findByUname(String uname) {
        User condition = new User();
        condition.setUname(uname);
        return userMapper.selectOne(buildQueryWrapper(condition));
    }

    public User findByEmail(String email) {
        User condition = new User();
        condition.setEmail(email);
        return userMapper.selectOne(buildQueryWrapper(condition));
    }

}
