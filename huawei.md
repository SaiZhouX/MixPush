��Ϊ����Ҫ��Ϣ���࣬����������������������ƣ�����û����+��
�ο��ٷ���ַ��https://developer.huawei.com/consumer/cn/doc/HMSCore-Guides/message-restriction-description-0000001361648361

������MixPushMessageConfig������������

public class MixPushMessageConfig {
    private String huaweiCategory;//��Ϊ��Ϣ���࣬����������������������ƣ�����û����+��
        
    public String getHuaweiCategory() {
        return huaweiCategory;
    }

    public void setHuaweiCategory(String huaweiCategory) {
        this.huaweiCategory = huaweiCategory;
    }
}
MixPushMessageConfig.Builder���У�����һ������

public Builder huaweiCategory(String huaweiCategory) {
    config.huaweiCategory = huaweiCategory;
    return this;
}
��HuaweiPushProvider��toMessage�����д��뻪Ϊ����

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
                .setType(1) // 1���û��Զ�������Ϊ2���������ض�url3��������Ӧ��App4�������򿪸�ý����Ϣ
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
                .setCategory(mixPushMessage.getConfig().getHuaweiCategory())//֪ͨ���
//                .setBiTag("the_sample_bi_tag_for_receipt_service")
                .setNotification(androidNotification)
                .build();

        return Message.builder()
                .setNotification(notification)
                .setAndroidConfig(androidConfig);
    }
�����õ�ʱ���뻪Ϊ���

MixPushMessageConfig activitiesMessageConfig = new MixPushMessageConfig.Builder()
        // OPPO �����ڡ�ͨ������ �� �½�ͨ����ģ���еǼ�ͨ�������ڷ�����Ϣʱѡ��
        .oppoPushChannelId(oppoChannelId)
        .huaweiPushChannelId(huaweiChannelId)
        .huaweiCategory(huaweiCategory)//��Ϊ����
        .channelId(channelId)

        .vivoSystemMessage(true)//vivo��Ϣ���� 0����Ӫ����Ϣ��1��ϵͳ����Ϣ  ��Ӫ��Ϣ�����û�һ��ֻ�ܷ���5����ϵͳ��Ϣû������
        .build();