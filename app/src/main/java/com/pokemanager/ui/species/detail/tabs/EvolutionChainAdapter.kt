package com.pokemanager.ui.species.detail.tabs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pokemanager.databinding.EvolutionRowBothSidesBinding
import com.pokemanager.databinding.EvolutionRowCenterBinding
import com.pokemanager.databinding.EvolutionRowLeftSideBinding
import com.pokemanager.databinding.EvolutionRowRightSideBinding
import com.pokemanager.ui.species.detail.tabs.EvolutionRow.*
import com.pokemanager.utils.AndroidUtils
import com.pokemanager.utils.AndroidUtils.visibleOrGone

const val TYPE_CENTER = 1
const val TYPE_BOTH_SIDES = 2
const val TYPE_LEFT_SIDE = 3
const val TYPE_RIGHT_SIDE = 4

class EvolutionChainAdapter(
    private val rows: List<EvolutionRow>,
    private val onSpecieClicked: (id: Int) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class EvolutionRowCenterViewHolder(
        private val binding: EvolutionRowCenterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun loadRow(row: EvolutionRowCenter, position: Int) = with(binding) {
            ivArrowBelow.visibleOrGone(row.hasArrowBelow)
            ivArrowLeft.visibleOrGone(row.hasArrowSides)
            ivArrowRight.visibleOrGone(row.hasArrowSides)

            AndroidUtils.loadRowSpecie(specieCenter, row.rowSpecieCenter, onSpecieClicked)
            AndroidUtils.loadExtraBottomSpace(bottomSpace, rows.lastIndex, position)
        }
    }

    inner class EvolutionRowBothSidesViewHolder(
        private val binding: EvolutionRowBothSidesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun loadRow(row: EvolutionRowBothSides, position: Int) = with(binding) {
            ivArrowBelowLeft.visibleOrGone(row.hasArrowLeftBelow)
            ivArrowBelowRight.visibleOrGone(row.hasArrowRightBelow)

            AndroidUtils.loadRowSpecie(specieLeft, row.rowSpecieLeft, onSpecieClicked)
            AndroidUtils.loadRowSpecie(specieRight, row.rowSpecieRight, onSpecieClicked)
            AndroidUtils.loadExtraBottomSpace(bottomSpace, rows.lastIndex, position)
        }
    }

    inner class EvolutionRowLeftSideViewHolder(
        private val binding: EvolutionRowLeftSideBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun loadRow(row: EvolutionRowLeftSide, position: Int) = with(binding) {
            ivArrowRightOut.visibleOrGone(row.isRightArrowOut)
            ivArrowRightIn.visibleOrGone(!row.isRightArrowOut)

            AndroidUtils.loadRowSpecie(specieLeft, row.rowSpecieLeft, onSpecieClicked)
            AndroidUtils.loadExtraBottomSpace(bottomSpace, rows.lastIndex, position)
        }
    }

    inner class EvolutionRowRightSideViewHolder(
        private val binding: EvolutionRowRightSideBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun loadRow(row: EvolutionRowRightSide) = with(binding) {
            AndroidUtils.loadRowSpecie(specieRight, row.rowSpecieRight, onSpecieClicked)
        }
    }

    override fun getItemCount() = rows.size

    override fun getItemViewType(position: Int) =
        when (rows[position]) {
            is EvolutionRowCenter -> TYPE_CENTER
            is EvolutionRowBothSides -> TYPE_BOTH_SIDES
            is EvolutionRowLeftSide -> TYPE_LEFT_SIDE
            else -> TYPE_RIGHT_SIDE//is EvolutionRowRightSide -> TYPE_RIGHT_SIDE
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_CENTER -> {
                val binding = EvolutionRowCenterBinding.inflate(inflater, parent, false)
                EvolutionRowCenterViewHolder(binding)
            }
            TYPE_BOTH_SIDES -> {
                val binding = EvolutionRowBothSidesBinding.inflate(inflater, parent, false)
                EvolutionRowBothSidesViewHolder(binding)
            }
            TYPE_LEFT_SIDE -> {
                val binding = EvolutionRowLeftSideBinding.inflate(inflater, parent, false)
                EvolutionRowLeftSideViewHolder(binding)
            }
            else -> { //TYPE_RIGHT_SIDE -> {
                val binding = EvolutionRowRightSideBinding.inflate(inflater, parent, false)
                EvolutionRowRightSideViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val row = rows[position]
        when (holder) {
            is EvolutionRowCenterViewHolder -> {
                if (row is EvolutionRowCenter) {
                    holder.loadRow(row, position)
                }
            }
            is EvolutionRowBothSidesViewHolder -> {
                if (row is EvolutionRowBothSides) {
                    holder.loadRow(row, position)
                }
            }
            is EvolutionRowLeftSideViewHolder -> {
                if (row is EvolutionRowLeftSide) {
                    holder.loadRow(row, position)
                }
            }
            is EvolutionRowRightSideViewHolder -> {
                if (row is EvolutionRowRightSide) {
                    holder.loadRow(row)
                }
            }
        }
    }
}