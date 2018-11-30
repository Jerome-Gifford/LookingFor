package lf.lookingfor.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import lf.lookingfor.R;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

public class CreateEventFragment extends Fragment implements ScreenShotable {

    private View containerView;
    protected ImageView mImageView;
    protected int res;
    private Bitmap bitmap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_event_fragment, container, false);
        return rootView;
    }


    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }
}
