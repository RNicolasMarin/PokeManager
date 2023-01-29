package com.pokemanager.ui.species

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pokemanager.R
import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.databinding.PokeSpecieItemBinding
import com.pokemanager.ui.species.PokeSpecieAdapter.*

class PokeSpecieAdapter : PagingDataAdapter<PokeSpecieItemDomain, PokeSpecieViewHolder>(diffCallback) {

    inner class PokeSpecieViewHolder(private val binding: PokeSpecieItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun loadPokeSpecie(pokeSpecie: PokeSpecieItemDomain?) = with(binding) {
            if (pokeSpecie == null) return@with
            pokeSpecieItemName.text = pokeSpecie.name
            pokeSpecieItemNumber.text = pokeSpecie.id.toString()

            val types = getTypes(pokeSpecie)
            pokeSpecieItemTypes.text = types

            Glide.with(root).load(pokeSpecie.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)//image while the image is loading
                //.diskCacheStrategy(DiskCacheStrategy.ALL)//in case it's having problems when loading the image
                .into(pokeSpecieItemImage)
        }

        private fun getTypes(pokeSpecie: PokeSpecieItemDomain): String {
            val types = StringBuilder()
            val separator = "-"
            pokeSpecie.types.forEach { types.append(it.name).append(separator) }
            return types.removeSuffix(separator).toString()
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<PokeSpecieItemDomain>() {
            override fun areItemsTheSame(
                oldItem: PokeSpecieItemDomain,
                newItem: PokeSpecieItemDomain
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: PokeSpecieItemDomain,
                newItem: PokeSpecieItemDomain
            ) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeSpecieViewHolder {
        val binding = PokeSpecieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokeSpecieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokeSpecieViewHolder, position: Int) {
        val pokeSpecie = getItem(position)
        holder.loadPokeSpecie(pokeSpecie)
    }

}

