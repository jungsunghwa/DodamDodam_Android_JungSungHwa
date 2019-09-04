package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import java.io.File;

import kr.hs.dgsw.b1nd.service.model.Member;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.MemberClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.MyinfoChangeRequest;

public class MemberViewModel extends BaseViewModel<Member> {

    private MemberClient client;
    private TokenManager manager;

    public MutableLiveData<File> file = new MutableLiveData<>();
    public MutableLiveData<Uri> uri = new MutableLiveData<>();
    public MyinfoChangeRequest request = new MyinfoChangeRequest();

    public MemberViewModel(Application application) {
        super(application);
        client = new MemberClient();
        manager = TokenManager.getInstance(application);
    }

    public void search(String id) {
        loading.setValue(true);
        addDisposable(client.findMember(manager.getToken(), id), getDataObserver());
    }

    public void change() {
        loading.setValue(true);
        addDisposable(client.changeMember(manager.getToken(), helper.getMyInfo().getId(), request),getBaseObserver());
    }
}
