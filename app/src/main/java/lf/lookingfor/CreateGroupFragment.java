package lf.lookingfor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CreateGroupFragment extends Fragment {
    EditText group_name;
    EditText group_desc;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    String userId;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Create a Group");
        return inflater.inflate(R.layout.fragment_create_group, null);
    }

    @Override
    public void onViewCreated(@NonNull final View mainView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(mainView, savedInstanceState);
        mainView.findViewById(R.id.create_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String groupResult = createGroup(mainView);
                if(groupResult.equals("Group Created")) {
                    Toast.makeText(getActivity(), "Group Created", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "You are missing " + groupResult, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public String createGroup(View view){
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        userId = fUser.getUid();
        User user = new User();
        user.setDisplayName(fUser.getDisplayName());
        user.setUserEmail(fUser.getEmail());
        user.setUserPhoto(fUser.getPhotoUrl().toString());
        user.setUserId(fUser.getUid());
        group_name = (EditText) view.findViewById(R.id.group_name);
        group_desc = (EditText) view.findViewById(R.id.group_desc);
        String groupName = group_name.getText().toString().trim();
        if(groupName.equals("")){
            return "Group Name";
        }
        String groupDesc = group_desc.getText().toString().trim();
        if(groupDesc.equals("")){
            return "Group Description";
        }
        UserGroup group = new UserGroup(groupName, groupDesc);
        group.joinGroup(user);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("groups").push();
        group.setGroupId(myref.getKey());
        myref.setValue(group);
        return "Group Created";
    }
}
