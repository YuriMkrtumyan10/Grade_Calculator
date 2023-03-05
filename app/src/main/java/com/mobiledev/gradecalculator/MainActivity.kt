package com.mobiledev.gradecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobiledev.gradecalculator.composables.Button
import com.mobiledev.gradecalculator.composables.TextField
import com.mobiledev.gradecalculator.ui.theme.GradeCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GradeCalculatorTheme {
                val viewModel = viewModel<CalculateAndValidate>()
                val valuesState = viewModel.state

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                ) {

                    for (i in 1..4) {
                        TextField(
                            input = when (i) {
                                1 -> valuesState.midterm1
                                2 -> valuesState.midterm2
                                3 -> valuesState.finalProject
                                4 -> valuesState.participation
                                else -> "" // handle the case when i is greater than the number of fields
                            },
                            label = when (i) {
                                1 -> "Midterm 1"
                                2 -> "Midterm 2"
                                3 -> "Final Project"
                                4 -> "Participation"
                                5 -> "Homework"
                                else -> "" // handle the case when i is greater than the number of fields
                            },
                            isValid = viewModel.isValid(
                                when (i) {
                                    1 -> valuesState.midterm1
                                    2 -> valuesState.midterm2
                                    3 -> valuesState.finalProject
                                    4 -> valuesState.participation
                                    else -> "" // handle the case when i is greater than the number of fields
                                }
                            ),
                            onValueChange = {
                                viewModel.onAction(
                                    Action.Input(
                                        it,
                                        when (i) {
                                            1 -> Field.MIDTERM_1
                                            2 -> Field.MIDTERM_2
                                            3 -> Field.FINAL_PROJECT
                                            4 -> Field.PARTICIPATION
                                            else -> "" // handle the case when i is greater than the number of fields
                                        } as Field
                                    )
                                )
                            },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                    }
                    var itemCount by remember { mutableStateOf(0) }

                    repeat(itemCount) { index ->
                        val isValid = viewModel.isValid(viewModel.state.homeworks[index])

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                        ) {

                            TextField(
                                input = viewModel.state.homeworks[index],
                                label = "Homework ${index + 1}",
                                isValid = isValid,
                                onValueChange = {
                                    viewModel.onAction(
                                        Action.InputWithIndex(
                                            it,
                                            Field.HOMEWORK,
                                            index
                                        )
                                    )
                                },

                            )
                        }
                    }

                    Button(
                        modifier = Modifier
                            .clip(RoundedCornerShape(5.dp))
                            .height(40.dp)
                            .width(150.dp)
                            .padding(start = 0.dp)
                            .align(Alignment.CenterHorizontally),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        text = "Add Homework",
                        enabled = itemCount in 0..4
                    ) {
                        itemCount = when (itemCount) {
                            Field.HOMEWORK.maxCount -> itemCount
                            else -> itemCount + 1
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(60.dp),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                text = "Final Grade: ${viewModel.state.finalGrade}"
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(5.dp))
                                    .align(Alignment.BottomCenter)
                                    .width(200.dp)
                                    .height(50.dp),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                text = "Calculate",
                                onClick = { viewModel.onAction(Action.Calculate) },
                                enabled = viewModel.isValidState()
                            )
                        }
                    }
                }
            }
        }
    }

enum class Field(val percent: Double, val maxCount: Int) {

    HOMEWORK(0.2, 5),
    PARTICIPATION(0.1, 1),
    PRESENTATION(0.1, 1),
    MIDTERM_1(0.1, 1),
    MIDTERM_2(0.2, 1),
    FINAL_PROJECT(0.3, 1);
}
}
