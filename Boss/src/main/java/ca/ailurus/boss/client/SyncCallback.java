package ca.ailurus.boss.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class SyncCallback {

    private static int NUM_RUNNING_CALLS = 0;

    private static PopupPanel loadingScreen = new PopupPanel(false, true);
    static {
//        loadingScreen.add(new Label("processing..."));
        loadingScreen.setGlassEnabled(true);
    }

    public static <T> AsyncCallback<T> create(AsyncCallback<T> callback) {
        return new ModalCallback<T>(callback);
    }

    private static class ModalCallback<T> implements AsyncCallback<T> {
        private AsyncCallback<T> callback;

        private ModalCallback(AsyncCallback<T> callback) {
            this.callback = callback;
            showLoadingScreen();
        }

        private void showLoadingScreen() {
            if (NUM_RUNNING_CALLS == 0) {
                loadingScreen.center();
            }
            NUM_RUNNING_CALLS++;
        }

        @Override
        public void onFailure(Throwable throwable) {
            callback.onFailure(throwable);
            hideLoadingScreen();
        }

        @Override
        public void onSuccess(T value) {
            callback.onSuccess(value);
            hideLoadingScreen();
        }


        private void hideLoadingScreen() {
            NUM_RUNNING_CALLS--;
            if (NUM_RUNNING_CALLS == 0) {
                loadingScreen.hide();
            }
        }
    }
}
