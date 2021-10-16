package cn.cy.security.enums;

/**
 * @Author: 友叔
 * @Date: 2020/12/31 15:08
 * @Description: 登陆状态
 */
public enum LoginStatus {
    // 登陆成功
    LOGIN_SUCCESS("LOGIN_SUCCESS", 1) {
        @Override
        public boolean isLoginSuccess() {
            return true;
        }
    },
    // 退出登陆
    LOGOUT("LOGOUT", 2) {
        @Override
        public boolean isLogout() {
            return true;
        }
    },
    // 登陆失败
    LOGIN_FAIL("LOGIN_FAIL", 0) {
        @Override
        public boolean isLoginFail() {
            return true;
        }
    };

    /**
     * 状态描述
     */
    private final String statusMessage;

    private final Integer statusCode;

    LoginStatus(final String statusMessage, final Integer statusCode) {
        this.statusMessage = statusMessage;
        this.statusCode = statusCode;
    }

    public boolean isLoginSuccess() {
        return false;
    }

    public boolean isLogout() {
        return false;
    }

    public boolean isLoginFail() {
        return false;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}
