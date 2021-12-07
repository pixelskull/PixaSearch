package de.pixelskull.pixasearch.repository

import android.app.appsearch.SearchResult
import androidx.lifecycle.LiveData
import de.pixelskull.pixasearch.api.RetrofitBuilder
import de.pixelskull.pixasearch.model.SearchResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

object SearchRepository {

    var job: CompletableJob? = null

    fun startSearch(searchTerm: String): LiveData<SearchResponse> {
        job = Job()
        return object : LiveData<SearchResponse>() {
            override fun onActive() {
                super.onActive()
                job?.let { currentJob ->
                    CoroutineScope(IO + currentJob).launch {
                        val search = RetrofitBuilder.searchService.search(searchTerm)
                        withContext(Main) {
                            value = search
                            currentJob.complete()
                        } // look
                    } // at
                } // this
            } // pyramid
        } // of
    } // doom

    fun cancelJobs() {
        job?.cancel()
    }
}