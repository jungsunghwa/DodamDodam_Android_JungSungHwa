package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.app.Application;

import kr.hs.dgsw.b1nd.service.model.Member;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.MemberClient;

public class MemberViewModel extends BaseViewModel<Member> {

    private MemberClient client;
    private TokenManager manager;

    public MemberViewModel(Application application) {
        super(application);
        client = new MemberClient(application);
        manager = TokenManager.getInstance(application);
    }

    public void search(String id) {
        loading.setValue(true);
        addDisposable(client.findMember(manager.getToken(), id), getDataObserver());
    }
}
