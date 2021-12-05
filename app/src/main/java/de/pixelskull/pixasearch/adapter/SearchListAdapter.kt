package de.pixelskull.pixasearch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.pixelskull.pixasearch.R
import de.pixelskull.pixasearch.model.SearchResult

class SearchListAdapter(private val mList: List<SearchResult>) : RecyclerView.Adapter<SearchListAdapter.ViewHolder>()  {

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.card_imageview)
        val userTextView: TextView = itemView.findViewById(R.id.card_user_textview)
        val imageTagView: TextView = itemView.findViewById(R.id.card_tag_textview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_design, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchResult = mList[position]

        Picasso.get()
            .load(searchResult.previewURL)
            .resize(60, 60)
            .centerCrop()
            .into(holder.imageView)
        holder.userTextView.text = searchResult.user
        holder.imageTagView.text = searchResult.tags
    }

    override fun getItemCount(): Int { return mList.count() }
}