package lf.lookingfor;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String msgToken = "REG_TOKEN";
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public void initFCM() {
        String recentToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(msgToken, recentToken);
        sendRegistrationToServer(recentToken);
    }

    public void sendRegistrationToServer(String token){
        Log.d(msgToken, "sendRegistrationToServer: sending token to server: " + token);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(mAuth.getCurrentUser().getUid()).child("messaging_token").setValue(token);
    }
}
