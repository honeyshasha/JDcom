package com.honey.jdcom.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.honey.jdcom.R;

/**
 * Created by 小傻瓜 on 2017/9/29.
 */

public class FragmentDiscovery extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null){
            view=inflater.inflate(R.layout.fragment_discovery,container,false);
        }
        return view;
    }
}
