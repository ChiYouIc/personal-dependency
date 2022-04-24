package cn.cy.dtc.core.handler;

import cn.cy.dtc.common.dto.DtpMainProp;
import cn.cy.dtc.common.dto.NotifyItem;
import cn.cy.dtc.common.em.NotifyTypeEnum;
import cn.cy.dtc.core.context.DtpContextHolder;
import cn.cy.dtc.core.notify.Notifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * @author: you
 * @date: 2022-04-23 19:53
 * @description: 通知处理器
 */
public class NotifierHandler {
    /**
     * 缓存通知器
     */
    private static final Map<String, Notifier> NOTIFIERS = new HashMap<>();

    private NotifierHandler() {
        ServiceLoader<Notifier> loader = ServiceLoader.load(Notifier.class);
        for (Notifier notifier : loader) {
            NOTIFIERS.put(notifier.platform(), notifier);
        }

//        Notifier dingNotifier = new DtpDingNotifier();
//        Notifier wechatNotifier = new DtpWechatNotifier();
//        NOTIFIERS.put(dingNotifier.platform(), dingNotifier);
//        NOTIFIERS.put(wechatNotifier.platform(), wechatNotifier);
    }

    /**
     * 发送通知
     *
     * @param prop
     * @param diffs
     */
    public void sendNotice(DtpMainProp prop, List<String> diffs) {

        try {
            NotifyItem notifyItem = DtpContextHolder.get().getNotifyItem();
            for (String platform : notifyItem.getPlatforms()) {
                Notifier notifier = NOTIFIERS.get(platform.toLowerCase());
                if (notifier != null) {
                    notifier.sendChangeMsg(prop, diffs);
                }
            }
        } finally {
            DtpContextHolder.remove();
        }
    }

    /**
     * 发送通知
     * <p>从当前 dtp 上下文中获取执行，并从中获取通知条目，进行消息发送
     *
     * @param typeEnum 通知类型
     */
    public void sendAlarm(NotifyTypeEnum typeEnum) {
        try {
            // 获取通知条目
            NotifyItem notifyItem = DtpContextHolder.get().getNotifyItem();

            // 发送推送消息
            for (String platform : notifyItem.getPlatforms()) {
                Notifier notifier = NOTIFIERS.get(platform.toLowerCase());
                if (notifier != null) {
                    notifier.sendAlarmMsg(typeEnum);
                }
            }
        } finally {
            // 推送消息发送成功之后，该 dtp 上文将移除
            DtpContextHolder.remove();
        }
    }

    public static NotifierHandler getInstance() {
        return NotifierHandlerHolder.INSTANCE;
    }

    /**
     * 通知处理程序持有人
     */
    private static class NotifierHandlerHolder {
        private static final NotifierHandler INSTANCE = new NotifierHandler();
    }
}
