package com.example.terratradetest.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.landrenting.Model.LandModel
import com.example.terratradetest.R

class LandAdapter(
    private val lands: List<LandModel>
) : RecyclerView.Adapter<LandAdapter.LandViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null
    private var favoriteClickListener: ((LandModel) -> Unit)? = null

    interface OnItemClickListener {
        fun onItemClick(land: LandModel)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }

    fun setOnFavoriteClickListener(listener: (LandModel) -> Unit) {
        this.favoriteClickListener = listener
    }

    inner class LandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgRecHome: ImageView = itemView.findViewById(R.id.imgRecHome)
        private val txtRecHome: TextView = itemView.findViewById(R.id.txtRecHome)
        private val priceRecHome: TextView = itemView.findViewById(R.id.priceRecHome)
        private val userNameRecHome: TextView = itemView.findViewById(R.id.userNameRecHome)
        private val outerCardView: CardView = itemView.findViewById(R.id.outer_cardView)
        private val imgHeart: ImageView = itemView.findViewById(R.id.favRecHome)

        fun bind(land: LandModel) {
            imgRecHome.setImageResource(land.imageRes)
            txtRecHome.text = land.location
            priceRecHome.text = land.price
            userNameRecHome.text = land.userName

            imgHeart.setImageResource(
                if (land.isFavorite) R.drawable.redheard else R.drawable.heartabd
            )

            imgHeart.setOnClickListener {
                favoriteClickListener?.invoke(land)
            }

            outerCardView.setOnClickListener {
                itemClickListener?.onItemClick(land)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_property, parent, false)
        return LandViewHolder(view)
    }

    override fun onBindViewHolder(holder: LandViewHolder, position: Int) {
        holder.bind(lands[position])
    }

    override fun getItemCount(): Int = lands.size
}
