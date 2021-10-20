package cn.cy.framework.serializer;

import cn.cy.common.util.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/12 13:58
 * @Description: 将LocalDate转成String返回到前台
 */
public class LocalDateTimeJsonSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime date, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (ObjectUtil.isEmpty(date)) {
            gen.writeString(StrUtil.EMPTY);
            return;
        }
        gen.writeString(DateUtil.formatLocalDateTimeToString(date));
    }

}
