package com.budi.setiawan.githubusersubmission3.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.budi.setiawan.githubusersubmission3.databinding.FragmentFollowBinding
import com.budi.setiawan.githubusersubmission3.R
import com.budi.setiawan.githubusersubmission3.data.adapter.UserAdapter
import com.budi.setiawan.githubusersubmission3.ui.viewmodel.FollowersViewModel

class FollowersFragment: Fragment(R.layout.fragment_follow) {
    private  var _binding : FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: UserAdapter
    private lateinit var username: String
    private val viewModel:FollowersViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowBinding.bind(view)

        adapter = UserAdapter()

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = adapter
        }

        showLoading(true)
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner) {
            if(it != null){
                adapter.setList(it)
                showLoading(false)
            }
            if(it.isEmpty()){
                showNoData(true)
            }else{
                showNoData(false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    private fun showNoData(isNoData: Boolean){
        binding.noData.visibility = if(isNoData) View.VISIBLE else View.GONE
    }
}