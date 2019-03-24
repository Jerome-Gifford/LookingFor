package lf.lookingfor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class User implements Parcelable {
    private String displayName;
    private String userEmail;
    private String userPhoto;
    private String userId;

    public User(String setName, String setEmail, String setUserId, String setPhoto){
        displayName = setName;
        userEmail = setEmail;
        userPhoto = setPhoto;
        userId = setUserId;
    }

    public User () {}

    protected User(Parcel in) {
        displayName = in.readString();
        userEmail = in.readString();
        userPhoto = in.readString();
        userId = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void setDisplayName(String setName) {
        displayName = setName;
    }

    public void setUserEmail(String setEmail) {
        userEmail = setEmail;
    }

    public void setUserPhoto(String setPhoto) {
        userPhoto = setPhoto;
    }

    public void setUserId(String setUserId) { userId = setUserId; }

    public String getDisplayName() {
        return displayName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public String getUserId() { return userId; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.displayName);
        dest.writeString(this.userEmail);
        dest.writeString(this.userPhoto);
        dest.writeString(this.userId);
    }
}
