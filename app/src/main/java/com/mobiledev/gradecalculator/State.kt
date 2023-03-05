package com.mobiledev.gradecalculator

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.mobiledev.gradecalculator.MainActivity.Field

data class State(
    val participation: String = "0",
    val presentation: String = "0",
    val midterm1: String = "0",
    val midterm2: String = "0",
    val finalProject: String = "0",
    val homeworks: SnapshotStateList<String> = SnapshotStateList<String>().apply { addAll(List(Field.HOMEWORK.maxCount) { "0" }) },
    val finalGrade: String = ""
)