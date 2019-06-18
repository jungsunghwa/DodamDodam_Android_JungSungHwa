package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.Model.song.Video;
import kr.hs.dgsw.smartschool.dodamdodam.Model.song.YoutubeData;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.SongClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.SongCheckRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.SongRequest;

public class SongViewModel extends BaseViewModel<List<Video>> {

    private final MutableLiveData<List<Video>> songsData = new MutableLiveData<>();
    private final MutableLiveData<YoutubeData> selectedYoutubeData = new MutableLiveData<>();

    private SongClient client;
    private CompositeDisposable disposable;
    private DatabaseHelper helper;

    public SongViewModel(Context context) {
        super(context);
        client = new SongClient();
        disposable = new CompositeDisposable();
        helper = DatabaseHelper.getDatabaseHelper(context);
    }

    public MutableLiveData<List<Video>> getSongsData() {
        return songsData;
    }

    public MutableLiveData<YoutubeData> getSelectedYoutubeData() {
        return selectedYoutubeData;
    }

    public void list() {
        loading.setValue(true);
        addDisposable(client.getSongs(helper.getToken()), dataObserver);
    }

    public void listMy() {
        loading.setValue(true);
        addDisposable(client.getMySongs(helper.getToken()), dataObserver);
    }

    public void listMyAllow() {
        loading.setValue(true);
        addDisposable(client.getMyAllowSongs(helper.getToken()), dataObserver);
    }

    public void apply(SongRequest request) {
        loading.setValue(true);
        addDisposable(client.postSong(helper.getToken(), request), baseObserver);
//        disposable.add(client.postSong(helper.getToken(), request).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
//                        new DisposableSingleObserver<String>() {
//                            @Override
//                            public void onSuccess(String msg) {
//                                message.setValue(msg);
//                                loading.setValue(false);
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                error.setValue(e);
//                                loading.setValue(false);
//                            }
//                        }));
    }

    public void allow(SongCheckRequest request) {
        loading.setValue(true);
        addDisposable(client.postAllowSong(helper.getToken(), request), baseObserver);
//        disposable.add(client.postAllowSong(helper.getToken(), request).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
//                        new DisposableSingleObserver<String>() {
//                            @Override
//                            public void onSuccess(String msg) {
//                                message.setValue(msg);
//                                loading.setValue(false);
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                error.setValue(e);
//                                loading.setValue(false);
//                            }
//                        }));
    }

    public void deny(SongCheckRequest request) {
        loading.setValue(true);
        addDisposable(client.postDenySong(helper.getToken(), request), baseObserver);
//        disposable.add(client.postDenySong(helper.getToken(), request).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
//                        new DisposableSingleObserver<String>() {
//                            @Override
//                            public void onSuccess(String msg) {
//                                message.setValue(msg);
//                                loading.setValue(false);
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                error.setValue(e);
//                                loading.setValue(false);
//                            }
//                        }));
    }

    public void select(YoutubeData youtubeData, boolean selected) {
        disposable.add(Single.<YoutubeData>create(observer -> {
            if (selected)
                observer.onSuccess(youtubeData);
            else
                observer.onSuccess(new YoutubeData());
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                new DisposableSingleObserver<YoutubeData>() {
                    @Override
                    public void onSuccess(YoutubeData youtubeData) {
                        selectedYoutubeData.setValue(youtubeData);
                    }

                    @Override
                    public void onError(Throwable e) {}
                }
        ));
    }
}
