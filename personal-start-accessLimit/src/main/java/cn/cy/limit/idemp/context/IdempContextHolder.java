package cn.cy.limit.idemp.context;

/**
 * @Author: 友
 * @Date: 2022/5/26 18:42
 * @Description: 上下文助手
 */
public class IdempContextHolder {

    private static IdempContext context;

    public static void setContext(IdempContext c) {
        if (context == null) {
            context = c;
        }
    }

    public static String getKey() {
        return context.getKey();
    }

    public static int getTime() {
        return context.getTime();
    }
}
