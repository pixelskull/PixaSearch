package de.pixelskull.pixasearch.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arlib.floatingsearchview.FloatingSearchView
import de.pixelskull.pixasearch.R
import de.pixelskull.pixasearch.adapter.SearchListAdapter
import de.pixelskull.pixasearch.databinding.FragmentSearchBinding
import de.pixelskull.pixasearch.extensions.OnItemClickListener
import de.pixelskull.pixasearch.extensions.addOnItemClickListener

import de.pixelskull.pixasearch.model.SearchResult
import de.pixelskull.pixasearch.viewmodel.SearchFragmentModel

class SearchFragment : Fragment() {
    private var data: List<SearchResult> = listOf()
    private var searchModel: SearchFragmentModel? = null
    private lateinit var searchView: FloatingSearchView
    private lateinit var searchRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        searchModel = ViewModelProvider(this).get(SearchFragmentModel::class.java)
        searchModel?.searchResults?.observe(viewLifecycleOwner, Observer {
            if (it.hits.isNotEmpty()) {
                Log.d("SEARCHRESULT:", it.hits.first().user)
                data = it.hits
                searchRecyclerView.adapter = SearchListAdapter(data)
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
        searchRecyclerView = view.findViewById(R.id.search_list_recyclerview)
        searchRecyclerView.adapter = SearchListAdapter(data)
        searchRecyclerView.layoutManager = LinearLayoutManager(context)

        searchRecyclerView.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                val toShow = searchModel?.searchResults?.value?.hits?.get(position)
                val dialog = AlertDialog.Builder(context)
                    .setMessage("Do you want to see more infos?")
                    .setPositiveButton("Open!") { _, _ ->
                        toShow?.let {
                            val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(it)
                            Navigation.findNavController(view).navigate(action)
                        }
                    }
                    .setCancelable(true)
                    .create()
                dialog.show()
            }
        })

        searchModel?.setSearchTerm("fruits") // Default Search
        // using search
        searchView.setOnQueryChangeListener { _, newQuery -> searchModel?.setSearchTerm(newQuery) }
        searchView.setOnClearSearchActionListener { searchModel?.setSearchTerm("fruits") } // revert to default
    }

    override fun onDestroy() {
        super.onDestroy()
        searchModel?.cancelJobs()
    }
}