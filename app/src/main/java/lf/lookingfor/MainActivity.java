package lf.lookingfor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.FrameStats;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    NavigationView navigationView;
    View headerView;
    int mapRadius = 1600;
    EditText radiusEdit;
    ImageView proPic;
    TextView nView;
    TextView eView;
    String profileImageUrl;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Looking For");

        if(fbUser == null){
            finish();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }else{
            user.setDisplayName(fbUser.getDisplayName());
            user.setUserEmail(fbUser.getEmail());
            user.setUserPhoto(fbUser.getPhotoUrl().toString());
            user.setUserId(fbUser.getUid());
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        radiusEdit = (EditText) findViewById(R.id.radiusEdit);

        navigationView = (NavigationView) findViewById(R.id.drawer_layout).findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);

        proPic = headerView.findViewById(R.id.imageView);
        TextView name = headerView.findViewById(R.id.nameView);
        TextView email = headerView.findViewById(R.id.emailView);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference picRef = storageRef.child("profilepics/" + user.getUserId() + ".jpg");

        try {
            final File localFile = File.createTempFile("images", ".jpg");
            picRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Glide.with(headerView.getContext())
                            .load(localFile)
                            .into(proPic);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        proPic.setVisibility(View.VISIBLE);

        Fragment fragment = new HomeFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        ft.replace(R.id.screen_area, fragment);

        ft.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        radiusEdit = (EditText) findViewById(R.id.radiusEdit);
        navigationView = (NavigationView) findViewById(R.id.drawer_layout).findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);

        proPic = headerView.findViewById(R.id.imageView);
        TextView name = headerView.findViewById(R.id.nameView);
        TextView email = headerView.findViewById(R.id.emailView);

        fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if(fbUser == null){
            finish();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }else{
            user.setDisplayName(fbUser.getDisplayName());
            user.setUserEmail(fbUser.getEmail());
            user.setUserPhoto(fbUser.getPhotoUrl().toString());
            user.setUserId(fbUser.getUid());
        }

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference picRef = storageRef.child("profilepics/ " + user.getUserId() + ".jpg");

        try {
            final File localFile = File.createTempFile("images", ".jpg");
            picRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Glide.with(headerView.getContext())
                            .load(localFile)
                            .into(proPic);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        proPic.setVisibility(View.VISIBLE);

        name.setText(user.getDisplayName());
        email.setText(user.getUserEmail());
    }

    @Override
    public void onStart(){
        super.onStart();
        fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if(fbUser == null){
            finish();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }else{
            user.setDisplayName(fbUser.getDisplayName());
            user.setUserEmail(fbUser.getEmail());
            user.setUserPhoto(fbUser.getPhotoUrl().toString());
            user.setUserId(fbUser.getUid());
        }
        radiusEdit = (EditText) findViewById(R.id.radiusEdit);

        navigationView = (NavigationView) findViewById(R.id.drawer_layout).findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);

        proPic = headerView.findViewById(R.id.imageView);
        TextView name = headerView.findViewById(R.id.nameView);
        TextView email = headerView.findViewById(R.id.emailView);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference picRef = storageRef.child("profilepics/ " + user.getUserId() + ".jpg");

        try {
            final File localFile = File.createTempFile("images", ".jpg");
            picRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Glide.with(headerView.getContext())
                            .load(localFile)
                            .into(proPic);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        proPic.setVisibility(View.VISIBLE);

        name.setText(user.getDisplayName());
        email.setText(user.getUserEmail());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_home){
            fragment = new HomeFragment();
        }
        else if (id == R.id.nav_findEvent) {
            fragment = new JoinEventFragment();
        }
        else if (id == R.id.nav_createEvent) {
            fragment = new CreateEventFragment();
        }
        else if (id == R.id.nav_myEvents) {
            fragment = new MyEventsFragment();
        }
        else if (id == R.id.nav_profile) {
            fragment = new ProfileFragment();
        }
        else if (id == R.id.nav_users) {
            fragment = new SelectUserFragment();
        }
        else if (id == R.id.nav_groups) {
            fragment = new SelectGroupFragment();
        }
        else if (id == R.id.nav_create_group) {
            fragment = new CreateGroupFragment();
        }
        else if (id == R.id.nav_logout){
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        else if (id == R.id.nav_viewMap){
            int rad;
            try {
                rad = Integer.parseInt(radiusEdit.getText().toString());
                mapRadius = rad;
                if( rad < 20 || rad > 99999){
                    radiusEdit.setFocusable(View.FOCUSABLE);
                    Toast.makeText(MainActivity.this, "Enter a search radius between 20 and 99999 meters.", Toast.LENGTH_SHORT).show();
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                    return false;
                }
            } catch (Exception e){
                radiusEdit.setFocusable(View.FOCUSABLE);
                Toast.makeText(MainActivity.this, "Enter a search radius between 20 and 99999 meters.", Toast.LENGTH_SHORT).show();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }

            finish();
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            intent.putExtra("MAP_RADIUS", mapRadius);
            startActivity(intent);
        }

        if(fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();

            ft.replace(R.id.screen_area, fragment);

            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
