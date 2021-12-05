package de.pixelskull.pixasearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import de.pixelskull.pixasearch.model.SearchResponse
import de.pixelskull.pixasearch.repository.SearchRepository

class SearchFragmentModel: ViewModel() {

    private val _searchTerm = MutableLiveData<String>()

    val searchResults: LiveData<SearchResponse> = Transformations
        .switchMap(_searchTerm) { searchTerm ->
        SearchRepository.startSearch(searchTerm)
    }

    fun setSearchTerm(newSearchTerm: String) {
        if (_searchTerm.value == newSearchTerm) {
            return
        }
        _searchTerm.value = newSearchTerm
    }

    fun cancelJobs() {
        SearchRepository.cancelJobs()
    }
}