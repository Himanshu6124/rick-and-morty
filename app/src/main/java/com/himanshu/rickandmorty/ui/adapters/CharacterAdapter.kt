package com.himanshu.rickandmorty.ui.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.himanshu.rickandmorty.R
import com.himanshu.rickandmorty.model.Character

class CharacterAdapter( private val onItemClick: (Character) -> Unit) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private var characters: List<Character> = emptyList()

    class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.character_name)
        val imageView: ImageView = view.findViewById(R.id.character_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.nameTextView.text = character.name
        Glide.with(holder.itemView.context)
            .load(character.image)
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.error_image)
            .into(holder.imageView)


        holder.itemView.setOnClickListener { onItemClick(character) }
    }

    override fun getItemCount() = characters.size

    fun submitList(results: List<Character>) {
        characters = results
        notifyDataSetChanged()  // this can be optimised based on dataset
    }
}
