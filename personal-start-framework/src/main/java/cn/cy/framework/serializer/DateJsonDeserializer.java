package cn.cy.framework.serializer;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Date;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/12 13:50
 * @Description: 接受字符串类型的时间转成Date
 */
public class DateJsonDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        if (ObjectUtil.isNotEmpty(jsonParser) && StrUtil.isNotEmpty(jsonParser.getText())) {
            return DateUtil.parse(jsonParser.getText());
        } else {
            return null;
        }
    }

}
