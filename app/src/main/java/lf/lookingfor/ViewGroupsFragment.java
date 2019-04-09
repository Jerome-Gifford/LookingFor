package lf.lookingfor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ViewGroupsFragment extends Fragment {

    UserGroup group;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<String> members = new ArrayList<>();
    User user;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Groups");
        Bundle bundle = getArguments();
        group = bundle.getParcelable("group");
        return inflater.inflate(R.layout.fragment_view_group, null);
    }

    public void onViewCreated(@NonNull final View mainView, @Nullable Bundle savedInstanceState) {
        TextView groupName = (TextView) mainView.findViewById(R.id.group_name);
        final TextView groupDesc = (TextView) mainView.findViewById(R.id.group_description);
        final ListView memberView = (ListView) mainView.findViewById(R.id.group_members);
        final Button button = (Button) mainView.findViewById(R.id.btn_join);
        groupName.setText(group.getGroupName());
        groupDesc.setText(group.getDescription());
        DatabaseReference myRef = database.getReference("users");
        final ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                FirebaseUser fUser = fAuth.getCurrentUser();
                String userId = fUser.getUid();
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    ArrayList<User> groupMembers = group.getMembers();
                    if(messageSnapshot.getKey().equals(userId)){
                        user = messageSnapshot.getValue(User.class);
                    }
                    for(User u: groupMembers){
                        if(u.getUserId().equals(messageSnapshot.getKey())) {
                            if(u.getUserId().equals(userId)){
                                button.setVisibility(View.INVISIBLE);
                            }
                            members.add(u.getDisplayName());
                            ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, members);
                            memberView.setAdapter(adapter);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error");
            }
        };
        myRef.addValueEventListener(listener);
        //myRef.addListenerForSingleValueEvent(listener);
        mainView.findViewById(R.id.btn_join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                group.joinGroup(user);
                DatabaseReference myRef = database.getReference();
                myRef.child("groups").child(group.getGroupId()).setValue(group);
                button.setVisibility(View.INVISIBLE);
            }
        });
    }
}
