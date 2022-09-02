package com.handitohe.myapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class DataItem(
    @SerialName("avg_bandwidth")
    val avgBandwidth: Int = 0,
    @SerialName("avg_time_generation")
    val avgTimeGeneration: Double = 0.0,
    @SerialName("avg_time_on_page")
    val avgTimeOnPage: Int = 0,
    @SerialName("bounce_rate")
    val bounceRate: String = "",
    @SerialName("entry_bounce_count")
    val entryBounceCount: Int = 0,
    @SerialName("entry_nb_actions")
    val entryNbActions: Int = 0,
    @SerialName("entry_nb_visits")
    val entryNbVisits: Int = 0,
    @SerialName("entry_sum_visit_length")
    val entrySumVisitLength: Int = 0,
    @SerialName("exit_nb_visits")
    val exitNbVisits: Int = 0,
    @SerialName("exit_rate")
    val exitRate: String = "",
    @SerialName("label")
    val label: String = "",
    @SerialName("max_bandwidth")
    val maxBandwidth: Int = 0,
    @SerialName("max_time_generation")
    val maxTimeGeneration: String = "",
    @SerialName("min_bandwidth")
    val minBandwidth: Int = 0,
    @SerialName("min_time_generation")
    val minTimeGeneration: String = "",
    @SerialName("nb_hits")
    val nbHits: Int = 0,
    @SerialName("nb_hits_with_bandwidth")
    val nbHitsWithBandwidth: Int = 0,
    @SerialName("nb_hits_with_time_generation")
    val nbHitsWithTimeGeneration: Int = 0,
    @SerialName("nb_visits")
    val nbVisits: Int = 0,
    @SerialName("sum_bandwidth")
    val sumBandwidth: Int = 0,
    @SerialName("sum_daily_entry_nb_uniq_visitors")
    val sumDailyEntryNbUniqVisitors: Int = 0,
    @SerialName("sum_daily_exit_nb_uniq_visitors")
    val sumDailyExitNbUniqVisitors: Int = 0,
    @SerialName("sum_daily_nb_uniq_visitors")
    val sumDailyNbUniqVisitors: Int = 0,
    @SerialName("sum_time_spent")
    val sumTimeSpent: Int = 0,
) : Parcelable
