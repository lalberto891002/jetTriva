package com.mypc.jettrivia.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.mypc.jettrivia.component.Questions
import com.mypc.jettrivia.screens.viewmodels.QuestionsViewModel

@Composable
fun TriviaHome(viewModel: QuestionsViewModel = hiltViewModel()) {
    Questions(viewModel = viewModel)

}