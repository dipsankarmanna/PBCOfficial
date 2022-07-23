package com.example.collegeapp.user.ui.about;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.collegeapp.R;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class BranchAdapter extends PagerAdapter {
    Context context;
    List<BranchModel> list;
    //Drawable[] imgs={};

    public BranchAdapter(Context context, List<BranchModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.branch_item_layout, container, false);
        ImageView bricon;
        TextView brtitle, brdesc;
        MaterialCardView cb;
        bricon = view.findViewById(R.id.branch_icon);
        brtitle = view.findViewById(R.id.branch_title);
        brdesc = view.findViewById(R.id.branch_desc);
        cb=view.findViewById(R.id.cb);

        //cb.setBackgroundResource(list.get(position).getBac());
        cb.setBackgroundResource(list.get(position).getBac());
        bricon.setImageResource(list.get(position).getImg());
        brtitle.setText(list.get(position).getTitle());
        brdesc.setText(list.get(position).getDescription());
        brdesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
