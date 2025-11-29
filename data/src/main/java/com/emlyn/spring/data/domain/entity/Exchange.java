package com.emlyn.spring.data.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.emlyn.spring.common.contract.ExtraInfo;
import com.emlyn.spring.common.error.ErrorCode;
import com.emlyn.spring.data.domain.enums.ExchangeType;
import com.emlyn.spring.data.domain.enums.TradeType;
import com.emlyn.spring.data.domain.enums.status.ExchangeStatus;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "exchange", autoResultMap = true)
public class Exchange {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String tradeId;
    private String exchangeId;
    private String originExchangeId;

    private String exchangeRequest;
    private String exchangeResponse;

    private Long amount;
    private String currency;
    private TradeType tradeType;
    private ExchangeType exchangeType;

    private ExchangeStatus status;

    private String errorCode;
    private String errorDesc;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private ExtraInfo extraInfo;

    private Date createTime;
    private Date updateTime;
    private Date finishTime;

    private String lockId;
    private Integer version;

    public void setError(ErrorCode errorCode) {
        setErrorCode(errorCode.getCode());
        setErrorDesc(errorCode.getDesc());
    }

    public boolean isCompleted() {
        return ExchangeStatus.checkFinal(status);
    }

}
