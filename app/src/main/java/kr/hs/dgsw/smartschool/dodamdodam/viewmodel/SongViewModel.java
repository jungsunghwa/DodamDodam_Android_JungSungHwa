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
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.SongClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.SongCheckRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.SongRequest;

public class SongViewModel extends ViewModel {

    private final MutableLiveData<List<Video>> songsData = new MutableLiveData<>();
    private final MutableLiveData<Throwable> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<String> message = new MutableLiveData<>();
    private final MutableLiveData<YoutubeData> selectedYoutubeData = new MutableLiveData<>();

    private SongClient client;
    private CompositeDisposable disposable;
    private TokenManager manager;

    public SongViewModel(Context context) {
        client = new SongClient();
        disposable = new CompositeDisposable();
        manager = TokenManager.getInstance(context);
    }

    public MutableLiveData<List<Video>> getSongsData() {
        return songsData;
    }

    public MutableLiveData<Throwable> getError() {
        return error;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<YoutubeData> getSelectedYoutubeData() {
        return selectedYoutubeData;
    }

    public void list() {
        loading.setValue(true);
        disposable.add(client.getSongs(manager.getToken()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<List<Video>>() {
                            @Override
                            public void onSuccess(List<Video> videos) {
                                songsData.setValue(videos);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                error.setValue(e);
                                loading.setValue(false);
                            }
                        }));
    }

    public void listMy() {
        loading.setValue(true);
        disposable.add(client.getMySongs(manager.getToken()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<List<Video>>() {
                            @Override
                            public void onSuccess(List<Video> videos) {
                                songsData.setValue(videos);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                error.setValue(e);
                                loading.setValue(false);
                            }
                        }));
    }

    public void listMyAllow() {
        loading.setValue(true);
        disposable.add(client.getMyAllowSongs(manager.getToken()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<List<Video>>() {
                            @Override
                            public void onSuccess(List<Video> videos) {
                                songsData.setValue(videos);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                error.setValue(e);
                                loading.setValue(false);
                            }
                        }));
    }

    public void apply(SongRequest request) {
        loading.setValue(true);
        disposable.add(client.postSong(manager.getToken(), request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<String>() {
                            @Override
                            public void onSuccess(String msg) {
                                message.setValue(msg);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                error.setValue(e);
                                loading.setValue(false);
                            }
                        }));
    }

    public void allow(SongCheckRequest request) {
        loading.setValue(true);
        disposable.add(client.postAllowSong(manager.getToken(), request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<String>() {
                            @Override
                            public void onSuccess(String msg) {
                                message.setValue(msg);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                error.setValue(e);
                                loading.setValue(false);
                            }
                        }));
    }

    public void deny(SongCheckRequest request) {
        loading.setValue(true);
        disposable.add(client.postDenySong(manager.getToken(), request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<String>() {
                            @Override
                            public void onSuccess(String msg) {
                                message.setValue(msg);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                error.setValue(e);
                                loading.setValue(false);
                            }
                        }));
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
                    public void onError(Throwable e) {
                    }
                }
        ));
    }
}
