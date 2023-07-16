/**
 * @api {DESC}  证书认证模块使用说明
 * @apiName 证书认证模块使用说明
 * @apiGroup 1.License（描述）
 * @apiDescription License认证模块使用说明
 * @apiParamExample {text}说明:
   使用证书验证，需要增加license-verify-spring-boot-starter模块依赖，在需要证书验证的接口上
   增加@VLicense注解，模块系统中需要增加相应的证书配置，验证模块中需要放公钥和证书文件
 */
 
/**
 * @api {DESC}  App测试说明
 * @apiName App测试说明
 * @apiGroup 1.License（描述）
 * @apiDescription License组件App测试说明
 * @apiParamExample {html} 说明:
 
- > ### 测试证书生成（Creator）

配置文件启用creator模式配置且pom.xml文件引入license-creator-spring-boot-starter包

spring.profiles.active = creator

<dependency>
    <groupId>com.appleyk</groupId>
    <artifactId>license-creator-spring-boot-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>

详细配置请查看application-creator.properties文件


- > ### 测试证书验证（Verify）

配置文件启用creator模式配置且pom.xml文件引入license-verify-spring-boot-starter包

spring.profiles.active = verify

<dependency>
    <groupId>com.appleyk</groupId>
    <artifactId>license-verify-spring-boot-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>

详细配置请查看application-verify.properties文件
  
*/ 
 
 
/**
 * @api {DESC}  自定义属性验证使用说明
 * @apiName 自定义属性验证使用说明
 * @apiGroup 1.License（描述）
 * @apiDescription License组件自定义属性验证说明
 * @apiParamExample {html} 说明:
  使用License组件自定义属性验证功能需要继承AGxCustomVerifyListener类，
  并且实现verify方法，使用@Component注解，验证成功需要返回true

  public abstract boolean verify(LicenseExtraParam licenseExtra) throws Exception;

  详细可参考TestACustomVerifyListener文件示例
 */
 

/**
 * @api {POST} /license/generate 2 生成许可证书 
 * @apiName generate
 * @apiGroup 2.License （接口）
 * @apiDescription 开发或市场人员通过填写业务系统软件许可证书的注册信息，来生成相应的lic；注册成功后，可将返回结果中的licUrl的地址，复制到浏览器中进行下载,
 即将服务器生成好的软件许可证书liccense.lic文件下载到本地
 * @apiParam {String} subject 证书名称，非空
 * @apiParam {String} privateAlias 私钥别名，非空
 * @apiParam {String} keyPass 私钥密码，非空
 * @apiParam {String} storePass 私钥库密码，非空
 * @apiParam {String} licensePath 证书生成地址，非空
 * @apiParam {String} issuedTime 授权日期，非空
 * @apiParam {String} expiryTime 证书失效日期，非空
 * @apiParam {String} consumerType 授权用户类型（默认1）
 * @apiParam {String} consumerAmount 授权用户数量
 * @apiParam {String} description 证书描述信息
 * @apiParam {Object} licenseCheck 证书额外验证信息对象
 * @apiParam {bool} licenseCheck.ipCheck 是否验证ip地址列表，非空
 * @apiParam {List} licenseCheck.ipAddress 可被允许的ip地址列表
 * @apiParam {bool} licenseCheck.macCheck 是否验证mac地址列表
 * @apiParam {List} licenseCheck.macAddress 可被允许的mac地址列表
 * @apiParam {bool} licenseCheck.isCpuCheck 是否验证cpu序列号
 * @apiParam {String} licenseCheck.cpuSerial 可被允许的cpu序列号
 * @apiParam {bool} licenseCheck.isBoardCheck 是否验证主板号
 * @apiParam {String} licenseCheck.mainBoardSerial 可被允许的主板序列号
 * @apiParam {bool} licenseCheck.registerCheck 是否验证注册人数
 * @apiParam {List} licenseCheck.registerAmount 可被允许的最大注册人数限制
 * @apiParam {String} licUrl 许可证书的（服务器）下载地址
 * @apiVersion v0.2.1
 * @apiParamExample 生成许可证书:
   /license/generate
 * @apiParamExample params-Json:
 {
    "subject": "landi",
    "privateAlias": "privateKeys",
    "keyPass": "123456a",
    "storePass": "123456a",
    "privateKeysStorePath": "/privateKeys.store",
    "issuedTime": "2020-05-01 08:30:00",
    "expiryTime": "2021-05-01 08:30:00",
    "description": "系统软件许可证书",
    "licenseCheck": {
        "ipAddress": [
            "192.168.1.2",
            "2408:8221:1d:bbd0:ad77:446e:4904:a776",
            "2408:8221:1d:bbd0:71b6:d1b0:39c6:3c4e",
            "192.168.145.1",
            "192.168.239.1"
        ],
        "macAddress": [
            "64-FB-81-6F-0E-C2",
            "00-50-56-C0-00-08",
            "00-50-56-C0-00-01"
        ],
        "cpuSerial": "BFEBFBFF000206D7",
        "mainBoardSerial": "MB-201706282017",
        "registerAmount": 1000,
        "macCheck": false,
        "boardCheck": false,
        "cpuCheck": false,
        "ipCheck": false,
        "registerCheck": true
    }
}
 * @apiSuccessExample {json} Success-Response:
 {
    "status": 200,
    "message": "证书生成成功，证书有效期：2020-05-01 08:30:00 - 2021-05-01 08:30:00",
    "data": {
        "subject": "landi",
        "privateAlias": "privateKeys",
        "keyPass": "123456a",
        "storePass": "123456a",
        "licensePath": "/data/license/20200509134009/license.lic",
        "privateKeysStorePath": "/privateKeys.store",
        "issuedTime": "2020-05-01 08:30:00",
        "expiryTime": "2021-05-01 08:30:00",
        "consumerType": "user",
        "consumerAmount": 1,
        "description": "系统软件许可证书",
        "licenseCheck": {
            "ipAddress": [
                "192.168.1.2",
                "2408:8221:1d:bbd0:ad77:446e:4904:a776",
                "2408:8221:1d:bbd0:71b6:d1b0:39c6:3c4e",
                "192.168.145.1",
                "192.168.239.1"
            ],
            "macAddress": [
                "64-FB-81-6F-0E-C2",
                "00-50-56-C0-00-08",
                "00-50-56-C0-00-01"
            ],
            "cpuSerial": "BFEBFBFF000206D7",
            "mainBoardSerial": "MB-201706282017",
            "registerAmount": 1000,
            "registerCheck": true,
            "cpuCheck": false,
            "boardCheck": false,
            "ipCheck": false,
            "macCheck": false
        },
        "licUrl": "http://127.0.0.1:8080/license/download?path=/data/license/20200509134009/license.lic"
    },
    "timestamp": "2020-08-22 12:51:09"
}
 *
 */

/**
 * @api {GET} /license/getServerInfos 1 获取硬件信息
 * @apiName getServerInfos
 * @apiGroup 2.License （接口）
 * @apiVersion v0.2.1
 * @apiDescription 部署人员通过该接口，可以获取到系统所在的部署目标服务器（集群）的硬件信息，通过将该信息提供给开发或市场人员，可以由开发或市场人员根据实际情况进行相应的lic的注册
 * @apiParam {String} osName 系统类型（选填，不填的话，程序字段获取）
 * @apiParamExample 获取硬件信息:
  /license/getServerInfos
 * @apiParamExample params-Json:
  {
      "osName": "linux"
  }
 * @apiSuccessExample {json} Success-Response:
 {
    "status": 200,
    "message": "成功",
    "data": {
        "ipAddress": [
            "192.168.1.103"
        ],
        "macAddress": [
            "00-50-56-BD-54-BE"
        ],
        "cpuSerial": "564d90d1-baff-8747-80ef-1c32318df424",
        "mainBoardSerial": "Not",
        "registerAmount": null,
        "registerCheck": false,
        "cpuCheck": false,
        "boardCheck": false,
        "ipCheck": false,
        "macCheck": false
    },
    "timestamp": "2020-08-22 12:53:34"
}
 */


/**
 * @api {GET} /license/download 3 下载许可证书
 * @apiName download
 * @apiGroup 2.License （接口）
 * @apiVersion v0.2.1
 * @apiParam {String} path 证书所在路径
 * @apiDescription 可以直接调用该接口，也可以直接通过证书生成返回的licUrl信息，将其复制到浏览器中进行下载
 * @apiParamExample 下载证书:
  http://127.0.0.1:8080/license/download?path=/home/license/20200509134009/license.lic

 * 
 */