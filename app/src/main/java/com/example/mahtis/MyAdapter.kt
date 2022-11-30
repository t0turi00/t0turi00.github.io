package com.example.mahtis

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyAdapter(private val mContext: Context, private var myFoodList: MutableList<FoodData?>?) :
    RecyclerView.Adapter<FoodViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): FoodViewHolder {
        val mView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_row_item, viewGroup, false)
        return FoodViewHolder(mView)
    }

    override fun onBindViewHolder(foodViewHolder: FoodViewHolder, i: Int) {
        Glide.with(mContext).load(myFoodList?.get(i)?.itemImage).into(foodViewHolder.imageView)

        // foodViewHolder.imageView.setImageResource(myFoodList.get(i).getItemImage());
        foodViewHolder.mTitle.text = myFoodList!![i]?.itemName
        foodViewHolder.mDescription.text = myFoodList!![i]?.itemDescription
        foodViewHolder.mDescription2.text = myFoodList!![i]?.itemDescription2
        foodViewHolder.mRating.text = myFoodList!![i]?.itemRating
        foodViewHolder.mCardView.setOnClickListener { view: View? ->
            val intent = Intent(mContext, DetailActivity::class.java)
            intent.putExtra("Image", myFoodList!![foodViewHolder.adapterPosition]?.itemImage)
            intent.putExtra(
                "RecipeName",
                myFoodList!![foodViewHolder.adapterPosition]?.itemName
            )
            intent.putExtra(
                "Description",
                myFoodList!![foodViewHolder.adapterPosition]?.itemDescription
            )
            intent.putExtra(
                "Instructions",
                myFoodList!![foodViewHolder.adapterPosition]?.itemDescription2
            )
            intent.putExtra(
                "Rating",
                myFoodList!![foodViewHolder.adapterPosition]?.itemRating
            )
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return myFoodList!!.size
    }

    fun filteredList(filterlist: ArrayList<FoodData?>) {
        myFoodList = filterlist
        notifyDataSetChanged()
    }
}

class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var imageView: ImageView
    var mTitle: TextView
    var mDescription: TextView
    var mDescription2: TextView
    var mRating: TextView
    var mCardView: CardView

    init {
        imageView = itemView.findViewById(R.id.ivImage)
        mTitle = itemView.findViewById(R.id.tvTitle)
        mDescription = itemView.findViewById(R.id.tvDescription)
        mDescription2 = itemView.findViewById(R.id.tvDescription2)
        mRating = itemView.findViewById(R.id.tvRating)
        mCardView = itemView.findViewById(R.id.myCardView)
    }
}