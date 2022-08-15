package br.com.douglasmotta.viewmodelbadpractices.ui.main

import androidx.lifecycle.*
import br.com.douglasmotta.viewmodelbadpractices.data.NewsRepository
import br.com.douglasmotta.viewmodelbadpractices.domain.News
import kotlinx.coroutines.launch

class MainViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val newsEvent: LiveData<List<News>> =
        newsRepository.allNews.asLiveData(viewModelScope.coroutineContext)

    init {
        viewModelScope.launch {
            newsRepository.getAndStoreNews()
        }
    }

    class MainViewModelFactory(
        private val newsRepository: NewsRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(
                NewsRepository::class.java
            ).newInstance(newsRepository)
        }
    }
}