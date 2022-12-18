package com.pokemanager.ui.species

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pokemanager.R
import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.databinding.PokeSpecieItemBinding

class PokeSpecieAdapter : RecyclerView.Adapter<PokeSpecieAdapter.PokeSpecieViewHolder>() {

    inner class PokeSpecieViewHolder(private val binding: PokeSpecieItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun loadPokeSpecie(pokeSpecie: PokeSpecieItemDomain) {
            binding.pokeSpecieItemName.text = pokeSpecie.name
            binding.pokeSpecieItemNumber.text = pokeSpecie.id.toString()

            val types = StringBuilder()
            val separator = "-"
            pokeSpecie.types?.forEach { types.append(it.name).append(separator) }
            binding.pokeSpecieItemTypes.text = types.removeSuffix(separator).toString()

            Glide.with(binding.root).load(pokeSpecie.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)//image while the image is loading
                //.diskCacheStrategy(DiskCacheStrategy.ALL)//in case it's having problems when loading the image
                .into(binding.pokeSpecieItemImage)
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<PokeSpecieItemDomain>() {
        override fun areItemsTheSame(
            oldItem: PokeSpecieItemDomain,
            newItem: PokeSpecieItemDomain
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: PokeSpecieItemDomain,
            newItem: PokeSpecieItemDomain
        ) = oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<PokeSpecieItemDomain>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeSpecieViewHolder {
        val binding = PokeSpecieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokeSpecieViewHolder(binding)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: PokeSpecieViewHolder, position: Int) {
        val pokeSpecie = differ.currentList[position]
        holder.loadPokeSpecie(pokeSpecie)
    }
}

