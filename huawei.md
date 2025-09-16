华为必须要消息分类，如果不分类推送条数有限制，并且没声音+震动
参考官方地址：https://developer.huawei.com/consumer/cn/doc/HMSCore-Guides/message-restriction-description-0000001361648361

首先在MixPushMessageConfig类中增加属性

public class MixPushMessageConfig {
    private String huaweiCategory;//华为消息分类，如果不分类推送条数有限制，并且没声音+震动
        
    public String getHuaweiCategory() {
        return huaweiCategory;
    }

    public void setHuaweiCategory(String huaweiCategory) {
        this.huaweiCategory = huaweiCategory;
    }
}
MixPushMessageConfig.Builder类中，增加一个方法

public Builder huaweiCategory(String huaweiCategory) {
    config.huaweiCategory = huaweiCategory;
    return this;
}
在HuaweiPushProvider类toMessage方法中传入华为分类

   private Message.Builder toMessage(MixPushMessage mixPushMessage) {
        Notification notification = Notification.builder().setTitle(mixPushMessage.getTitle())
                .setBody(mixPushMessage.getDescription())
                .build();

        //// mixpush://com.mixpush.huawei/message?payload=%7b%22url%22%3a%22http%3a%2f%2fsoso.com%22%7d
        String url = "mixpush://com.mixpush.huawei/message?";
        if (!mixPushMessage.isJustOpenApp()) {
            try {
                url += "payload=" + URLEncoder.encode(mixPushMessage.getPayload(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

//        LightSettings lightSettings = LightSettings.builder().setColor(Color.builder().setAlpha(0f).setRed(0f).setBlue(1f).setGreen(1f).build())
//                .setLightOnDuration("3.5")
//                .setLightOffDuration("5S")
//                .build();
        ClickAction clickAction = ClickAction.builder()
                .setType(1) // 1：用户自定义点击行为2：点击后打开特定url3：点击后打开应用App4：点击后打开富媒体信息
                .setIntent(url)
                .build();

        AndroidNotification androidNotification = AndroidNotification.builder()
                .setClickAction(clickAction)
//                .setBodyLocKey("M.String.body")
//                .addBodyLocArgs("boy").addBodyLocArgs("dog")
//                .setTitleLocKey("M.String.title")
//                .addTitleLocArgs("Girl").addTitleLocArgs("Cat")
                .setChannelId(mixPushMessage.getConfig().getChannelId())
//                .setNotifySummary("some summary")
//                .setMultiLangkey(JSON.parseObject(mixPushMessage.getPayload()))
//                .setStyle(1)
                .setBigTitle(mixPushMessage.getTitle())
                .setBigBody(mixPushMessage.getDescription())
//                .setAutoClear(86400000)
//                .setNotifyId(486)
//                .setGroup("Group1")
//                .setImportance(Importance.LOW.getValue())
//                .setLightSettings(lightSettings)
//                .setBadge(BadgeNotification.builder().setAddNum(1).setBadgeClass("Classic").build())
//                .setVisibility(Visibility.PUBLIC.getValue())
                .setForegroundShow(true)
                .build();

        AndroidConfig androidConfig = AndroidConfig.builder()
//                .setCollapseKey(-1)
//                .setUrgency(Urgency.HIGH.getValue())
                .setTtl((mixPushMessage.getConfig().getTimeToLive() / 1000) + "s")
                .setCategory(mixPushMessage.getConfig().getHuaweiCategory())//通知类别
//                .setBiTag("the_sample_bi_tag_for_receipt_service")
                .setNotification(androidNotification)
                .build();

        return Message.builder()
                .setNotification(notification)
                .setAndroidConfig(androidConfig);
    }
最后掉用的时候传入华为类别

MixPushMessageConfig activitiesMessageConfig = new MixPushMessageConfig.Builder()
        // OPPO 必须在“通道配置 → 新建通道”模块中登记通道，再在发送消息时选择
        .oppoPushChannelId(oppoChannelId)
        .huaweiPushChannelId(huaweiChannelId)
        .huaweiCategory(huaweiCategory)//华为分类
        .channelId(channelId)

        .vivoSystemMessage(true)//vivo消息类型 0：运营类消息，1：系统类消息  运营消息单个用户一天只能发送5条，系统消息没有限制
        .build();