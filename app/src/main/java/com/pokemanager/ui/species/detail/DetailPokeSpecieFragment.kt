package com.pokemanager.ui.species.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.pokemanager.databinding.FragmentDetailPokeSpecieBinding
import com.pokemanager.ui.species.detail.tabs.DetailPokeSpecieViewPagerAdapter
import com.pokemanager.utils.AndroidUtils.setTapBackground
import com.pokemanager.utils.AndroidUtils.setUpTextViewForNameDetail
import com.pokemanager.utils.DataState.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DetailPokeSpecieFragment : Fragment() {

    private val viewModel: DetailPokeSpecieViewModel by viewModels()
    private val args: DetailPokeSpecieFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailPokeSpecieBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailPokeSpecieBinding.inflate(inflater, container, false)

        setUpViews()

        return binding.root
    }

    private fun setUpViews() = with(binding) {
        val viewPagerAdapter = DetailPokeSpecieViewPagerAdapter(parentFragmentManager, lifecycle)
        with(viewModel) {
            loadPokeSpecieData(args.pokeSpecieId)

            lifecycleScope.launchWhenCreated {
                pokeSpecieDetail.collectLatest {
                    loadingContainer.isVisible = it is Loading
                    errorContainer.isVisible = it is Error
                    successContainer.isVisible = it is Success

                    if (it is Success) {
                        val pokeSpecie = it.data
                        //show in each tab
                        loadNames()

                        with(viewPager) {
                            adapter = viewPagerAdapter
                            viewPagerAdapter.setPokeSpecie(pokeSpecie, isThereMultipleForms()) { changeForm() }
                            registerOnPageChangeCallback(object : OnPageChangeCallback() {
                                override fun onPageSelected(position: Int) {
                                    setPagePosition(position)
                                    super.onPageSelected(position)
                                }
                            })
                        }
                    }
                }
            }

            lifecycleScope.launchWhenCreated {
                evolutionToShow.collectLatest {
                    //show in tab 4
                    if (it is Success) {
                        viewPagerAdapter.setEvolutionRows(it.data) { id ->
                            loadPokeSpecieData(id)
                        }
                    }
                }
            }

            setSwipePointButton(selectedTabOne, ivTab1, viewPager, 0)
            setSwipePointButton(selectedTabTwo, ivTab2, viewPager, 1)
            setSwipePointButton(selectedTabThree, ivTab3, viewPager, 2)
            setSwipePointButton(selectedTabFour, ivTab4, viewPager, 3)

            btnPrevious.setOnClickListener { loadPreviousPokeSpecieData() }
            btnNext.setOnClickListener { loadNextPokeSpecieData() }
            errorBtnRetry.setOnClickListener { loadPokeSpecieData(args.pokeSpecieId) }
        }
    }

    private fun setSwipePointButton(
        selectedTab: StateFlow<Boolean>,
        ivTab: AppCompatImageView,
        viewPager: ViewPager2,
        value: Int
    ) = with(ivTab) {
        lifecycleScope.launchWhenCreated {
            selectedTab.collectLatest {
                setTapBackground(it)
            }
        }
        setOnClickListener { viewPager.currentItem = value }
    }

    private fun loadNames() = with(binding) {
        val namesToShow = viewModel.getNamesByLanguage()
        with(namesToShow) {
            tvName1.isSelected = true
            tvName1.setUpTextViewForNameDetail(name1)
            tvName2.setUpTextViewForNameDetail(name2)
            tvName3.setUpTextViewForNameDetail(name3)
        }
    }

}