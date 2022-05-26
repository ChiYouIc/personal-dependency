package cn.cy.limit.idemp.context;

import lombok.Builder;
import lombok.Getter;

/**
 * @Author: 友
 * @Date: 2022/5/26 18:41
 * @Description: 幂等性上下文
 */
@Getter
@Builder
public class IdempContext {

    private final String key;

    private final int time;

}
