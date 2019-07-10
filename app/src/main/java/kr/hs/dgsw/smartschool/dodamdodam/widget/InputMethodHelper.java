package kr.hs.dgsw.smartschool.dodamdodam.widget;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class InputMethodHelper {
    private InputMethodManager im;

    public InputMethodHelper(Activity activity) {
        im = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
    }

    public void closeSoftKeyboard() {
        im.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}
