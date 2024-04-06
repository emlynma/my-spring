package com.emlynma.spring.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CountryEnum {

    CN("CN", "CNY", "zh_CN", "zh", "Asia/Shanghai", "China"),
    US("US", "USD", "en_US", "en", "America/New_York", "United States"),
    UK("UK", "GBP", "en_GB", "en", "Europe/London", "United Kingdom"),
    JP("JP", "JPY", "ja_JP", "ja", "Asia/Tokyo", "Japan"),
    HK("HK", "HKD", "zh_HK", "zh", "Asia/Hong_Kong", "Hong Kong"),
    BR("BR", "BRL", "pt_BR", "pt", "America/Sao_Paulo", "Brazil"),
    MX("MX", "MXN", "es_MX", "es", "America/Mexico_City", "Mexico"),
    ;

    private final String country;
    private final String currency;
    private final String locale;
    private final String language;
    private final String timezone;
    private final String name;

}
