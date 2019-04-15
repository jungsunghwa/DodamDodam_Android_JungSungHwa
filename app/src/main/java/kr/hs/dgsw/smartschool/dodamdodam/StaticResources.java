package kr.hs.dgsw.smartschool.dodamdodam;

import android.os.Environment;

import androidx.databinding.library.baseAdapters.BuildConfig;

/**
 * @author 정성화
 */
public final class StaticResources {

    public static final boolean DEBUG = BuildConfig.DEBUG;
    public static final boolean LEAK_TEST_MODE = false;

    public static final String DIRECTORY_DOWNLOADS = Environment.DIRECTORY_DOWNLOADS + "/TMessenger";

    public static String SOCKET_HOST = "http://192.168.0.55:3000";
    public static String DEFAULT_HOST = "http://192.168.0.42:8000";

}
