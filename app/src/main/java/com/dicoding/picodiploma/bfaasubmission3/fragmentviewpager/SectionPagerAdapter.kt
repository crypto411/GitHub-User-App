package com.dicoding.picodiploma.bfaasubmission3.fragmentviewpager

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.picodiploma.bfaasubmission3.model.User

class SectionPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    var model: User? = null
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return FollowerFollowingFragment.newInstance(position+1, model)
    }
}