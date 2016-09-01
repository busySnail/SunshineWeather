package com.busysnail.sunshineweather;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;


import java.io.Closeable;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Util {





    /**
     * 只关注是否联网
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static String dayForWeek(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = 0;
        String week = "";
        dayForWeek = c.get(Calendar.DAY_OF_WEEK);
        switch (dayForWeek) {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }
        return week;
    }

    public static int getIconResourceForWeatherCondition(int weatherId) {
        // Based on weather code data found at:
        // http://bugs.openweathermap.org/projects/api/wiki/Weather_Condition_Codes
        if (weatherId >= 300 && weatherId <= 303 || weatherId>=310 &&weatherId<=312 ) {
            return R.drawable.ic_storm;
        } else if (weatherId >= 305 && weatherId <= 306) {
            return R.drawable.ic_light_rain;
        } else if (weatherId ==307) {
            return R.drawable.ic_rain;
        } else if (weatherId >=400 && weatherId<=403) {
            return R.drawable.ic_snow;
        } else if (weatherId >= 500 && weatherId <= 502) {
            return R.drawable.ic_fog;
        } else if (weatherId == 100) {
            return R.drawable.ic_clear;
        } else if (weatherId == 102) {
            return R.drawable.ic_light_clouds;
        } else if (weatherId >= 101 && weatherId <=103) {
            return R.drawable.ic_cloudy;
        }
        return -1;

    }

    /**
     * 安全的 String 返回
     *
     * @param prefix 默认字段
     * @param obj 拼接字段 (需检查)
     */
    public static String safeText(String prefix, String obj) {
        if (TextUtils.isEmpty(obj)) return "";
        return TextUtils.concat(prefix, obj).toString();
    }

    public static String safeText(String msg) {
        return safeText("", msg);
    }

    /**
     * 天气代码 100 为晴 101-213 500-901 为阴 300-406为雨
     *
     * @param code 天气代码
     * @return 天气情况
     */
    public static String getWeatherType(int code) {
        if (code == 100) {
            return "晴";
        }
        if ((code >= 101 && code <= 213) || (code >= 500 && code <= 901)) {
            return "阴";
        }
        if (code >= 300 && code <= 406) {
            return "雨";
        }
        return "错误";
    }

    /**
     * 正则匹配掉错误信息
     */
    public static String replaceCity(String city) {
        city = safeText(city).replaceAll("(?:省|市|自治区|特别行政区|地区|盟)", "");
        return city;
    }

    /**
     * Java 中有一个 Closeable 接口,标识了一个可关闭的对象,它只有一个 close 方法.
     */
    public static void closeQuietly(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}