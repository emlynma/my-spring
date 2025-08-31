package com.emlynma.ms.data.common.sharding;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

@Slf4j
public class StringSuffixModShardingAlgorithm implements StandardShardingAlgorithm<Comparable<?>> {

    @Override
    public void init(final Properties props) {
        if (props == null) return;
    }

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Comparable<?>> shardingValue) {
        // 分表数量
        int count = collection.size();
        // 截取长度
        int length = length(count);
        // 分表值
        String value = String.valueOf(shardingValue.getValue());
        String tail = value.substring(Math.max(0, value.length() - length));
        int index = Integer.parseInt(tail) % count;
        return shardingValue.getLogicTableName() + "_" + index;
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Comparable<?>> rangeShardingValue) {
        return List.of();
    }

    private static int length(int count) {
        int length = 1;
        while ((count = (count / 10)) > 0) {
            length ++;
        }
        return length;
    }

}
