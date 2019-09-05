package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import kr.hs.dgsw.b1nd.service.B1ndService;
import kr.hs.dgsw.smartschool.dodamdodam.Constants;

public class ChangePasswordClient extends NetworkClient {

    public ChangePasswordClient() {
        B1ndService.setHostURL(Constants.DEFAULT_HOST);
    }

}
