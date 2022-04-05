package cn.cy.log;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: you
 * @date: 2022-04-03 20:57
 * @description: Spel 表达式解析工具
 */
public class SpelUtils {

    public static final String REG = "\\{[\\#\\w\\'\\.]*[\\w]*\\}";

    private static Pattern pattern = Pattern.compile(REG);

    private static SpelExpressionParser parser = new SpelExpressionParser();

    /**
     * 将 msg 中的 Spel 表达式解析出来
     *
     * @param context 内容
     * @return 所有匹配项
     */
    public static List<String> parseExpression(String context) {
        List<String> list = new ArrayList<>();

        Matcher matcher = pattern.matcher(context);

        while (matcher.find()) {
            String group = matcher.group();
            list.add(group.substring(1, group.length() - 1));
        }
        return list;
    }

    /**
     * 解析 logRecord
     *
     * @param logRecord      日志记录
     * @param methodParamMap 方法参数映射
     * @return 日志
     */
    public static String parseContext(String logRecord, Map<String, Object> methodParamMap) {
        // 解析 Spel 表达式

        List<String> spelList = SpelUtils.parseExpression(logRecord);
        for (String spel : spelList) {

            // 获取参数
            int index = spel.indexOf(".");
            String rootObjectName = spel.substring(1, index == -1 ? spel.length() : index);
            Object rootObject = methodParamMap.get(rootObjectName);

            spel = spel.replace(rootObjectName, "root");

            Expression expression = parser.parseExpression(spel);
            String value = expression.getValue(rootObject, String.class);
            // 如果 value 为 null 则给定默认值 'null'
            value = Optional.ofNullable(value).orElse("null");

            if (value.isEmpty()) {
                value = "null";
            }

            logRecord = logRecord.replaceFirst(SpelUtils.REG, value);
        }

        return logRecord;
    }

}
