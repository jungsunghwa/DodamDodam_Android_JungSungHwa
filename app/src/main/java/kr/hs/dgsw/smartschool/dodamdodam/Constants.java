package kr.hs.dgsw.smartschool.dodamdodam;

import android.os.Environment;

/**
 * @author 정성화
 */
public final class Constants {

    public static final boolean DEBUG = BuildConfig.DEBUG;
    public static final boolean LEAK_TEST_MODE = false;

    public static final String DIRECTORY_DOWNLOADS = Environment.DIRECTORY_DOWNLOADS + "/DodamDodam";

    public static String SOCKET_HOST = "http://192.168.0.55:3000";
    public static final String DEFAULT_HOST = "http://49.247.130.189:35800";

}
