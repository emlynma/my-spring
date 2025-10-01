package com.emlynma.ms.data.component.sharding;

import com.emlynma.ms.core.util.HashUtils;
import org.apache.shardingsphere.infra.algorithm.core.exception.AlgorithmInitializationException;
import org.apache.shardingsphere.infra.exception.core.ShardingSpherePreconditions;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class TradeShardingAlgorithm implements ComplexKeysShardingAlgorithm<Comparable<?>> {

    private static final String SHARDING_COUNT_KEY = "shardingCount";

    private static final String TRADE_ID_COLUMN_NAME = "trade_id";
    private static final String OUT_TRADE_ID_COLUMN_NAME = "out_trade_id";

    private int shardingCount;

    @Override
    public void init(final Properties props) {
        shardingCount = getShardingCount(props);
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<Comparable<?>> shardingValue) {
        Set<String> shardingValues = getShardingValues(shardingValue);
        return shardingValues.stream()
                .map(this::calculateSharding)
                .map(sharding -> shardingValue.getLogicTableName() + "_" + sharding)
                .collect(Collectors.toList());
    }

    private int getShardingCount(Properties props) {
        ShardingSpherePreconditions.checkContainsKey(props, SHARDING_COUNT_KEY, () -> new AlgorithmInitializationException(this, "Sharding count cannot be null."));
        int result = Integer.parseInt(String.valueOf(props.getProperty(SHARDING_COUNT_KEY)));
        ShardingSpherePreconditions.checkState(result > 0, () -> new AlgorithmInitializationException(this, "Sharding count must be a positive integer."));
        return result;
    }

    private Set<String> getShardingValues(ComplexKeysShardingValue<Comparable<?>> shardingValue) {
        Set<String> shardingValues = new HashSet<>();
        Optional.ofNullable(shardingValue.getColumnNameAndShardingValuesMap())
                .map(map -> map.get(TRADE_ID_COLUMN_NAME))
                .orElse(Collections.emptySet())
                .stream()
                .map(String::valueOf)
                .filter(StringUtils::hasText)
                .forEach(shardingValues::add);
        if (!shardingValues.isEmpty()) {
            return shardingValues;
        }
        Optional.ofNullable(shardingValue.getColumnNameAndShardingValuesMap())
                .map(map -> map.get(OUT_TRADE_ID_COLUMN_NAME))
                .orElse(Collections.emptySet())
                .stream()
                .map(String::valueOf)
                .filter(StringUtils::hasText)
                .map(HashUtils::crc32)
                .forEach(shardingValues::add);
        if (!shardingValues.isEmpty()) {
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
