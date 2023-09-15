package com.bekircaglar.yellowapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bekircaglar.yellowapp.dataclass.Post
import com.bekircaglar.yellowapp.databinding.RecyclerRowBinding
import com.bekircaglar.yellowapp.view.FeedFragmentDirections
import com.squareup.picasso.Picasso

class RecycleAdapter(val context: Context,var postlist : ArrayList<Post>):RecyclerView.Adapter<RecycleAdapter.PostHolder>(){


    class PostHolder(val binding: RecyclerRowBinding): RecyclerView.ViewHolder(binding.root){




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)    }

    override fun getItemCount(): Int {


        return postlist.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {

        var title = postlist.get(position).title
        var fuel = postlist.get(position).fuel
        var model = postlist.get(position).model
        var km = postlist.get(position).km
        var price = postlist.get(position).price
        var image = postlist.get(position).downloadurl



        holder.binding.linearLayout2.setOnClickListener {

            var  act5 = FeedFragmentDirections.actionFeedFragmentToDetailsFragment(title.toString(),fuel.toString(),km.toString(),model.toString(),price.toString(), image.toString())
            Navigation.findNavController(it).navigate(act5)


        }
        holder.binding.statementTextRow.text = title
        holder.binding.fuelTypeTextRow.text = fuel
        holder.binding.modelTextRow.text = model
        holder.binding.kmTextRow.text = km
        holder.binding.priceTextRow.text = price
        holder.binding.usernameTextRow.text = postlist.get(position).newuser
        Picasso.get().load(postlist.get(position).downloadurl).into(holder.binding.imageView)

    }


}