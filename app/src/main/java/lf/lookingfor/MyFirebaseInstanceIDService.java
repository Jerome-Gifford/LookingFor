package lf.lookingfor;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String msgToken = "REG_TOKEN";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String recentToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(msgToken, recentToken);
    }
}
