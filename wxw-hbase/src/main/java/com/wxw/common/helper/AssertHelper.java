package com.wxw.common.helper;

import com.wxw.common.exception.ExceptionMessage;
import com.wxw.common.exception.RRException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.client.Put;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author weixiaowei
 * @desc: 参数统一处理
 * @date: 2021/7/3
 */
@Slf4j
public class AssertHelper {

    /**
     * 批量验证参数
     */
    public static void notNullValues(String message, Object... value) {

        if (StringUtils.isBlank(message) || value == null) {
            throw new RRException(ExceptionMessage.ILLEGAL_PARAMS + message);
        }
        for (Object param : value) {
            if (param == null) {
                log.error("方法入参：{}", Arrays.toString(value));
                throw new RRException(ExceptionMessage.ILLEGAL_PARAMS + message);
            }

            if (param instanceof Collection && CollectionUtils.isEmpty((Collection<?>) param)){
                log.error("方法入参：{}", Arrays.toString(value));
                throw new RRException(ExceptionMessage.ILLEGAL_PARAMS + message);
            }
        }
    }
}
