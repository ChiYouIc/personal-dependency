package cn.cy.framework.serializer;

import cn.cy.framework.util.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.SneakyThrows;

import java.time.LocalDate;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/12 13:51
 * @Description: 接受字符串类型的时间转成LocalDate
 */
public class LocalDateJsonDeserializer extends JsonDeserializer<LocalDate> {

    @SneakyThrows
    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext ctxt) {
        if (ObjectUtil.isNotEmpty(jsonParser) && StrUtil.isNotEmpty(jsonParser.getText())) {
            return DateUtil.stringToLocalDate(jsonParser.getText());
        } else {
            return null;
        }
    }

}
