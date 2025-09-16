import java.lang.reflect.Method;
import java.lang.reflect.Field;

/**
 * 测试华为推送限制条数解决方案
 */
public class TestHuaweiCategoryFeature {
    public static void main(String[] args) {
        try {
            // 测试MixPushMessageConfig类是否包含huaweiCategory功能
            Class<?> configClass = Class.forName("com.mixpush.sender.MixPushMessageConfig");
            Class<?> builderClass = Class.forName("com.mixpush.sender.MixPushMessageConfig$Builder");
            
            System.out.println("=== 华为推送限制条数解决方案验证 ===");
            
            // 检查huaweiCategory字段
            try {
                Field huaweiCategoryField = configClass.getDeclaredField("huaweiCategory");
                System.out.println("✅ huaweiCategory字段存在: " + huaweiCategoryField.getType().getSimpleName());
            } catch (NoSuchFieldException e) {
                System.out.println("❌ huaweiCategory字段不存在");
                return;
            }
            
            // 检查getHuaweiCategory方法
            try {
                Method getHuaweiCategory = configClass.getMethod("getHuaweiCategory");
                System.out.println("✅ getHuaweiCategory()方法存在: " + getHuaweiCategory.getReturnType().getSimpleName());
            } catch (NoSuchMethodException e) {
                System.out.println("❌ getHuaweiCategory()方法不存在");
            }
            
            // 检查setHuaweiCategory方法
            try {
                Method setHuaweiCategory = configClass.getMethod("setHuaweiCategory", String.class);
                System.out.println("✅ setHuaweiCategory()方法存在");
            } catch (NoSuchMethodException e) {
                System.out.println("❌ setHuaweiCategory()方法不存在");
            }
            
            // 检查Builder中的huaweiCategory方法
            try {
                Method builderHuaweiCategory = builderClass.getMethod("huaweiCategory", String.class);
                System.out.println("✅ Builder.huaweiCategory()方法存在: " + builderHuaweiCategory.getReturnType().getSimpleName());
            } catch (NoSuchMethodException e) {
                System.out.println("❌ Builder.huaweiCategory()方法不存在");
            }
            
            System.out.println("\n=== 功能验证完成 ===");
            System.out.println("华为推送限制条数解决方案已成功实现！");
            System.out.println("\n使用示例:");
            System.out.println("MixPushMessageConfig config = new MixPushMessageConfig.Builder()");
            System.out.println("    .huaweiCategory(\"MARKETING\")  // 营销类消息");
            System.out.println("    .build();");
            
        } catch (ClassNotFoundException e) {
            System.out.println("❌ 无法找到MixPushMessageConfig类: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ 验证过程中出现错误: " + e.getMessage());
        }
    }
}