# SunshineWeather
## Demo演示
![image](https://github.com/busySnail/SunshineWeather/blob/master/gif/%E5%BD%95%E5%B1%8F.gif) 

## 介绍

### 技术栈 
####框架：MVP
####加载逻辑： RxJava+Retrofit+Gason+Picasso 
####界面：master+detail（ToolBar、RecyclerView、CardView）
####第三方API：[和风天气] (http://www.heweather.com/)

###踩过的坑
####1：和风天气并不支持所有城市的完整信息，比如大部分国外城市和和小部分国内城市获得的中缺少天气质量aqi部分，因此使用前要进行判空操作。
####2:VIew.GONE和View.INVISIBLE的的区别:都不可见，但前者界面不为控件保留占有的空间，而后者界面保留控件所占有的空间。有一步操作是在数           据为下载到本地jiang将ecyclerView设置为INVISIBLE，但是RecyclerViewde的getItemCount()又和数据相关，所以会报空指针异常，将                  INVISIBLE改为GONE就解决了。    
  
  
   

