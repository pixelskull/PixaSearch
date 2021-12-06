package de.pixelskull.pixasearch.viewmodel

import de.pixelskull.pixasearch.model.SearchResult
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailFragmentModel: ViewModel() {
     val searchResult = MutableLiveData<SearchResult>()
}