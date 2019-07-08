package kr.hs.dgsw.smartschool.dodamdodam;

import android.os.Environment;

/**
 * @author 정성화
 */
public final class Constants {

    public static final boolean DEBUG = BuildConfig.DEBUG;
    public static final boolean LEAK_TEST_MODE = false;

    public static final String DIRECTORY_DOWNLOADS = Environment.DIRECTORY_DOWNLOADS + "/DodamDodam";

    public static final String SOCKET_HOST = "http://192.168.0.55:3000";
    public static final String DEFAULT_HOST = "http://192.168.0.55:8000";
//    public static final String DEFAULT_HOST = "http://49.247.130.189:35800";
//    public static final String DEFAULT_HOST = "http://10.80.163.81:8000";
//    public static final String DEFAULT_HOST = "http://172.30.1.44:8000";


        /*
         광용 192.168.0.20
         태형 192.168.0.18 (10.80.161.100)
         진영 192.168.0.19
         서연 192.168.0.42
         M2SYS HOST 192.168.0.55
         HOST 49.247.130.189:35800
        */

}
