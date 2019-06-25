package kr.hs.dgsw.smartschool.dodamdodam;

import android.util.Log;

import io.reactivex.observers.DisposableSingleObserver;

public abstract class BaseObserver<T> extends DisposableSingleObserver<T> {
    @Override
    public abstract void onSuccess(T data);

    @Override
    public void onError(Throwable e) {
        Log.e("a","a");
    }
}
