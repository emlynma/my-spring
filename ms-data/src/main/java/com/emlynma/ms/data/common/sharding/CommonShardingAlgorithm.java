package com.emlynma.ms.data.common.sharding;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

@Slf4j
public class CommonShardingAlgorithm implements StandardShardingAlgorithm<Comparable<?>> {

    private int tableCount;

    @Override
    public void init(final Properties props) {
        if (props == null) return;
        tableCount = Integer.parseInt(props.getProperty("tableCount", "2"));
    }

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Comparable<?>> shardingValue) {
        // 根据分表数量计算需要截取几位
        int digits = calculateDigits(tableCount);
        // 分表值
        String value = String.valueOf(shardingValue.getValue());
        // 截取后缀
        String tail = value.substring(Math.max(0, value.length() - digits));
        // 后缀取模
        int index = Integer.parseInt(tail) % tableCount;
        // 实际表名
        return shardingValue.getLogicTableName() + "_" + index;
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Comparable<?>> rangeShardingValue) {
        return List.of();
    }

    private static int calculateDigits(int count) {
        int digits = 1;
        while ((count = (count / 10)) > 0) {
            digits ++;
        }
        return digits;
    }

}
