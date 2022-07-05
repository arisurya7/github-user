package com.arisurya.project.githubuser3.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arisurya.project.githubuser3.R
import com.arisurya.project.githubuser3.data.model.User
import com.arisurya.project.githubuser3.databinding.FragmentFollowBinding
import com.arisurya.project.githubuser3.ui.main.ListUserAdapter

class FollowingFragment : Fragment(R.layout.fragment_follow) {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()

        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowBinding.bind(view)

        recyclerViewFollowing()
        viewModelFollowing()
    }

    private fun recyclerViewFollowing() {
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnClickCallback(object : ListUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) { return }
        })

        binding?.rvUser?.setHasFixedSize(true)
        binding?.rvUser?.layoutManager = LinearLayoutManager(activity)
        binding?.rvUser?.adapter = adapter
    }

    private fun viewModelFollowing() {
        showProgressBar(true)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)
        viewModel.setListFollowing(username)
        viewModel.getListFollowing().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setData(it)
                showProgressBar(false)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showProgressBar(state: Boolean) {
        if (state) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }


}