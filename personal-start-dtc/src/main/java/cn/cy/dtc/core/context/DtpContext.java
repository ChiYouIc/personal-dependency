package cn.cy.dtc.core.context;

import cn.cy.dtc.common.dto.AlarmInfo;
import cn.cy.dtc.common.dto.NotifyItem;
import cn.cy.dtc.common.dto.NotifyPlatform;
import cn.cy.dtc.core.thread.DtpExecutor;
import cn.hutool.core.collection.CollUtil;
import lombok.Builder;
import lombok.Data;
import lombok.val;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

/**
 * @author: you
 * @date: 2022-04-23 19:46
 * @description: 动态线程池上下文
 */
@Data
@Builder
public class DtpContext {

    /**
     * 执行器
     */
    private DtpExecutor dtpExecutor;

    /**
     * 通知平台
     */
    private List<NotifyPlatform> platforms;

    /**
     * 通知条目
     */
    private NotifyItem notifyItem;

    /**
     * 通知消息
     */
    private AlarmInfo alarmInfo;

    public NotifyPlatform getPlatform(String platform) {
        if (CollUtil.isEmpty(platforms)) {
            return null;
        }
        val map = platforms.stream().collect(toMap(x -> x.getPlatform().toLowerCase(), Function.identity(), (v1, v2) -> v2));
        return map.get(platform.toLowerCase());
    }
}
