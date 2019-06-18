package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Leave;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Leaves;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Offbase;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Pass;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Passes;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.OffbaseClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.OffbaseRequest;

/**
 * @author kimji
 */
public class OffbaseViewModel extends BaseViewModel<Offbase> {

    private final MutableLiveData<Throwable> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<String> message = new MutableLiveData<>();
    private final MutableLiveData<Offbase> offbaseData = new MutableLiveData<>();
    private final MutableLiveData<List<Leave>> leavesData = new MutableLiveData<>();
    private final MutableLiveData<List<Pass>> passesData = new MutableLiveData<>();
    private final MutableLiveData<Leave> leaveData = new MutableLiveData<>();
    private final MutableLiveData<Pass> passData = new MutableLiveData<>();

    private OffbaseClient client;
    private CompositeDisposable disposable;
    private DatabaseHelper helper;

    public OffbaseViewModel(Context context) {
        super(context);
        client = new OffbaseClient();
        disposable = new CompositeDisposable();
        helper = DatabaseHelper.getDatabaseHelper(context);
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

    public MutableLiveData<Offbase> getOffbase() {
        return offbaseData;
    }

    public MutableLiveData<List<Leave>> getLeaves() {
        return leavesData;
    }

    public MutableLiveData<List<Pass>> getPasses() {
        return passesData;
    }

    public MutableLiveData<Leave> getLeave() {
        return leaveData;
    }

    public MutableLiveData<Pass> getPass() {
        return passData;
    }

    public void list() {
        loading.setValue(true);
        addDisposable(client.getOffbase(helper.getToken()), dataObserver);
    }

    public void listAllow() {
        loading.setValue(true);
        disposable.add(client.getOffbaseAllow(helper.getToken()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<Offbase>() {
                            @Override
                            public void onSuccess(Offbase offbase) {
                                offbaseData.setValue(offbase);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                error.setValue(e);
                                loading.setValue(false);
                            }
                        }));
    }

    public void listCancel() {
        loading.setValue(true);
        disposable.add(client.getOffbaseCancel(helper.getToken()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<Offbase>() {
                            @Override
                            public void onSuccess(Offbase offbase) {
                                offbaseData.setValue(offbase);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                error.setValue(e);
                                loading.setValue(false);
                            }
                        }));
    }

    public void leaves() {
        loading.setValue(true);
        disposable.add(client.getLeaves(helper.getToken()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<Leaves>() {
                            @Override
                            public void onSuccess(Leaves leaves) {
                                leavesData.setValue(leaves.getLeaves());
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                error.setValue(e);
                                loading.setValue(false);
                            }
                        }));
    }

    public void leaveAllows() {
        loading.setValue(true);
        disposable.add(client.getLeaveAllows(helper.getToken()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<Leaves>() {
                            @Override
                            public void onSuccess(Leaves leaves) {
                                leavesData.setValue(leaves.getLeaves());
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                error.setValue(e);
                                loading.setValue(false);
                            }
                        }));
    }

    public void leaveCancels() {
        loading.setValue(true);
        disposable.add(client.getLeaveCancels(helper.getToken()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<Leaves>() {
                            @Override
                            public void onSuccess(Leaves leaves) {
                                leavesData.setValue(leaves.getLeaves());
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                error.setValue(e);
                                loading.setValue(false);
                            }
                        }));
    }

    public void leaveById(int leaveId) {
        loading.setValue(true);
        disposable.add(client.getLeaveById(helper.getToken(), leaveId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<Leave>() {
                            @Override
                            public void onSuccess(Leave leave) {
                                leaveData.setValue(leave);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                error.setValue(e);
                                loading.setValue(false);
                            }
                        }));
    }

    public void passes() {
        loading.setValue(true);
        disposable.add(client.getPasses(helper.getToken()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<Passes>() {
                            @Override
                            public void onSuccess(Passes passes) {
                                passesData.setValue(passes.getPasses());
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                error.setValue(e);
                                loading.setValue(false);
                            }
                        }));
    }

    public void passAllows() {
        loading.setValue(true);
        disposable.add(client.getPassAllows(helper.getToken()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<Passes>() {
                            @Override
                            public void onSuccess(Passes passes) {
                                passesData.setValue(passes.getPasses());
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                error.setValue(e);
                                loading.setValue(false);
                            }
                        }));
    }

    public void passCancels() {
        loading.setValue(true);
        disposable.add(client.getPassCancels(helper.getToken()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<Passes>() {
                            @Override
                            public void onSuccess(Passes passes) {
                                passesData.setValue(passes.getPasses());
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                error.setValue(e);
                                loading.setValue(false);
                            }
                        }));
    }

    public void passById(int passId) {
        loading.setValue(true);
        disposable.add(client.getPassById(helper.getToken(), passId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<Pass>() {
                            @Override
                            public void onSuccess(Pass pass) {
                                passData.setValue(pass);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                error.setValue(e);
                                loading.setValue(false);
                            }
                        }));
    }

    public void postLeave(OffbaseRequest request) {
        loading.setValue(true);
        disposable.add(client.postLeave(helper.getToken(), request).subscribeOn(Schedulers.io())
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

    public void postPass(OffbaseRequest request) {
        loading.setValue(true);
        disposable.add(client.postPass(helper.getToken(), request).subscribeOn(Schedulers.io())
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

    public void updateLeave(int leaveId, OffbaseRequest request) {
        loading.setValue(true);
        disposable.add(client.putLeave(helper.getToken(), leaveId, request).subscribeOn(Schedulers.io())
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

    public void updatePass(int passId, OffbaseRequest request) {
        loading.setValue(true);
        disposable.add(client.putPass(helper.getToken(), passId, request).subscribeOn(Schedulers.io())
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

    public void deleteLeave(int leaveId) {
        loading.setValue(true);
        disposable.add(client.deleteLeave(helper.getToken(), leaveId).subscribeOn(Schedulers.io())
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

    public void deletePass(int passId) {
        loading.setValue(true);
        disposable.add(client.deletePass(helper.getToken(), passId).subscribeOn(Schedulers.io())
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
}
