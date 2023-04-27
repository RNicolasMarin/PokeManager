package com.pokemanager.ui.species

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.databinding.PokeSpecieItemBinding
import com.pokemanager.ui.species.PokeSpecieAdapter.*
import com.pokemanager.utils.AndroidUtils
import com.pokemanager.utils.AndroidUtils.setUpTextViewForNameItem
import com.pokemanager.utils.NameLanguagesToList
import com.pokemanager.utils.VisualUtils

class PokeSpecieAdapter(
    val nameLanguages: NameLanguagesToList,
    val listener: PokeSpecieAdapterListener
) : PagingDataAdapter<PokeSpecieItemDomain, PokeSpecieViewHolder>(diffCallback) {

    inner class PokeSpecieViewHolder(private val binding: PokeSpecieItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun loadPokeSpecie(pokeSpecie: PokeSpecieItemDomain?) = with(binding) {
            if (pokeSpecie == null) return@with

            tvNameEnglish.setUpTextViewForNameItem(VisualUtils.convertName(pokeSpecie.id, pokeSpecie.englishName), nameLanguages.showEnglishName)
            tvNameKana.setUpTextViewForNameItem(pokeSpecie.japHrKtName, nameLanguages.showKanaName)
            tvNameRoomaji.setUpTextViewForNameItem(pokeSpecie.japRoomajiName, nameLanguages.showRoomajiName)

            tvNumber.text = pokeSpecie.id.toString()

            AndroidUtils.loadTypes(pokeSpecie.types, tvType1, tvType2)

            AndroidUtils.loadImage(root, pokeSpecie.imageUrl, ivImage)

            root.setOnClickListener { listener.onPokeSpecieClicked(pokeSpecie.id) }
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

