package cn.cy.sso.model;

import java.util.StringJoiner;

/**
 * @author: 开水白菜
 * @description: Sso-Server 返回的数据格式
 * @create: 2021-08-15 23:41
 **/
public class SsoResult {

    private Result result;
    private SsoUser userInfo;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public SsoUser getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(SsoUser userInfo) {
        this.userInfo = userInfo;
    }

    public boolean isSuccess() {
        return Result.SUCCESS.equals(this.result);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SsoResult.class.getSimpleName() + "[", "]")
                .add("result='" + result + "'")
                .add("userInfo=" + userInfo)
                .toString();
    }

    public enum Result {
        SUCCESS,
        FAIL
    }
}
