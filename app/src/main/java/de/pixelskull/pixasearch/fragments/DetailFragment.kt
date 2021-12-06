package de.pixelskull.pixasearch.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import de.pixelskull.pixasearch.model.SearchResult
import de.pixelskull.pixasearch.R
import de.pixelskull.pixasearch.databinding.FragmentDetailBinding
import de.pixelskull.pixasearch.databinding.FragmentSearchBinding
import de.pixelskull.pixasearch.viewmodel.DetailFragmentModel
import de.pixelskull.pixasearch.viewmodel.SearchFragmentModel
import org.w3c.dom.Text

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "searchResult"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {

    private lateinit var detailModel: DetailFragmentModel

    private lateinit var imageView: ImageView
    private lateinit var downloadsView: TextView
    private lateinit var commentsView: TextView
    private lateinit var likesView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        detailModel = ViewModelProvider(this).get(DetailFragmentModel::class.java)
        val binding: FragmentDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.lifecycleOwner = this
        binding.detailModel = detailModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            val arg = arguments
            val args = DetailFragmentArgs.fromBundle(arg!!)
            detailModel.searchResult.value = args.displayedResult

            Log.d("RESULT:", detailModel.searchResult.value!!.user)
        }

        imageView = view.findViewById(R.id.detail_imageview)
        downloadsView = view.findViewById(R.id.detail_downloads)
        commentsView = view.findViewById(R.id.detail_comments)
        likesView = view.findViewById(R.id.detail_likes)

        Picasso.get()
            .load(detailModel.searchResult.value?.largeImageURL)
            .into(imageView)

        downloadsView.text = "Downloads: " + detailModel.searchResult.value?.downloads.toString()
        commentsView.text = "Comments: " + detailModel.searchResult.value?.comments.toString()
        likesView.text = "Likes: " + detailModel.searchResult.value?.likes.toString()
    }
}