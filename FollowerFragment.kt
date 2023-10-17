package com.dicoding.githubuserapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserapp.data.response.ItemsItem
import com.dicoding.githubuserapp.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {
    private lateinit var binding: FragmentFollowerBinding
    private var position: Int=0
    private var username: String? = null
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowerBinding.inflate(inflater,container, false)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFragment.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvFragment.addItemDecoration(itemDecoration)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        var username = arguments?.getString(ARG_USERNAME)
        var number = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        
        arguments?.let{
           position = it.getInt(ARG_SECTION_NUMBER)
            username = it.getString(ARG_USERNAME)
        }
        if(position ==1){
            showLoading(true)
            viewModel.getFollowers(username.toString())
            viewModel.followers.observe(viewLifecycleOwner){ followers->
                setUsersData(followers)
            }
        }else{
            viewModel.getFollowing(username.toString())
            viewModel.following.observe(viewLifecycleOwner){following->
                setUsersData(following)
            }
        }
    }
    private fun setUsersData(items: List<ItemsItem>){
    binding.rvFragment.layoutManager=LinearLayoutManager(requireContext())
        val adapter = ListUserAdapter()
        adapter.submitList(items)
        binding.rvFragment.adapter = adapter
        showLoading(false)
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }
}