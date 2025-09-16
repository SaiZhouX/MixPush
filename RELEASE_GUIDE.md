# MixPush 发布指南

## 版本 2.4.0 发布说明

### 🆕 新功能
- **华为推送限制条数解决方案**: 添加了 `huaweiCategory` 配置，用于突破华为推送条数限制

### 📝 变更内容

#### 1. MixPushMessageConfig 类增强
- 新增 `huaweiCategory` 字段
- 新增 `getHuaweiCategory()` 和 `setHuaweiCategory()` 方法
- Builder 中新增 `huaweiCategory(String huaweiCategory)` 方法

#### 2. HuaweiPushProvider 类更新
- 在 `toMessage()` 方法中添加华为类别设置
- 支持通过 `.setCategory()` 传递华为消息类别

#### 3. 使用示例
```java
MixPushMessageConfig config = new MixPushMessageConfig.Builder()
    .huaweiCategory("MARKETING")  // 营销类消息
    .build();

MixPushMessageConfig imConfig = new MixPushMessageConfig.Builder()
    .huaweiCategory("VOIP")       // IM/通话类消息
    .build();
```

## 🚀 发布步骤

### 1. 准备工作
- [x] 代码修改完成
- [x] 版本号已更新到 2.4.0
- [x] gradle.properties 文件已创建

### 2. 构建和测试
```bash
# 构建项目
./gradlew clean build

# 运行测试
./gradlew test
```

### 3. 发布到 Maven Central
```bash
# 上传到 Sonatype OSSRH
./gradlew uploadArchives

# 或者使用新的发布插件
./gradlew publishToSonatype closeAndReleaseSonatypeStagingRepository
```

### 4. 配置要求
在发布前，需要在 `gradle.properties` 中配置以下信息：
- `signing.keyId`: GPG 签名密钥 ID
- `signing.password`: GPG 签名密码
- `signing.secretKeyRingFile`: GPG 密钥文件路径
- `OSSRH_USERNAME`: Sonatype OSSRH 用户名
- `OSSRH_PASSWORD`: Sonatype OSSRH 密码

### 5. 发布后更新
发布成功后，其他项目可以使用新版本：

```xml
<dependency>
    <groupId>io.github.mixpush</groupId>
    <artifactId>mixpush-sender</artifactId>
    <version>2.4.0</version>
</dependency>
```

## 📋 发布检查清单

- [x] 代码功能完整实现
- [x] 版本号已更新
- [x] 测试用例已更新
- [ ] 配置签名和发布凭据
- [ ] 执行构建和测试
- [ ] 发布到 Maven Central
- [ ] 验证发布成功
- [ ] 更新文档和示例

## 🔗 相关链接
- [华为推送官方文档](https://developer.huawei.com/consumer/cn/doc/HMSCore-Guides/message-restriction-description-0000001361648361)
- [Sonatype OSSRH 指南](https://central.sonatype.org/publish/publish-guide/)