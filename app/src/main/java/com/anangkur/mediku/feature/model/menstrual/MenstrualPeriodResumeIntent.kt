package com.anangkur.mediku.feature.model.menstrual

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MenstrualPeriodResumeIntent(
    val year: String = "",
    val month: String = "",
    val firstDayPeriod: String = "",
    val lastDayPeriod: String = "",
    val firstDayFertile: String = "",
    val lastDayFertile: String = "",
    val firstDayFertileDay: Int = 0,
    val lastDayFertileDay: Int = 0,
    val longCycle: Int = 0,
    val longPeriod: Int = 0,
    val isEdit: Boolean = false
): Parcelable