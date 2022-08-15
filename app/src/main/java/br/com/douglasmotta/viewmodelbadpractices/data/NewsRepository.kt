package br.com.douglasmotta.viewmodelbadpractices.data

import br.com.douglasmotta.viewmodelbadpractices.db.NewsDao
import br.com.douglasmotta.viewmodelbadpractices.db.NewsEntity
import br.com.douglasmotta.viewmodelbadpractices.db.toDomain
import br.com.douglasmotta.viewmodelbadpractices.network.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

// Nunca passar o contexto da atividade para objetos Singleton
// Passe sempre o contexto da aplicação
class NewsRepository(
    private val dispatcher: CoroutineDispatcher,
    private val newsDao: NewsDao,
    private val apiService: ApiService
) {
    val allNews = newsDao.getAll().map { newsEntityList ->
        newsEntityList.map { it.toDomain() }
    }

    suspend fun getAndStoreNews() {
        val news = apiService.getNews().news.map { title ->
            NewsEntity(title = title)
        }

        saveNews(news)
    }

    private suspend fun saveNews(newsList: List<NewsEntity>) {
        withContext(dispatcher) {
            newsDao.clearAll()
            newsDao.insertAll(newsList)
        }
    }
}