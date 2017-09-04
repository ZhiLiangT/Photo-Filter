# Photo-Filter
关于图片处理的App
其中包括对图片色调的处理，以及图片的滤镜（分为GPUImage滤镜和自己定义的滤镜矩阵），以及画笔功能（提供清除，选择画笔颜色和选择画笔大小等功能），选择本地文件夹进行保存
<br>
### 以下是主要的功能

## 色调

选择色调，提供了RGB三种色调进行调整，通过左右滑动进行选择
<br>

![色调](https://github.com/ZhiLiangT/Photo-Filter/raw/master/img/photo_filter_6.png  "首页")<br>

## 自定义滤镜

自定义滤镜，提供了24种滤镜可供选择

![自定义滤镜](https://github.com/ZhiLiangT/Photo-Filter/raw/master/img/photo_filter_4.png "首页")<br>

## GPUImage滤镜

GPUImage框架的提供滤镜，概框架本来是IOS的项目，现在也开发了Android版，有超过100中的滤镜效果，其中选了23种，有兴趣的可以去该项目的github连接去看<br>
https://github.com/CyberAgent/android-gpuimage
![GPUImage滤镜](https://github.com/ZhiLiangT/Photo-Filter/raw/master/img/photo_filter_5.png  "首页")<br>

## 颜色选择器

颜色选择器，选择一种颜色给画笔，用的也是一个开源的框架，右边还可以选择画笔的粗细，这里就不多演示了
链接：https://github.com/LarsWerkman/HoloColorPicker

![画笔颜色选择器](https://github.com/ZhiLiangT/Photo-Filter/raw/master/img/photo_filter_3.png "首页")<br>

## 画笔

画笔模块，暂时功能有 清除轨迹，选择画笔，选择橡皮擦，选择画笔颜色和选择画笔粗细（后期 待添加撤销和反撤销）

![画笔模块](https://github.com/ZhiLiangT/Photo-Filter/raw/master/img/photo_filter_2.png  "首页")<br>

## 保存

点击保存弹出Dialog 选择文件夹，对当前的图片进行保存到指定文件夹的操作。

![文件夹选择器](https://github.com/ZhiLiangT/Photo-Filter/raw/master/img/photo_filter_1.png "首页")<br>

以上就是所有的功能
