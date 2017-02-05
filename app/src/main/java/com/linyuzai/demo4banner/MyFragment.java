package com.linyuzai.demo4banner;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/2/5 0005.
 */

public class MyFragment extends Fragment {
    static String ID = "id";
    private Drawable drawable;

    public static Fragment newInstance(int drawableId) {
        Fragment fragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ID, drawableId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.drawable = getResources().getDrawable(getArguments().getInt(ID));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        ImageView image = (ImageView) view.findViewById(R.id.fragment_image);
        image.setImageDrawable(drawable);
        return view;
    }
}
