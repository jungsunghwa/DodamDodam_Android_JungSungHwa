package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter;

import android.content.Context;
import android.os.SystemClock;
import android.widget.CompoundButton;
import android.widget.Toast;

public abstract class OnSingleCheckChangeListener implements CompoundButton.OnCheckedChangeListener {

    private static final long MIN_CLICK_INTERVAL=100;

    private long mLastClickTime;

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        long currentClickTime= SystemClock.uptimeMillis();
        long elapsedTime=currentClickTime-mLastClickTime;
        mLastClickTime=currentClickTime;

        if(elapsedTime<=MIN_CLICK_INTERVAL){
            Toast.makeText(context(), "쫌 천천히 누르세요 ㅡㅡ", Toast.LENGTH_SHORT).show();
            buttonView.setChecked(!isChecked);
            return;
        }

        onSingleClick(buttonView, isChecked);
    }

    public abstract void onSingleClick(CompoundButton buttonView, boolean isChecked);

    public abstract Context context();

}
