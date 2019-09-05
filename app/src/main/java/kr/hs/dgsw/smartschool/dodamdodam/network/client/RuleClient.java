package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import java.util.List;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Model.rule.Rule;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.RuleRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.RuleService;

public class RuleClient extends NetworkClient {
    private RuleService rule;

    public RuleClient() {
        rule = Utils.RETROFIT.create(RuleService.class);
    }

    public Single<List<Rule>> getRule(Token token) {
        return rule.getRule(token.getToken()).map(getResponseObjectsFunction());
    }

    public Single<String> postRuleAdd(Token token, RuleRequest request) {
        return rule.postRuleAdd(token.getToken(), request).map(Response::getMessage);
    }

//    public Single<String> getlookRule(Token token, int idx) {
//        return rule.getlookRule(token.getToken(), idx).map(getResponseObjectsFunction());
//    }

    public Single<String> putModifyRule(Token token, int idx, RuleRequest request) {
        return rule.putModifyRule(token.getToken(), idx, request).map(Response::getMessage);
    }

    public Single<String> deleteRule(Token token, int idx) {
        return rule.deleteRule(token.getToken(), idx).map(Response::getMessage);
    }
}
