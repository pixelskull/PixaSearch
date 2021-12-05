package de.pixelskull.pixasearch.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arlib.floatingsearchview.FloatingSearchView
import de.pixelskull.pixasearch.R
import de.pixelskull.pixasearch.adapter.SearchListAdapter
import de.pixelskull.pixasearch.databinding.FragmentSearchBinding
import de.pixelskull.pixasearch.model.SearchResult
import de.pixelskull.pixasearch.viewmodel.SearchFragmentModel
import kotlinx.coroutines.GlobalScope

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    private var param1: String? = null
    private var data: List<SearchResult> = listOf()

    private lateinit var searchModel: SearchFragmentModel

    private lateinit var searchView: FloatingSearchView
    private lateinit var searchRecylerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        searchModel = ViewModelProvider(this).get(SearchFragmentModel::class.java)
        searchModel.searchResults.observe(viewLifecycleOwner, Observer {
            if (it.hits.isNotEmpty()) {
                Log.d("SEARCHRESULT:", it.hits.first().user)
                data = it.hits
                searchRecylerView.adapter = SearchListAdapter(data)
            }
        })

        val binding: FragmentSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = searchModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = view.findViewById(R.id.floating_search_view)
        searchRecylerView = view.findViewById(R.id.search_list_recyclerview)
        searchRecylerView.adapter = SearchListAdapter(data)
        searchRecylerView.layoutManager = LinearLayoutManager(context)

        searchModel.setSearchTerm("fruits") // Default Search
        // using search
        searchView.setOnQueryChangeListener { _, newQuery -> searchModel.setSearchTerm(newQuery) }
        searchView.setOnClearSearchActionListener { searchModel.setSearchTerm("fruits") } // revert to default
    }

    override fun onDestroy() {
        super.onDestroy()
        searchModel.cancelJobs()
    }
}