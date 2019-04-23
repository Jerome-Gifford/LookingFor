package lf.lookingfor;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ProfileFragment extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    UserRating rating;
    FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        getActivity().setTitle("Profile");
        return inflater.inflate(R.layout.fragment_profile, null);
    }

    public void onViewCreated(@NonNull final View mainView, @Nullable Bundle savedInstanceState) {
        final RatingBar rating_bar = (RatingBar) mainView.findViewById(R.id.user_rating);
        TextView nameView = (TextView) mainView.findViewById(R.id.nameView);
        TextView emailView = (TextView) mainView.findViewById(R.id.emailView);
        final ImageView imageView = (ImageView) mainView.findViewById(R.id.imageView);

        imageView.setMaxHeight(500);
        imageView.setMaxWidth(500);

        rating_bar.setEnabled(false);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference picRef = storageRef.child("profilepics/" + fbUser.getUid() + ".jpg");

        if(fbUser != null){
            nameView.setText("Display Name: " + fbUser.getDisplayName());
            emailView.setText("Email: " + fbUser.getEmail());
            try {
                final File localFile = File.createTempFile("images", ".jpg");
                picRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Glide.with(mainView.getContext())
                                .load(localFile)
                                .into(imageView);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        DatabaseReference myRef = database.getReference("ratings");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    if(messageSnapshot.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
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
    }
}
