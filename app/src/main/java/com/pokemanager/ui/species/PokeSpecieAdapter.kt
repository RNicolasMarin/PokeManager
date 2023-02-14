package com.pokemanager.ui.species

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pokemanager.R
import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.databinding.PokeSpecieItemBinding
import com.pokemanager.ui.species.PokeSpecieAdapter.*
import com.pokemanager.utils.NameLanguagesToList

class PokeSpecieAdapter(
    val nameLanguages: NameLanguagesToList,
    val listener: PokeSpecieAdapterListener
) : PagingDataAdapter<PokeSpecieItemDomain, PokeSpecieViewHolder>(diffCallback) {

    inner class PokeSpecieViewHolder(private val binding: PokeSpecieItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun loadPokeSpecie(pokeSpecie: PokeSpecieItemDomain?) = with(binding) {
            if (pokeSpecie == null) return@with

            pokeSpecieItemEnglishName.setUpTextViewForName(pokeSpecie.englishName, nameLanguages.showEnglishName)
            pokeSpecieItemJapHrKtName.setUpTextViewForName(pokeSpecie.japHrKtName, nameLanguages.showJapHrKtName)
            pokeSpecieItemJapRoomajiName.setUpTextViewForName(pokeSpecie.japRoomajiName, nameLanguages.showJapRoomajiName)

            pokeSpecieItemNumber.text = pokeSpecie.id.toString()

            val types = getTypes(pokeSpecie)
            pokeSpecieItemTypes.text = types

            Glide.with(root).load(pokeSpecie.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)//image while the image is loading
                //.diskCacheStrategy(DiskCacheStrategy.ALL)//in case it's having problems when loading the image
                .into(pokeSpecieItemImage)

            root.setOnClickListener { listener.onPokeSpecieClicked(pokeSpecie.id) }
        }

        private fun TextView.setUpTextViewForName(text: String, isVisible: Boolean) {
            this.text = text
            this.isVisible = isVisible
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

    interface PokeSpecieAdapterListener {
        fun onPokeSpecieClicked(id: Int)
    }

}

