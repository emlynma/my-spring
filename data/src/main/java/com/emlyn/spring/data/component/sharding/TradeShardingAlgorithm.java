package com.emlyn.spring.data.component.sharding;

import com.emlyn.spring.common.util.HashUtils;
import org.apache.shardingsphere.infra.algorithm.core.exception.AlgorithmInitializationException;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class TradeShardingAlgorithm implements ComplexKeysShardingAlgorithm<String> {

    private static final String SHARDING_COUNT_KEY = "shardingCount";

    private static final String TRADE_ID = "trade_id";
    private static final String OUT_TRADE_ID = "out_trade_id";

    private int shardingCount;

    @Override
    public void init(final Properties props) {
        shardingCount = Integer.parseInt(props.getProperty(SHARDING_COUNT_KEY));
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<String> shardingValue) {
        return getShardingValues(shardingValue).stream()
                .map(this::calculateSharding)
                .map(sharding -> shardingValue.getLogicTableName() + "_" + sharding)
                .collect(Collectors.toList());
    }

    private Set<String> getShardingValues(ComplexKeysShardingValue<String> shardingValue) {
        Map<String, Collection<String>> map = shardingValue.getColumnNameAndShardingValuesMap();
        Set<String> shardingValues = new HashSet<>();
        if (map.containsKey(TRADE_ID)) {
            map.get(TRADE_ID).stream().filter(StringUtils::hasText).forEach(shardingValues::add);
            return shardingValues;
        }
        if (map.containsKey(OUT_TRADE_ID)) {
            map.get(OUT_TRADE_ID).stream().filter(StringUtils::hasText).map(HashUtils::crc32).forEach(shardingValues::add);
            return shardingValues;
        }
        throw new AlgorithmInitializationException(this, "Sharding value can not be found.");
    }

    private int calculateSharding(String value) {
        int digits = calculateDigits(shardingCount);
        String tail = value.substring(Math.max(0, value.length() - digits));
        return Integer.parseInt(tail) % shardingCount;
    }

    private int calculateDigits(int count) {
        int digits = 1;
        while ((count = (count / 10)) > 0) {
            digits ++;
        }
        return digits;
    }
}
