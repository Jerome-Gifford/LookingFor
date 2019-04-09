package lf.lookingfor;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class UserGroup implements Parcelable {
    private String groupName;
    private String description;
    private ArrayList<User> members = new ArrayList<>();
    private String groupId;

    public UserGroup() {
    }

    public UserGroup(String groupName, String description) {
        this.groupName = groupName;
        this.description = description;
    }

    protected UserGroup(Parcel in) {
        this.groupName = in.readString();
        this.description = in.readString();
        this.members = in.createTypedArrayList(User.CREATOR);
    }

    public static final Creator<UserGroup> CREATOR = new Creator<UserGroup>() {
        @Override
        public UserGroup createFromParcel(Parcel in) {
            return new UserGroup(in);
        }

        @Override
        public UserGroup[] newArray(int size) {
            return new UserGroup[size];
        }
    };

    public void joinGroup(User user) {
        this.members.add(user);
    }

    public boolean eligibleToJoin(User user) {
        for (User currentUser : this.members) {
            if(currentUser.getUserId().equals(user.getUserId())){
                return false;
            }
        }
        return true;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.groupName);
        dest.writeString(this.description);
        dest.writeList(this.members);
    }
}
