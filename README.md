[![](https://jitpack.io/v/coolfire2015/RxFluxArchitecture.svg)](https://jitpack.io/#coolfire2015/RxFluxArchitecture)
[![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=19)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e3864a8c7c9b4f768702376e665b1d44)](https://app.codacy.com/app/coolfire2015/RxFluxArchitecture?utm_source=github.com&utm_medium=referral&utm_content=coolfire2015/RxFluxArchitecture&utm_campaign=Badge_Grade_Dashboard)
[![Build Status](https://travis-ci.org/coolfire2015/RxFluxArchitecture.svg?branch=master)](https://travis-ci.org/coolfire2015/RxFluxArchitecture)

**相关系列文章**

[模块化解耦框架RxFluxArchitecture1-框架简介](https://juejin.im/post/5cdc1038f265da037b613589)

[模块化解耦框架RxFluxArchitecture2-基本功能实现](https://juejin.im/post/5cd92ce1f265da03a436efd5)

[模块化解耦框架RxFluxArchitecture3-订阅管理绑定生命周期](https://juejin.im/post/5cda6f4e518825796d63ec58)

[模块化解耦框架RxFluxArchitecture4-依赖库与依赖注入](https://juejin.im/post/5cdcfb996fb9a0321855760e)

[模块化解耦框架RxFluxArchitecture5-Application多模块共存](https://juejin.im/post/5cd6cada6fb9a03218556de2)

## Demo下载地址
![下载](image/下载.png)

## 效果图
![效果图](image/效果图/Fragment切换.gif)
![效果图](image/效果图/屏幕旋转.gif)


![效果图](image/效果图/状态加载-操作成功.gif)
![效果图](image/效果图/状态加载-取消操作.gif)

![效果图](image/效果图/操作重试.gif)

## 依赖框架
### 核心
- 依赖注入 [**Dagger+Dagger.Android**](https://github.com/google/dagger)
- 函数编程 [**RxJava**](https://github.com/ReactiveX/RxJava)
+[**RxAndroid**](https://github.com/ReactiveX/RxAndroid)
- AndroidX [**X-Core**](https://mvnrepository.com/artifact/androidx.core/core)
+[**X-AppCompat**](https://mvnrepository.com/artifact/androidx.appcompat/appcompat)
+[**X-Lifecycle**](https://mvnrepository.com/artifact/androidx.lifecycle/lifecycle-extensions)
- AndroidX迁移 [**Jetifier**](https://mvnrepository.com/artifact/com.android.tools.build.jetifier/jetifier-core)

### 通用
- 模块路由 [**Arouter**](https://github.com/alibaba/ARouter)
- View注入 [**ButterKnife**](https://github.com/JakeWharton/butterknife)
- 网络框架 [**OkHttp**](https://github.com/square/okhttp)
+[**OkIo**](https://github.com/square/okio)
+[**Retrofit**](https://github.com/square/retrofit)
- 图片加载 [**Glide**](https://github.com/bumptech/glide)
- 日志展示 [**Logger**](https://github.com/orhanobut/logger)
- 数据解析 [**Fastjson**](https://github.com/alibaba/fastjson)
+[**Gson**](https://github.com/google/gson)
- 内存泄漏 [**LeakCanary**](https://github.com/square/leakcanary)
- 通用适配 [**BaseRecyclerViewAdapterHelper**](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
- AndroidX [**X-Multidex**](https://mvnrepository.com/artifact/androidx.multidex/multidex)
+[**X-Fragment**](https://mvnrepository.com/artifact/androidx.fragment/fragment)
+[**X-CardView**](https://mvnrepository.com/artifact/androidx.cardview/cardview)
+[**X-RecyclerView**](https://mvnrepository.com/artifact/androidx.recyclerview/recyclerview)
+[**X-Material**](https://mvnrepository.com/artifact/com.google.android.material/material)
+[**X-Collection**](https://mvnrepository.com/artifact/androidx.collection/collection)
+[**X-ConstraintLayout**](https://mvnrepository.com/artifact/androidx.constraintlayout/constraintlayout)

### 测试 
- [**Junit**](https://github.com/junit-team/junit4)
- [**Mockito**](https://github.com/mockito/mockito)
- [**RESTMock**](https://github.com/andrzejchm/RESTMock)
- [**Powermock**](https://github.com/powermock/powermock)
- [**Robolectric**](https://github.com/robolectric/robolectric)
- [**Daggermock**](https://github.com/fabioCollini/DaggerMock)
- [**X-Test-Core**](https://mvnrepository.com/artifact/androidx.test/core)
- [**X-Test-Junit**](https://mvnrepository.com/artifact/androidx.test.ext/junit)
- [**X-Test-Rules**](https://mvnrepository.com/artifact/androidx.test/rules)
- [**X-Test-Runner**](https://mvnrepository.com/artifact/androidx.test/runner)
- [**X-Test-Espresso**](https://mvnrepository.com/artifact/androidx.test.espresso/espresso-core)
- [**X-Test-Fragment**](https://mvnrepository.com/artifact/androidx.fragment/fragment-testing)

## License
Copyright 2019 liujunfeng

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
