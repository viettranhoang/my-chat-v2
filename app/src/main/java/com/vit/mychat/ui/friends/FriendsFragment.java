package com.vit.mychat.ui.friends;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vit.mychat.R;
import com.vit.mychat.data.model.Friend;
import com.vit.mychat.ui.base.BaseFragment;
import com.vit.mychat.ui.friends.adapter.FriendNewsAdapter;
import com.vit.mychat.ui.friends.adapter.FriendOnlineAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FriendsFragment extends BaseFragment {
    private FriendNewsAdapter mFriendNewsAdapter;
    private FriendOnlineAdapter mFriendOnlineAdapter;
    @BindView(R.id.list_friend_news)
    RecyclerView mRcvFriendNews;

    @BindView(R.id.list_friend_online)
    RecyclerView mRcvFriendOnline;


    @Override
    public int getLayoutId() {
        return R.layout.friends_fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFriendNewsAdapter = new FriendNewsAdapter(crateListFriendNews());
        mFriendOnlineAdapter = new FriendOnlineAdapter(creatListFriendOnline());
        initView();
    }

    private void initView(){
        mRcvFriendOnline.setLayoutManager(new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false));
        mRcvFriendOnline.setHasFixedSize(true);
        mRcvFriendOnline.setAdapter(mFriendOnlineAdapter);

        mRcvFriendNews.setLayoutManager(new GridLayoutManager(mainActivity, 2));
        mRcvFriendNews.setHasFixedSize(true);
        mRcvFriendNews.setAdapter(mFriendNewsAdapter);
    }
    private List<Friend> creatListFriendOnline() {
        List<Friend> listFriendOnline = new ArrayList<>();
        listFriendOnline.add(new Friend("https://tophinhanhdep.com/wp-content/uploads/2017/07/avatar-de-thuong-han-quoc-300x300.jpg", "kim Le", true, ""));
        listFriendOnline.add(new Friend("https://i.pinimg.com/originals/f4/22/36/f422363e23bb12a9b5100b4c22c6b85a.jpg", "Vu hanh", false, ""));
        listFriendOnline.add(new Friend("https://thuthuattienich.com/wp-content/uploads/2017/06/anh-dai-dien-facebook-cho-meo-de-thuong-2.jpg", "Ngoc Cham", true, ""));
        listFriendOnline.add(new Friend("https://tophinhanhdep.com/wp-content/uploads/2017/07/avatar-de-thuong-han-quoc-300x300.jpg", "Con cho" , true, ""));
        listFriendOnline.add(new Friend("https://tophinhanhdep.com/wp-content/uploads/2017/07/avatar-de-thuong-han-quoc-300x300.jpg", "Anh trai", true, ""));
        listFriendOnline.add(new Friend("https://tophinhanhdep.com/wp-content/uploads/2017/07/avatar-de-thuong-han-quoc-300x300.jpg", "Heo beo", true, ""));

        return listFriendOnline;
    }

    private List<Friend> crateListFriendNews() {
        List<Friend> listFriendNews = new ArrayList<>();
        listFriendNews.add(new Friend("https://tophinhanhdep.com/wp-content/uploads/2017/07/avatar-de-thuong-han-quoc-300x300.jpg","kimle",true, "https://thuthuattienich.com/wp-content/uploads/2017/06/anh-dai-dien-facebook-cho-meo-de-thuong-2.jpg" ));
        listFriendNews.add(new Friend("https://i.pinimg.com/originals/f4/22/36/f422363e23bb12a9b5100b4c22c6b85a.jpg","Ngoc cham",true, "https://thuthuattienich.com/wp-content/uploads/2017/06/anh-dai-dien-facebook-cho-meo-de-thuong-2.jpg" ));
        listFriendNews.add(new Friend("https://tophinhanhdep.com/wp-content/uploads/2017/07/avatar-de-thuong-han-quoc-300x300.jpg","Vu hanh",true, "https://i.pinimg.com/originals/f4/22/36/f422363e23bb12a9b5100b4c22c6b85a.jpg" ));
        listFriendNews.add(new Friend("https://tophinhanhdep.com/wp-content/uploads/2017/07/avatar-de-thuong-han-quoc-300x300.jpg","Cuc xuc",true, "https://thuthuattienich.com/wp-content/uploads/2017/06/anh-dai-dien-facebook-cho-meo-de-thuong-2.jpg" ));
        listFriendNews.add(new Friend("https://tophinhanhdep.com/wp-content/uploads/2017/07/avatar-de-thuong-han-quoc-300x300.jpg","Cho viet",true, "https://thuthuattienich.com/wp-content/uploads/2017/06/anh-dai-dien-facebook-cho-meo-de-thuong-2.jpg" ));
        listFriendNews.add(new Friend("https://tophinhanhdep.com/wp-content/uploads/2017/07/avatar-de-thuong-han-quoc-300x300.jpg","Hong hanh",true, "https://thuthuattienich.com/wp-content/uploads/2017/06/anh-dai-dien-facebook-cho-meo-de-thuong-2.jpg" ));
        return listFriendNews;
    }
}
