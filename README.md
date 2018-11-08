# MVPBaseApplication
[TOC]

###简介
用于开始新项目进行开发。后续用的不多。
### 集成库

### MVP框架包含内容
#### 基类
主要用于使用MVP框架进行搭建项目，MVP主要包含以下
* IBaseView 包含初始化Presenter的方法
* IBasePresenter 包含绑定View和解绑View的方法
* BaseActivity 初始化Presenter,将View和Presenter进行绑定和解绑
* BaseFragment 初始化Presenter,将View和Presenter进行绑定和解绑

#### Template
使用MVP架构方式需要每次都创建关联类，这里使用了Android Studio的模板进行创建。
> 1. 将Template文件夹下的MVPActivity文件夹拷贝置Applications/Android Studio.app/Contents/plugins/android/lib/templates/activities/

> 2. 将Template文件夹下的MVPFragment文件夹拷贝置Applications/Android Studio.app/Contents/plugins/android/lib/templates/other