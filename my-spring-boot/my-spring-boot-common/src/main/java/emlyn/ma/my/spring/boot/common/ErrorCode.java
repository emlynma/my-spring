package emlyn.ma.my.spring.boot.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    SUCCESS("00000", "success"),

    // client error: about param, auth, frequency, version ...
    CLIENT_ERROR("A0000", "client error"),
    INVALID_PARAM("A0001", "invalid param"),
    AUTH_FAILED("A0002", "auth failed"),
    FREQUENCY_LIMIT("A0003", "frequency limit"),

    // server error: about business, feature, logic, data, idempotent ...
    SERVER_ERROR("B0000", "server error"),

    // third party error: about third party service, database, cache, mq ...
    THIRD_PARTY_ERROR("C0000", "third party error"),
    THIRD_SERVICE_ERROR("C1000", "third service error"),
    DATABASE_ERROR("C2000", "database error"),
    CACHE_ERROR("C3000", "cache error"),
    MESSAGE_QUEUE_ERROR("C4000", "message queue error"),

    UNKNOWN_ERROR("11111", "unknown error"),
    ;

    private final String code;
    private final String desc;

    @Override
    public String toString() {
        return "code: " + code + ", desc: " + desc;
    }

}
