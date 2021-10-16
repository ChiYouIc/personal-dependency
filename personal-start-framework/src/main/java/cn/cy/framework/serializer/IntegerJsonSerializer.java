package cn.cy.framework.serializer;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/12 13:49
 * @Description: 将Integer转成String 返回前台
 */
public class IntegerJsonSerializer extends JsonSerializer<Integer> {
    @Override
    public void serialize(Integer type, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (ObjectUtil.isEmpty(type)) {
            gen.writeString(StrUtil.EMPTY);
            return;
        }
        gen.writeString(type.toString());
    }
}
