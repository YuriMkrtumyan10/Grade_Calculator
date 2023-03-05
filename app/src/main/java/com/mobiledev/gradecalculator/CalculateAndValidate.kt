package com.mobiledev.gradecalculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

import com.mobiledev.gradecalculator.MainActivity.Field
import java.math.RoundingMode


class CalculateAndValidate : ViewModel() {
//Validations
    var state by mutableStateOf(State())


    private fun calculate() {
        val homeworks = state.homeworks
            .toList()
            .map { it.toInt() }
            .average().times(MainActivity.Field.HOMEWORK.percent)

        val midterm1 = state.midterm1.toIntOrNull()
            ?.let { it * MainActivity.Field.MIDTERM_1.percent }
            ?: 0.0

        val midterm2 = state.midterm2.toIntOrNull()
            ?.let { it * MainActivity.Field.MIDTERM_2.percent }
            ?: 0.0

        val participation = state.participation.toIntOrNull()
            ?.let { it * MainActivity.Field.PARTICIPATION.percent }
            ?: 0.0

        val presentation = state.presentation.toIntOrNull()
            ?.let { it * MainActivity.Field.PRESENTATION.percent }
            ?: 0.0

        val finalProject = state.finalProject.toIntOrNull()
            ?.let { it * MainActivity.Field.FINAL_PROJECT.percent }
            ?: 0.0

        val finalGrade = homeworks + midterm1 + midterm2 + participation + presentation + finalProject
        state = state.copy(finalGrade = finalGrade.toBigDecimal().setScale(1, RoundingMode.UP).toString())

    }

    fun onAction(action: Action) {
        when (action) {
            is Action.Input -> {
                val inputText = action.input.trim()
                if (inputText.isNotEmpty()) {
                    input(inputText, action.field)
                }
            }
            is Action.InputWithIndex -> {
                val inputText = action.input.trim()
                if (inputText.isNotEmpty()) {
                    input(inputText, action.field, action.index)
                }
            }
            is Action.Calculate -> calculate()
        }
    }

    fun isValidState(): Boolean {
        val isValidHws = state.homeworks
            .toList().any { isValid(it) }

        val isValidMidterm1 = isValid(state.midterm1)
        val isValidMidterm2 = isValid(state.midterm2)
        val isValidParticipation = isValid(state.participation)
        val isValidPresentation = isValid(state.presentation)
        val isValidFinalProject = isValid(state.finalProject)

        return isValidHws
                && isValidMidterm1
                && isValidMidterm2
                && isValidParticipation
                && isValidPresentation
                && isValidFinalProject
    }
    fun isValid(text: String): Boolean {
        val value = text.toIntOrNull()
        if (value == null || value < 0 || value > 100) {
            return false
        }
        return true
    }

    private fun input(input: String, field: Field, index: Int = -1) {
        when (field) {
            Field.HOMEWORK -> {
                if (index in 0 until state.homeworks.size) {
                    val homeworks = state.homeworks
                    homeworks[index] = input
                    state = state.copy(homeworks = homeworks)
                }
            }
            Field.PARTICIPATION -> state = state.copy(participation = input)
            Field.PRESENTATION -> state = state.copy(presentation = input)
            Field.MIDTERM_1 -> state = state.copy(midterm1 = input)
            Field.MIDTERM_2 -> state = state.copy(midterm2 = input)
            Field.FINAL_PROJECT -> state = state.copy(finalProject = input)
        }
    }
}