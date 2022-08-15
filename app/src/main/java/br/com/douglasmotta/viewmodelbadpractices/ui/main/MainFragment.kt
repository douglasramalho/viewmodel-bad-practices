package br.com.douglasmotta.viewmodelbadpractices.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import br.com.douglasmotta.viewmodelbadpractices.R
import br.com.douglasmotta.viewmodelbadpractices.data.NewsRepository
import br.com.douglasmotta.viewmodelbadpractices.databinding.MainFragmentBinding
import br.com.douglasmotta.viewmodelbadpractices.network.ApiService
import kotlinx.coroutines.Dispatchers

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.main
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModelFactory = MainViewModel.MainViewModelFactory(
            requireContext(),
            NewsRepository(),
            ApiService()
        )

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        viewModel.newsEvent.observe(viewLifecycleOwner) { newsList ->
            val titles = newsList.map {
                "${it.title}\n"
            }

            binding.news.text = ""
            titles.forEach {
                binding.news.text = "${binding.news.text} $it"
            }
        }

        viewModel.getNews()
    }
}