package lf.lookingfor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewUsersFragment extends Fragment {

    User user;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    UserRating rating;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Users");
        Bundle bundle = getArguments();
        user = bundle.getParcelable("user");
        return inflater.inflate(R.layout.fragment_view_user, null);
    }

    public void onViewCreated(@NonNull final View mainView, @Nullable Bundle savedInstanceState) {
        TextView displayName = (TextView) mainView.findViewById(R.id.display_name);
        TextView userEmail = (TextView) mainView.findViewById(R.id.user_email);
        displayName.setText(user.getDisplayName());
        userEmail.setText(user.getUserEmail());
        final RatingBar rating_bar = (RatingBar) mainView.findViewById(R.id.user_rating);
        rating_bar.setEnabled(false);
        final RatingBar user_rate = (RatingBar) mainView.findViewById(R.id.set_user_rating) ;
        DatabaseReference myRef = database.getReference("ratings");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    if(messageSnapshot.getKey().equals(user.getUserId())) {
                        rating = messageSnapshot.getValue(UserRating.class);
                        rating_bar.setMax(5);
                        rating_bar.setRating(rating.averageRating());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error");
            }
        };
        myRef.addListenerForSingleValueEvent(listener);
        mainView.findViewById(R.id.btn_rate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                rateUser(user_rate);
            }
        });
    }

    private void rateUser(RatingBar user_rating){
        float ratingValue = user_rating.getRating();
        rating.rate(new Rating(FirebaseAuth.getInstance().getCurrentUser().getUid(), ratingValue));
        DatabaseReference myRef = database.getReference();
        myRef.child("ratings").child(user.getUserId()).setValue(rating);
        Toast.makeText(getActivity(), "User rated", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getContext(), MainActivity.class));
    }
}
