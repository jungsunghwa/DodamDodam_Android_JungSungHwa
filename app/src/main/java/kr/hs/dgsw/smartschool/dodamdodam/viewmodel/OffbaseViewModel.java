package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;

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
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.OffbaseClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.OffbaseRequest;

/**
 * @author kimji
 */
public class OffbaseViewModel extends BaseViewModel<Offbase> {

    private final MutableLiveData<String> message = new MutableLiveData<>();
    private final MutableLiveData<List<Leave>> leavesData = new MutableLiveData<>();
    private final MutableLiveData<List<Pass>> passesData = new MutableLiveData<>();
    private final MutableLiveData<Leave> leaveData = new MutableLiveData<>();
    private final MutableLiveData<Pass> passData = new MutableLiveData<>();

    private OffbaseClient client;
    private CompositeDisposable disposable;
    private TokenManager manager;

    public OffbaseViewModel(Application application) {
        super(application);
        client = new OffbaseClient();
        disposable = new CompositeDisposable();
        manager = TokenManager.getInstance(application);
    }

    public MutableLiveData<String> getMessage() {
        return message;
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
        addDisposable(client.getOffbase(manager.getToken()), getDataObserver());
    }

    public void listAllow() {
        loading.setValue(true);
        disposable.add(client.getOffbaseAllow(manager.getToken()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<Offbase>() {
                            @Override
                            public void onSuccess(Offbase offbase) {
                                data.setValue(offbase);
                                loading.setValue(false);
                                dispose();
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (errorEvent(e))
                                    dispose();
                            }
                        }));
    }

    public void listCancel() {
        loading.setValue(true);
        disposable.add(client.getOffbaseCancel(manager.getToken()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<Offbase>() {
                            @Override
                            public void onSuccess(Offbase offbase) {
                                data.setValue(offbase);
                                loading.setValue(false);
                                dispose();
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (errorEvent(e))
                                    dispose();
                            }
                        }));
    }

    public void leaves() {
        loading.setValue(true);
        disposable.add(client.getLeaves(manager.getToken()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<Leaves>() {
                            @Override
                            public void onSuccess(Leaves leaves) {
                                leavesData.setValue(leaves.getLeaves());
                                loading.setValue(false);
                                dispose();
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (errorEvent(e))
                                    dispose();
                            }
                        }));
    }

    public void leaveAllows() {
        loading.setValue(true);
        disposable.add(client.getLeaveAllows(manager.getToken()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<Leaves>() {
                            @Override
                            public void onSuccess(Leaves leaves) {
                                leavesData.setValue(leaves.getLeaves());
                                loading.setValue(false);
                                dispose();
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (errorEvent(e))
                                    dispose();
                            }
                        }));
    }

    public void leaveCancels() {
        loading.setValue(true);
        disposable.add(client.getLeaveCancels(manager.getToken()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<Leaves>() {
                            @Override
                            public void onSuccess(Leaves leaves) {
                                leavesData.setValue(leaves.getLeaves());
                                loading.setValue(false);
                                dispose();
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (errorEvent(e))
                                    dispose();
                            }
                        }));
    }

    public void leaveById(int leaveId) {
        loading.setValue(true);
        disposable.add(client.getLeaveById(manager.getToken(), leaveId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<Leave>() {
                            @Override
                            public void onSuccess(Leave leave) {
                                leaveData.setValue(leave);
                                loading.setValue(false);
                                dispose();
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (errorEvent(e))
                                    dispose();
                            }
                        }));
    }

    public void passes() {
        loading.setValue(true);
        disposable.add(client.getPasses(manager.getToken()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<Passes>() {
                            @Override
                            public void onSuccess(Passes passes) {
                                passesData.setValue(passes.getPasses());
                                loading.setValue(false);
                                dispose();
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (errorEvent(e))
                                    dispose();
                            }
                        }));
    }

    public void passAllows() {
        loading.setValue(true);
        disposable.add(client.getPassAllows(manager.getToken()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<Passes>() {
                            @Override
                            public void onSuccess(Passes passes) {
                                passesData.setValue(passes.getPasses());
                                loading.setValue(false);
                                dispose();
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (errorEvent(e))
                                    dispose();
                            }
                        }));
    }

    public void passCancels() {
        loading.setValue(true);
        disposable.add(client.getPassCancels(manager.getToken()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<Passes>() {
                            @Override
                            public void onSuccess(Passes passes) {
                                passesData.setValue(passes.getPasses());
                                loading.setValue(false);
                                dispose();
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (errorEvent(e))
                                    dispose();
                            }
                        }));
    }

    public void passById(int passId) {
        loading.setValue(true);
        disposable.add(client.getPassById(manager.getToken(), passId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<Pass>() {
                            @Override
                            public void onSuccess(Pass pass) {
                                passData.setValue(pass);
                                loading.setValue(false);
                                dispose();
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (errorEvent(e))
                                    dispose();
                            }
                        }));
    }

    public void postLeave(OffbaseRequest request) {
        loading.setValue(true);
        disposable.add(client.postLeave(manager.getToken(), request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<String>() {
                            @Override
                            public void onSuccess(String msg) {
                                message.setValue(msg);
                                loading.setValue(false);
                                dispose();
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (errorEvent(e))
                                    dispose();
                            }
                        }));
    }

    public void postPass(OffbaseRequest request) {
        loading.setValue(true);
        disposable.add(client.postPass(manager.getToken(), request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<String>() {
                            @Override
                            public void onSuccess(String msg) {
                                message.setValue(msg);
                                loading.setValue(false);
                                dispose();
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (errorEvent(e))
                                    dispose();
                            }
                        }));
    }

    public void updateLeave(int leaveId, OffbaseRequest request) {
        loading.setValue(true);
        disposable.add(client.putLeave(manager.getToken(), leaveId, request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<String>() {
                            @Override
                            public void onSuccess(String msg) {
                                message.setValue(msg);
                                loading.setValue(false);
                                dispose();
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (errorEvent(e))
                                    dispose();
                            }
                        }));
    }

    public void updatePass(int passId, OffbaseRequest request) {
        loading.setValue(true);
        disposable.add(client.putPass(manager.getToken(), passId, request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<String>() {
                            @Override
                            public void onSuccess(String msg) {
                                message.setValue(msg);
                                loading.setValue(false);
                                dispose();
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (errorEvent(e))
                                    dispose();
                            }
                        }));
    }

    public void deleteLeave(int leaveId) {
        loading.setValue(true);
        disposable.add(client.deleteLeave(manager.getToken(), leaveId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<String>() {
                            @Override
                            public void onSuccess(String msg) {
                                message.setValue(msg);
                                loading.setValue(false);
                                dispose();
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (errorEvent(e))
                                    dispose();
                            }
                        }));
    }

    public void deletePass(int passId) {
        loading.setValue(true);
        disposable.add(client.deletePass(manager.getToken(), passId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<String>() {
                            @Override
                            public void onSuccess(String msg) {
                                message.setValue(msg);
                                loading.setValue(false);
                                dispose();
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (errorEvent(e))
                                    dispose();
                            }
                        }));
    }
}
