package com.anangkur.mediku.util

object Const {
    const val PREF_NAME = "PREF_NAME"
    const val DATABASE_NAME = "mediku_database"

    const val EXTRA_COUNTRY = "EXTRA_COUNTRY"

    const val PROVIDER_GOOGLE = "google.com"
    const val PROVIDER_FIREBASE = "firebase"
    const val PROVIDER_PASSWORD = "password"

    const val MAX_IMAGE_SIZE = 500.0
    const val COMPRESS_QUALITY = 95
    const val SAMPLE_SIZE = 2

    // fireStore
    const val COLLECTION_USER = "users"
    const val COLLECTION_MEDICAL_RECORD = "medical_records"
    const val COLLECTION_MENSTRUAL_PERIOD = "menstrual_period"

    // medical record
    const val CATEGORY_SICK = "Sick"
    const val CATEGORY_CHECKUP = "Checkup"
    const val CATEGORY_HOSPITAL = "Hospital"

    // date format
    const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    const val TIME_GENERAL_HH_MM = "HH:mm"
    const val DAY_NAME_DATE_MONTH_NAME = "EEE, dd MMM"
    const val DAY_FULL_WITH_DATE_LOCALE = "EEE, dd MMM yyyy"
    const val DATE_ENGLISH_YYYY_MM_DD = "yyyy-M-d"
    const val DATE_FORMAT_NEW_COVID19 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSS'Z'"
    const val DATE_FORMAT_NEW_COVID19_2 = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    const val DATE_FORMAT_SHOWN_COVID19 = "EEE, dd MMM yyyy - hh:mm"
    const val DATE_NEWS_SHOWN = "dd/MM/yyyy - hh:mm"

    // notification
    const val NOTIFICATION_CHANNEL_ID = "10001"

    // storage
    const val STORAGE_PROFILE_PHOTO = "profile_photos"

    // status new covid 19 data
    const val STATUS_NEW_COVID19_CONFIRMED = "confirmed"
    const val STATUS_NEW_COVID19_RECOVERED = "recovered"
    const val STATUS_NEW_COVID19_DEATHS = "deaths"

    // news
    const val API_KEY = "261d82dd7e26494e841fb1039a4fdaf7"
    const val NEWS_HEALTH = "health"
}