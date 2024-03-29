package kr.hs.dgsw.smartschool.dodamdodam.network.task;

/**
 * AsyncTack 에서 UI 더 나은 상호작용을 위한 Listener
 *
 * @param <T> AsyncTask 에서 반환 값으로 받을 클레스
 * @author kimji
 */
public interface OnTaskListener<T> {
    void onFinished(T t);

    void onCancelled();

    <E extends Exception> void onFailed(E exception);
}
