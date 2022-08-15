package br.com.douglasmotta.viewmodelbadpractices.network

class ApiService {
    fun getNews() = NewsResponse(listOf("Title News 1", "Title News 2"))
}