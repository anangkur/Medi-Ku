package com.anangkur.mediku.feature.addMedicalRecord

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.base.BaseSpinnerListener
import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.util.*
import kotlinx.android.synthetic.main.activity_add_medical_record.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class AddMedicalRecordActivity: BaseActivity<AddMedicalRecordViewModel>(), AddMedicalActionListener {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, AddMedicalRecordActivity::class.java))
        }
    }

    override val mLayout: Int
        get() = R.layout.activity_add_medical_record
    override val mViewModel: AddMedicalRecordViewModel
        get() = obtainViewModel(AddMedicalRecordViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = toolbar
    override val mTitleToolbar: String?
        get() = getString(R.string.toolbar_add_medical_record)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeViewModel()
        setupSpinner(mViewModel.createCategoryList())
        btn_save.setOnClickListener {
            this.onClickSave(
                category = mViewModel.selectedCategory?:"",
                heartRate = et_heart_rate.text.toString(),
                bodyTemperature = et_temperature.text.toString(),
                bloodPressure = et_blood_pressure.text.toString(),
                diagnose = et_diagnose.text.toString()
            )
        }
        btn_select_category.setOnClickListener { this.onClickCategory() }
    }

    private fun observeViewModel(){
        mViewModel.apply {
            progressAddMedicalRecord.observe(this@AddMedicalRecordActivity, Observer {
                if (it){
                    btn_save.showProgress()
                }else{
                    btn_save.hideProgress()
                }
            })
            successAddMedicalRecord.observe(this@AddMedicalRecordActivity, Observer {
                showToastShort(getString(R.string.message_success_add_medical_report))
                finish()
            })
            errorAddMedicalRecord.observe(this@AddMedicalRecordActivity, Observer {
                showSnackbarLong(it)
            })
        }
    }

    override fun onClickSave(
        category: String,
        diagnose: String?,
        bloodPressure: String?,
        bodyTemperature: String?,
        heartRate: String?
    ) {
        validateInput(category, diagnose, bloodPressure, bodyTemperature, heartRate)
    }

    override fun onClickCategory() {
        spinner_category.performClick()
    }

    private fun validateInput(
        category: String,
        diagnose: String?,
        bloodPressure: String?,
        bodyTemperature: String?,
        heartRate: String?
    ){
        when {
            diagnose.isNullOrEmpty() -> {
                til_diagnose.setErrorMessage(getString(R.string.error_diagnose_empty))
            }
            bloodPressure.isNullOrEmpty() -> {
                til_blood_pressure.setErrorMessage(getString(R.string.error_blood_pressure_empty))
            }
            bodyTemperature.isNullOrEmpty() -> {
                til_temperature.setErrorMessage(getString(R.string.error_body_temperature_empty))
            }
            heartRate.isNullOrEmpty() -> {
                til_heart_rate.setErrorMessage(getString(R.string.error_heart_rate_empty))
            }
            else -> {
                mViewModel.addMedicalRecord(MedicalRecord(
                    category = category,
                    bloodPressure = bloodPressure.toInt(),
                    bodyTemperature = bodyTemperature.toInt(),
                    createdAt = getCurrentTimeStamp()?:"1990-01-01 00:00:00",
                    diagnosis = diagnose,
                    heartRate = heartRate.toInt(),
                    updateAt = getCurrentTimeStamp()?:"1990-01-01 00:00:00"
                ))
            }
        }
    }

    private fun setupSpinner(data: List<String>){
        spinner_category.setupSpinner(data, object: BaseSpinnerListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                setupCategoryView(parent?.selectedItem.toString())
            }
        })
    }

    private fun setupCategoryView(category: String){
        mViewModel.selectedCategory = category
        val resource = when (category){
            Const.CATEGORY_SICK -> {
                Pair(R.drawable.ic_pills, R.drawable.rect_rounded_4dp_gradient_blue)
            }
            Const.CATEGORY_HOSPITAL -> {
                Pair(R.drawable.ic_first_aid_kit, R.drawable.rect_rounded_4dp_gradient_purple)
            }
            Const.CATEGORY_CHECKUP -> {
                Pair(R.drawable.ic_healthy, R.drawable.rect_rounded_4dp_gradient_green)
            }
            else -> Pair(0,0)
        }
        iv_category.setImageResource(resource.first)
        btn_select_category.background = ContextCompat.getDrawable(this, resource.second)
        tv_category.text = category
    }

}