package kr.hs.dgsw.smartschool.dodamdodam.widget;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * @author Felipe Herranz (felhr85@gmail.com)
 * <p>
 * -- Contributors --
 * Francesco Verheye (verheye.francesco@gmail.com)
 * Israel Dominguez (dominguez.israel@gmail.com)
 */
public class SoftKeyboardManager implements View.OnFocusChangeListener {
    private static final int CLEAR_FOCUS = 0;
    // This handler will clear focus of selected EditText
    private static final Handler mHandler = new FocusHandler();
    private static WeakReference<View> tempView; // reference to a focused EditText
    private ViewGroup layout;
    private int layoutBottom;
    private InputMethodManager im;
    private int[] coordinates;
    private boolean isKeyboardShow;
    private SoftKeyboardChangesThread softKeyboardThread;

    public SoftKeyboardManager(Activity activity) {
        this((ViewGroup) activity.getWindow().getDecorView(), (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE));
    }


    private SoftKeyboardManager(ViewGroup layout, InputMethodManager im) {
        this.layout = layout;
        keyboardHideByDefault();
        initEditTexts(layout);
        this.im = im;
        this.coordinates = new int[2];
        this.isKeyboardShow = false;
        this.softKeyboardThread = new SoftKeyboardChangesThread();
        this.softKeyboardThread.start();
    }

    public void openSoftKeyboard() {
        if (!isKeyboardShow) {
            layoutBottom = getLayoutCoordinates();
            im.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
            softKeyboardThread.keyboardOpened();
            isKeyboardShow = true;
        }
    }

    public void closeSoftKeyboard() {
        if (isKeyboardShow) {
            im.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            isKeyboardShow = false;
        }
    }

    public void setSoftKeyboardCallback(SoftKeyboardChanged mCallback) {
        softKeyboardThread.setCallback(mCallback);
    }

    public void unregisterSoftKeyboardCallback() {
        softKeyboardThread.stopThread();
    }

    private int getLayoutCoordinates() {
        layout.getLocationOnScreen(coordinates);
        return coordinates[1] + layout.getHeight();
    }

    private void keyboardHideByDefault() {
        layout.setFocusable(true);
        layout.setFocusableInTouchMode(true);
    }

    /*
     * InitEditTexts now handles EditTexts in nested views
     * Thanks to Francesco Verheye (verheye.francesco@gmail.com)
     */
    private void initEditTexts(ViewGroup viewgroup) {
        int childCount = viewgroup.getChildCount();
        for (int i = 0; i <= childCount - 1; i++) {
            View v = viewgroup.getChildAt(i);

            if (v instanceof ViewGroup) {
                initEditTexts((ViewGroup) v);
            }

            if (v instanceof EditText) {
                EditText editText = (EditText) v;
                editText.setOnFocusChangeListener(this);
                editText.setCursorVisible(true);
            }
        }
    }

    /*
     * OnFocusChange does update tempView correctly now when keyboard is still shown
     * Thanks to Israel Dominguez (dominguez.israel@gmail.com)
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            tempView = new WeakReference<>(v);
            if (!isKeyboardShow) {
                layoutBottom = getLayoutCoordinates();
                softKeyboardThread.keyboardOpened();
                isKeyboardShow = true;
            }
        }
    }

    public interface SoftKeyboardChanged {
        void onSoftKeyboardHide();

        void onSoftKeyboardShow();
    }

    private static final class FocusHandler extends Handler {
        @Override
        public void handleMessage(Message m) {
            if (m.what == CLEAR_FOCUS) {
                if (tempView.get() != null) {
                    tempView.get().clearFocus();
                    tempView = new WeakReference<>(null);
                }
            }
        }
    }

    private class SoftKeyboardChangesThread extends Thread {
        private AtomicBoolean started;
        private SoftKeyboardChanged mCallback;

        SoftKeyboardChangesThread() {
            started = new AtomicBoolean(true);
        }

        public void setCallback(SoftKeyboardChanged mCallback) {
            this.mCallback = mCallback;
        }

        @Override
        public void run() {
            while (started.get()) {
                // Wait until keyboard is requested to open
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                int currentBottomLocation = getLayoutCoordinates();

                // There is some lag between open soft-keyboard function and when it really appears.
                while (currentBottomLocation == layoutBottom && started.get()) {
                    currentBottomLocation = getLayoutCoordinates();
                }

                if (started.get())
                    if (mCallback != null)
                        mCallback.onSoftKeyboardShow();

                // When keyboard is opened from EditText, initial bottom location is greater than layoutBottom
                // and at some moment equals layoutBottom.
                // That broke the previous logic, so I added this new loop to handle this.
                while (currentBottomLocation >= layoutBottom && started.get()) {
                    currentBottomLocation = getLayoutCoordinates();
                }

                // Now Keyboard is shown, keep checking layout dimensions until keyboard is gone
                while (currentBottomLocation != layoutBottom && started.get()) {
                    synchronized (this) {
                        try {
                            wait(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    currentBottomLocation = getLayoutCoordinates();
                }

                if (started.get())
                    if (mCallback != null)
                        mCallback.onSoftKeyboardHide();

                // if keyboard has been opened clicking and EditText.
                if (isKeyboardShow && started.get())
                    isKeyboardShow = false;

                // if an EditText is focused, remove its focus (on UI thread)
                if (started.get())
                    mHandler.obtainMessage(CLEAR_FOCUS).sendToTarget();
            }
        }

        void keyboardOpened() {
            synchronized (this) {
                notify();
            }
        }

        void stopThread() {
            synchronized (this) {
                started.set(false);
                notify();
            }
        }

    }
}
