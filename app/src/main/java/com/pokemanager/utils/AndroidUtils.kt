package com.pokemanager.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.pokemanager.R
import com.pokemanager.data.domain.PokeTypeDomain
import com.pokemanager.databinding.EvolutionRowSpecieBinding
import com.pokemanager.ui.species.detail.tabs.EvolutionRowSpecie
import com.pokemanager.ui.species.PokeTypeUi

object AndroidUtils {

    fun loadImage(root: View, imageUrl: String, pokeSpecieDetailImage: ImageView) {
        Glide.with(root).load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background)//image while the image is loading
            //.diskCacheStrategy(DiskCacheStrategy.ALL)//in case it's having problems when loading the image
            .into(pokeSpecieDetailImage)
    }

    fun loadTypes(
        types: MutableList<PokeTypeDomain>,
        tvType1: AppCompatTextView,
        tvType2: AppCompatTextView
    ) {
        for (index in types.indices) {
            val typeDomain = types[index]
            val typeUi = getTypeUi(typeDomain)
            if (typeUi != null) {
                loadOneType(if (index == 0) tvType1 else tvType2, typeUi)
            }
        }
        if (types.size == 1) {
            tvType2.visibility = View.INVISIBLE
        }
    }

    private fun loadOneType(tvType: AppCompatTextView, typeUi: PokeTypeUi) = with(typeUi) {
        tvType.apply {
            setText(uiNameId)
            setBackgroundResource(backgroundId)
            setTextColor(ContextCompat.getColor(context, textColorId))
            visibility = View.VISIBLE
        }
    }

    private fun getTypeUi(typeDomain: PokeTypeDomain): PokeTypeUi? {
        for (typeUi in PokeTypeUi.values()) {
            if (typeDomain.id == typeUi.id) {
                return typeUi
            }
        }
        return null
    }

    fun TextView.setUpTextViewForNameItem(text: String, isVisible: Boolean) {
        this.text = text.firstToUpperCase()
        this.isVisible = isVisible
    }

    fun TextView.setUpTextViewForNameDetail(textToSet : String) {
        text = textToSet
        visibility = if (textToSet.isBlank()) View.GONE else View.VISIBLE
    }

    fun View.visibleOrGone(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun ImageView.setTapBackground(isSelected: Boolean) {
        setImageResource(if (isSelected)
            R.drawable.detail_poke_specie_tab_button_selected
        else
            R.drawable.detail_poke_specie_tab_button_unselected
        )
    }

    fun loadRowSpecie(
        specie: EvolutionRowSpecieBinding,
        rowSpecie: EvolutionRowSpecie,
        root: ConstraintLayout,
        onSpecieClicked: (id: Int) -> Unit
    ) = with(specie) {
        val id = rowSpecie.id
        tvNumber.text = id.toString()
        tvName.text = VisualUtils.convertName(id, rowSpecie.englishName)
        ivIcon.setBackgroundResource(
            if (rowSpecie.isSelected)
                R.drawable.evolution_row_specie_image_background_selected
            else
                R.drawable.evolution_row_specie_image_background
        )
        loadImage(root, rowSpecie.imageUrl, ivIcon)
        root.setOnClickListener { onSpecieClicked.invoke(id) }

    }

    fun loadExtraBottomSpace(root: View, lastPosition: Int, position: Int) {
        root.visibleOrGone(position == lastPosition)
    }
}