package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.Single;
import kr.hs.dgsw.b1nd.service.B1ndService;
import kr.hs.dgsw.b1nd.service.interfaces.OnGetClassesListener;
import kr.hs.dgsw.b1nd.service.model.ClassInfo;
import kr.hs.dgsw.b1nd.service.service.B1ndGetClasses;
import kr.hs.dgsw.smartschool.dodamdodam.Constants;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;

public class ClassClient {
    public ClassClient() {
        B1ndService.setHostURL(Constants.DEFAULT_HOST);
    }


}
