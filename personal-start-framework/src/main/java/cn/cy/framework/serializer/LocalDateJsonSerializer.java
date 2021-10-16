package cn.cy.framework.serializer;

import cn.cy.framework.util.DateUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/12 13:52
 * @Description: 将LocalDate转成String返回到前台
 */
public class LocalDateJsonSerializer extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(LocalDate date, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (ObjectUtil.isEmpty(date)) {
            gen.writeString(StrUtil.EMPTY);
            return;
        }
        gen.writeString(DateUtil.formatLocalDateToString(date, DatePattern.NORM_DATE_PATTERN));
    }

}