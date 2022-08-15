package br.com.douglasmotta.viewmodelbadpractices.data

import android.content.Context
import br.com.douglasmotta.viewmodelbadpractices.db.NewsDatabase
import br.com.douglasmotta.viewmodelbadpractices.db.NewsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NewsRepository {

    suspend fun saveNews(context: Context, newsList: List<NewsEntity>) {
        val database = NewsDatabase.getDatabase(context)
        withContext(Dispatchers.IO) {
            database.newsDao().clearAll()
            database.newsDao().insertAll(newsList)
        }
    }

    fun getNews(context: Context): Flow<List<NewsEntity>> {
        val database = NewsDatabase.getDatabase(context)
        return database.newsDao().getAll()
    }
}