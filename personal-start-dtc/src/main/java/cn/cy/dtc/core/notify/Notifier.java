package cn.cy.dtc.core.notify;

import cn.cy.dtc.common.dto.DtpMainProp;
import cn.cy.dtc.common.em.NotifyTypeEnum;

import java.util.List;

/**
 * 消息通知行为
 *
 * @author: you
 * @date: 2022-04-23 16:32
 * @description: 通知行为
 */
public interface Notifier {
    /**
     * 获取发送平台的名称
     *
     * @return 平台名称
     */
    String platform();

    /**
     * 发送通知消息
     *
     * @param oldProp old properties
     * @param diffs   the changed keys
     */
    void sendChangeMsg(DtpMainProp oldProp, List<String> diffs);

    /**
     * 发送报警消息
     *
     * @param typeEnum 通知类型
     */
    void sendAlarmMsg(NotifyTypeEnum typeEnum);
}
