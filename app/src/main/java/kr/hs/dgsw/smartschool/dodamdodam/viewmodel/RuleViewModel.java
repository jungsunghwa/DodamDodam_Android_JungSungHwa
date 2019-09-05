package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import io.reactivex.disposables.CompositeDisposable;
import kr.hs.dgsw.smartschool.dodamdodam.Model.rule.Rules;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.RuleClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.RuleRequest;

public class RuleViewModel extends BaseViewModel<Rules> {

    private final MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private RuleClient ruleClient;
    private CompositeDisposable disposable;
    private TokenManager manager;

    public RuleViewModel(Application application) {
        super(application);
        ruleClient = new RuleClient();
        disposable = new CompositeDisposable();
        manager = TokenManager.getInstance(application);
    }

    public void getRule() {
        loading.setValue(true);

        addDisposable(ruleClient.getRule(manager.getToken()), getDataObserver());
    }

    public void postRuleAdd(RuleRequest request) {
        loading.setValue(true);

        addDisposable(ruleClient.postRuleAdd(manager.getToken(), request), getBaseObserver());
    }

//    public void getlookRule(int idx) {
//        loading.setValue(true);
//
//        addDisposable(ruleClient.);
//    }

    public void putModifyRule(int idx, RuleRequest request) {
        loading.setValue(true);

        addDisposable(ruleClient.putModifyRule(manager.getToken(), idx, request), getBaseObserver());
    }

    public void deleteRule(int idx) {
        loading.setValue(true);

        addDisposable(ruleClient.deleteRule(manager.getToken(), idx), getBaseObserver());
    }
}
