package vn.edu.usth.firstblood;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import vn.edu.usth.firstblood.fragments.FriendListFragment;
import vn.edu.usth.firstblood.fragments.NewsFeedFragment;

public class PagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //enable the icon at the left side of the toolbar

        //tab layout
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.newfeeds));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.friendsicon));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.messengericon));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.photoicon));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.abouticon));
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);
        //set view pager
        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        //declare Adapter
        FragmentStatePagerAdapter fragmentStatePagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        NewsFeedFragment newsFeedFragment = new NewsFeedFragment();
                        return newsFeedFragment;
                    case 1:
                        FriendListFragment friendListFragment = new FriendListFragment();
                        return friendListFragment;
                    case 2:
                        NewsFeedFragment newsFeedFragment2 = new NewsFeedFragment();
                        return newsFeedFragment2;
                    case 3:
                        NewsFeedFragment newsFeedFragment3 = new NewsFeedFragment();
                        return newsFeedFragment3;
                    case 4:
                        NewsFeedFragment newsFeedFragment4 = new NewsFeedFragment();
                        return newsFeedFragment4;
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 5;
            }
        };
        //set adapter for viewpager
        viewPager.setAdapter(fragmentStatePagerAdapter);

        //When view pager moves (changed) -> tablayout moves
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //When select a tablayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
