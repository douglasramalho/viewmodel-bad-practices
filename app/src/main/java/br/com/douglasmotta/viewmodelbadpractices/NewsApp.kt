package br.com.douglasmotta.viewmodelbadpractices

import android.app.Application
import br.com.douglasmotta.viewmodelbadpractices.db.NewsDatabase

class NewsApp : Application() {
    val database: NewsDatabase by lazy { NewsDatabase.getDatabase(this) }
}