package cn.cy.web.response;

import cn.cy.framework.ResponseType;
import lombok.*;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/4 15:09
 * @Description: 成功返回API
 */
@Setter
@Getter
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse<T> implements ResponseType {

    private static final long serialVersionUID = 1L;
    /**
     * http 状态码
     */
    @Builder.Default
    private int code = 200;
    /**
     * 结果集返回
     */
    private T data;
}
