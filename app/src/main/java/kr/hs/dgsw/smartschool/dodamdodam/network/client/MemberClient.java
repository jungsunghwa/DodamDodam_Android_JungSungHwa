package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import android.content.Context;

import io.reactivex.Single;
import kr.hs.dgsw.b1nd.service.model.Member;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.MyinfoChangeRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.MemberService;

public class MemberClient extends NetworkClient {
    private MemberService member;

    public MemberClient() {
        member = Utils.RETROFIT.create(MemberService.class);
    }

    public Single<Member> findMember(Token token, String id) {
        return member.findMember(token.getToken(), id).map(getResponseObjectsFunction());
    }

    public Single<String> changeMember(Token token, String id, MyinfoChangeRequest myinfoChangeRequest) {
        return member.changeMember(token.getToken(), id, myinfoChangeRequest).map(getResponseMessageFunction());
    }
}
