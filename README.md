# license
软件许可证书生成+验证

作者：appleyk

博客地址：https://blog.csdn.net/appleyk

本篇博客地址：https://blog.csdn.net/Appleyk/article/details/101530203

## license.app

软件许可应用（WebApp，主要用于引入license模块，便于测试）

1、首先要用KeyTool工具来生成密钥库：（-alias别名 –validity 3650表示10年有效）
keytool -genkey -alias privatekeys -keysize 1024 -keystore privateKeys.store -validity 3650

2、然后将密钥库中名称为‘privatekeys’的证书条目导出到证书文件certfile.cer中：
keytool -export -alias privatekeys -file certfile.cer -keystore privateKeys.store

3、然后再把这个证书文件的信息导入到公钥库中别名为publiccert的证书条目中：
keytool -import -alias publiccert -file certfile.cer -keystore publicCerts.store

最后生成的文件privateKeys.store和publicCerts.store拷贝出来备用。

application.properties文件中添加如下配置：

##License相关配置
 证书名称
- > springboot.license.verify.subject=landi

公钥别名
- > springboot.license.verify.publicAlias=publiccert

公钥库所在的位置
- > springboot.license.verify.publicKeysStorePath=/publicCerts.store 

公钥库访问密码
- > springboot.license.verify.storePass=123456a

证书生成的临时位置（如果参数不指定，则由程序自动进行处理）
- > springboot.license.generate.temp.dir=D:/app/licenses/

证书所在系统中的的位置（默认放在classes下面）
- > springboot.license.verify.licensePath =classpath:license.lic 


============License相关配置=============

生成证书的请求参数（json）：

```json
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
```

============License相关配置=============

### license.api 
证书接口文档

### license.app 
证书应用App

：可打包成war（默认war）或jar，进行部署

### license.core 
证书核心模块


### license.creator 
证书生成模块

：也包括lic文件下载接口

### license.verify 
证书验证模块

：内置lic文件变动定时检测功能，如业务系统需要用到，只需要在SpringBoot启动类上，添加@EnableScheduling注解即可

：如需在接口上开启License验证功能，只需要在对应的API方法上添加@VLicense注解即可

### 效果图 

![图1](https://i.postimg.cc/wBz3ZLHM/11.png)


![图2](https://i.postimg.cc/G2V4MNpw/12.png)


![图3](https://i.postimg.cc/jqnwWs8s/13.png)


![图4](https://i.postimg.cc/bwpsF1qJ/14.png)
