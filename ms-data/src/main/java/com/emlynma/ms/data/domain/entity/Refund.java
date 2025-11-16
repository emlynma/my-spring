package com.emlynma.ms.data.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.emlynma.ms.core.base.ErrorCode;
import com.emlynma.ms.data.domain.enums.RefundStatusEnum;
import com.emlynma.ms.data.domain.enums.TradeTypeEnum;
import com.emlynma.ms.data.domain.model.RefundExtraInfo;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "refund", autoResultMap = true)
public class Refund {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String refundId;

    private String merchantId;
    private String outRefundId;

    private String originTradeId;
    private String originOutTradeId;

    private Long uid;

    private Long amount;
    private String currency;
    private TradeTypeEnum tradeType;

    private RefundStatusEnum status;

    private String errorCode;
    private String errorDesc;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private RefundExtraInfo extraInfo;

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
        return RefundStatusEnum.finalStatus(status);
    }
}
