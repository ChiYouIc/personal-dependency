package cn.cy.framework.serializer;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/12 13:51
 * @Description: 将Date转成String返回到前台
 */
public class DateJsonSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (ObjectUtil.isEmpty(date)) {
            gen.writeString(StrUtil.EMPTY);
            return;
        }
        gen.writeString(DateUtil.format(date, DatePattern.NORM_DATETIME_PATTERN));
    }

}