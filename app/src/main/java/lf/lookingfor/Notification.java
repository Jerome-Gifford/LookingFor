package lf.lookingfor;

import android.os.Parcel;
import android.os.Parcelable;

public class Notification implements Parcelable {
    private String title;
    private String body;
    private  String token;
    private String uid;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        dest.writeString(this.title);
        dest.writeString(this.body);
        dest.writeString(this.token);
        dest.writeString(this.uid);
    }

    protected Notification(Parcel in) {
        title = in.readString();
        body = in.readString();
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
        this.title = copyNotification.title;
        this.token = copyNotification.token;
        this.uid = copyNotification.uid;
    }

    public Notification(String title, String body, String token, String uid){
        this.title = title;
        this.body = body;
        this.token = token;
        this.uid = uid;
    }
}
