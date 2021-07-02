package pl.pwr.s249319.babble;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import pl.pwr.s249319.babble.Fragments.ChatFragment;
import pl.pwr.s249319.babble.Fragments.UsersFragment;

public class FragmentAdapter extends FragmentStateAdapter {

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
//  Top tabs (All users, Chats)
//        if (position == 1) {
//            return new ChatFragment();
//        }
        return new UsersFragment();
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}