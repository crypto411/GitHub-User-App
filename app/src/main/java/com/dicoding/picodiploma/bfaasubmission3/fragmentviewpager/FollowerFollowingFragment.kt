package com.dicoding.picodiploma.bfaasubmission3.fragmentviewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.bfaasubmission3.R
import com.dicoding.picodiploma.bfaasubmission3.adapter.ListUserAdapter
import com.dicoding.picodiploma.bfaasubmission3.model.User
import com.dicoding.picodiploma.bfaasubmission3.retrofit.DataRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowerFollowingFragment : Fragment() {

    private lateinit var frameProgressBar: FrameLayout
    companion object {
        private const val ARG_INDEX = "fragment_index"
        private const val ARG_PARCEL = "user_parcel"
        @JvmStatic
        fun newInstance(index: Int, user: User?) =
            FollowerFollowingFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_INDEX, index)
                    putParcelable(ARG_PARCEL, user)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follower_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val index = arguments?.getInt(ARG_INDEX)
        val user = arguments?.getParcelable<User>(ARG_PARCEL)
        val rvFolData: RecyclerView = view.findViewById(R.id.rv_fol_data)
        frameProgressBar = view.findViewById(R.id.frame_progress)
        val layoutManager = LinearLayoutManager(view.context)
        val login = user?.login

        showProgressBar(true)
        val client = DataRepository.client
        if(index == 1) {
            client.followers(login!!).enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                        call: Call<ArrayList<User>>,
                        response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            val recyclerViewAdapter = ListUserAdapter(data)
                            rvFolData.layoutManager = layoutManager
                            rvFolData.adapter = recyclerViewAdapter
                            showProgressBar(false)
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    showProgressBar(false)
                }
            })
        }
        else {
            client.following(login!!).enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                        call: Call<ArrayList<User>>,
                        response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            val recyclerViewAdapter = ListUserAdapter(data)
                            rvFolData.layoutManager = layoutManager
                            rvFolData.adapter = recyclerViewAdapter
                            showProgressBar(false)
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    showProgressBar(false)
                }
            })
        }

    }

    fun showProgressBar(state: Boolean) {
        if(state)
            frameProgressBar.visibility = View.VISIBLE
        else
            frameProgressBar.visibility = View.INVISIBLE
    }
}