package com.emlynma.ms.data.lock;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LockParams {

    private String key;

    private String value;

    private Long expire;

}
