package com.anangkur.mediku.feature.covid19

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.data.model.BaseResult
import com.anangkur.mediku.data.model.covid19.Covid19ApiResponse
import com.anangkur.mediku.data.model.covid19.Covid19Data
import com.anangkur.mediku.feature.covid19.adapter.CovidHorizontalAdapter
import com.anangkur.mediku.feature.covid19.adapter.CovidVerticalAdapter
import com.anangkur.mediku.util.*
import kotlinx.android.synthetic.main.activity_covid.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.text.SimpleDateFormat
import java.util.*

class CovidActivity : BaseActivity<CovidViewModel>() {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, CovidActivity::class.java))
        }
    }

    override val mLayout: Int
        get() = R.layout.activity_covid
    override val mViewModel: CovidViewModel
        get() = obtainViewModel(CovidViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = toolbar
    override val mTitleToolbar: String?
        get() = getString(R.string.toolbar_covid_19)

    private lateinit var otherCountryAdapter: CovidVerticalAdapter
    private lateinit var yourCountryAdapter: CovidHorizontalAdapter
    private lateinit var topCountryAdapter: CovidHorizontalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tv_data_shown.text = getString(R.string.label_data_shown, SimpleDateFormat(Const.DAY_FULL_WITH_DATE_LOCALE, Locale.US).format(
            getTimeYesterday()
        ))
        setupOtherCountryAdapter()
        setupYourCountryAdapter()
        setupTopCountryAdapter()
        observeViewModel()
        mViewModel.getCovid19Data(SimpleDateFormat(Const.DATE_ENGLISH_YYYY_MM_DD, Locale.US).format(
            getTimeYesterday()
        ))
        swipe_covid.setOnRefreshListener {
            mViewModel.getCovid19Data(SimpleDateFormat(Const.DATE_ENGLISH_YYYY_MM_DD, Locale.US).format(
                getTimeYesterday()
            ))
            swipe_covid.isRefreshing = false
        }
    }

    private fun observeViewModel(){
        mViewModel.apply {
            covidLiveData.observe(this@CovidActivity, Observer {
                when (it.status){
                    BaseResult.Status.LOADING -> {
                        setupLoadingGeneral(true)
                    }
                    BaseResult.Status.ERROR -> {
                        setupLoadingGeneral(false)
                        showSnackbarLong(it.message?:"")
                    }
                    BaseResult.Status.SUCCESS -> {
                        setupLoadingGeneral(false)
                        val date = SimpleDateFormat(Const.DATE_ENGLISH_YYYY_MM_DD, Locale.US).format(
                            getTimeYesterday()
                        )
                        setupDataOtherCountryToView(it.data!!)
                        getCovid19DataOnYourCountry(country.convertCoutryCodeIsoToCountryName(), date)
                        getCovid19DataTopCountry(date)
                    }
                }
            })
            covid19DataOnYourCountryLive.observe(this@CovidActivity, Observer {
                when (it.status){
                    BaseResult.Status.LOADING -> {
                        setupLoadingYourCountry(true)
                    }
                    BaseResult.Status.SUCCESS -> {
                        setupLoadingYourCountry(false)
                        yourCountryAdapter.setRecyclerData(it.data!!)
                    }
                    BaseResult.Status.ERROR -> {
                        setupLoadingYourCountry(false)
                        showSnackbarLong(it.message?:"")
                    }
                }
            })
            covid19DataTopCountry.observe(this@CovidActivity, Observer {
                when (it.status){
                    BaseResult.Status.LOADING -> {
                        setupLoadingTopCountry(true)
                    }
                    BaseResult.Status.SUCCESS -> {
                        setupLoadingTopCountry(false)
                        topCountryAdapter.setRecyclerData(it.data!!)
                    }
                    BaseResult.Status.ERROR -> {
                        setupLoadingTopCountry(false)
                        showSnackbarLong(it.message?:"")
                    }
                }
            })
        }
    }

    private fun setupOtherCountryAdapter(){
        otherCountryAdapter = CovidVerticalAdapter()
        recycler_other_country.apply {
            adapter = otherCountryAdapter
            setupRecyclerViewLinear(this@CovidActivity, RecyclerView.VERTICAL)
        }
    }

    private fun setupYourCountryAdapter(){
        yourCountryAdapter = CovidHorizontalAdapter()
        recycler_your_country.apply {
            adapter = yourCountryAdapter
            setupRecyclerViewLinear(this@CovidActivity, RecyclerView.HORIZONTAL)
        }
    }

    private fun setupTopCountryAdapter(){
        topCountryAdapter = CovidHorizontalAdapter()
        recycler_top_country.apply {
            adapter = topCountryAdapter
            setupRecyclerViewLinear(this@CovidActivity, RecyclerView.HORIZONTAL)
        }
    }

    private fun setupDataOtherCountryToView(listCovid19Data: List<Covid19Data>){
        otherCountryAdapter.setRecyclerData(listCovid19Data)
        val statCovid = mViewModel.getStatCovid(listCovid19Data)
        tv_stat_confirmed.text = statCovid.first.formatThousandNumber()
        tv_stat_death.text = statCovid.second.formatThousandNumber()
        tv_stat_recover.text = statCovid.third.formatThousandNumber()
    }

    private fun setupLoadingGeneral(isLoading: Boolean){
        if (isLoading){
            pb_stat_confirmed.visible()
            pb_stat_death.visible()
            pb_stat_recover.visible()
            pb_layout_content.visible()
            layout_content.gone()
            tv_label_stat_confirmed.gone()
            tv_label_stat_death.gone()
            tv_label_stat_recover.gone()
            tv_stat_confirmed.gone()
            tv_stat_death.gone()
            tv_stat_recover.gone()
        }else{
            pb_stat_confirmed.gone()
            pb_stat_death.gone()
            pb_stat_recover.gone()
            pb_layout_content.gone()
            layout_content.visible()
            tv_label_stat_confirmed.visible()
            tv_label_stat_death.visible()
            tv_label_stat_recover.visible()
            tv_stat_confirmed.visible()
            tv_stat_death.visible()
            tv_stat_recover.visible()
        }
    }

    private fun setupLoadingYourCountry(isLoading: Boolean){
        if (isLoading){
            pb_layout_content.visible()
            layout_content.gone()
        }else{
            pb_layout_content.gone()
            layout_content.visible()
        }
    }

    private fun setupLoadingTopCountry(isLoading: Boolean){
        if (isLoading){
            pb_layout_content.visible()
            layout_content.gone()
        }else{
            pb_layout_content.gone()
            layout_content.visible()
        }
    }
}
