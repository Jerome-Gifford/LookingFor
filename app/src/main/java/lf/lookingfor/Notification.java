package lf.lookingfor;

import android.os.Parcel;
import android.os.Parcelable;

public class Notification implements Parcelable {
    private String message;
    private  String token;
    private String uid;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setId(String uid) {
        this.uid = uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeString(this.token);
        dest.writeString(this.uid);
    }

    protected Notification(Parcel in) {
        message = in.readString();
        token = in.readString();
        uid = in.readString();
    }

    public static final Creator<Notification> CREATOR = new Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel in) {
            return new Notification(in);
        }

        @Override
        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };

    public Notification (){

    }

    public Notification(Notification copyNotification){
        this.message = copyNotification.message;
        this.token = copyNotification.token;
        this.uid = copyNotification.uid;
    }

    public Notification(String message, String token, String uid){
        this.message = message;
        this.token = token;
        this.uid = uid;
    }
}
