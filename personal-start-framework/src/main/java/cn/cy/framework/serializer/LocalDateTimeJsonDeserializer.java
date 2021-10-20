package cn.cy.framework.serializer;

import cn.cy.common.util.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.SneakyThrows;

import java.time.LocalDateTime;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/12 13:58
 * @Description: 接受字符串类型的时间转成LocalDateTime
 */
public class LocalDateTimeJsonDeserializer extends JsonDeserializer<LocalDateTime> {

    @SneakyThrows
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext ctxt) {
        if (ObjectUtil.isNotEmpty(jsonParser) && StrUtil.isNotEmpty(jsonParser.getText())) {
            return DateUtil.stringToLocalDateTime(jsonParser.getText());
        } else {
            return null;
        }
    }

}
