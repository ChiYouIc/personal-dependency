package cn.cy.web.enums;

/**
 * @Author: 友叔
 * @Date: 2020/11/26 21:23
 * @Description: 常用的API操作码，用来表示请求响应状态以及提示消息
 */
public enum ResultCode implements IErrorCode {
    /**
     * 常用的API操作码
     */
    SUCCESS(200, "操作成功."),
    FAILED(500, "操作失败."),
    VALIDATE_FAILED(404, "参数检验失败."),
    UNAUTHORIZED(401, "暂未登录或token已经过期."),
    FORBIDDEN(403, "没有相关权限.");

    private final long code;
    private final String message;

    ResultCode(final long code, final String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Long getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
