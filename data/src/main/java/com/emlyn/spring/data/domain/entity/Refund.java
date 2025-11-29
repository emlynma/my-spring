package com.emlyn.spring.data.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.emlyn.spring.common.contract.ExtraInfo;
import com.emlyn.spring.common.error.ErrorCode;
import com.emlyn.spring.data.domain.enums.TradeType;
import com.emlyn.spring.data.domain.enums.status.RefundStatus;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "refund", autoResultMap = true)
public class Refund {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String tradeId;

    private String merchantId;
    private String outTradeId;

    private String originTradeId;
    private String originOutTradeId;

    private String exchangeId;

    private Long uid;

    private Long amount;
    private String currency;
    private TradeType tradeType;

    private RefundStatus status;

    private String errorCode;
    private String errorDesc;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private ExtraInfo extraInfo;

    private String attach;
    private String notifyUrl;

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
        return RefundStatus.checkFinal(status);
    }

}
