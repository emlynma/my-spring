package com.emlynma.ms.data.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.emlynma.ms.core.base.ErrorCode;
import com.emlynma.ms.data.domain.enums.TradeBizStatusEnum;
import com.emlynma.ms.data.domain.enums.TradeStatusEnum;
import com.emlynma.ms.data.domain.enums.TradeTypeEnum;
import com.emlynma.ms.data.domain.model.TradeExtraInfo;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "trade", autoResultMap = true)
public class Trade {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String tradeId;

    private String merchantId;
    private String outTradeId;

    private Long uid;

    private Long amount;
    private String currency;
    private TradeTypeEnum tradeType;

    private TradeStatusEnum status;
    private TradeBizStatusEnum bizStatus;

    private String errorCode;
    private String errorDesc;

    private String paymentId;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private TradeExtraInfo extraInfo;

    private String subject;
    private String attach;
    private String notifyUrl;

    private Date createTime;
    private Date updateTime;
    private Date finishTime;
    private Date expireTime;

    private String lockId;
    private Integer version;

    public void setError(ErrorCode errorCode) {
        setErrorCode(errorCode.getCode());
        setErrorDesc(errorCode.getDesc());
    }

    public boolean isCompleted() {
        return TradeStatusEnum.finalStatus(status);
    }

}
