package com.himanshu.rickandmorty
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.himanshu.rickandmorty.model.Character

class CharacterAdapter(private val characters: List<Character>, private val onItemClick: (Character) -> Unit) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

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
        Glide.with(holder.itemView.context).load(character.image).into(holder.imageView)
        holder.itemView.setOnClickListener { onItemClick(character) }
    }

    override fun getItemCount() = characters.size
}
