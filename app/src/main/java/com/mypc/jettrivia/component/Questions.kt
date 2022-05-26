package com.mypc.jettrivia.component

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mypc.jettrivia.model.QuestionItem
import com.mypc.jettrivia.screens.QuestionsViewModel
import com.mypc.jettrivia.util.AppColors
import com.mypc.jettrivia.util.Constants

@Composable
fun Questions(viewModel: QuestionsViewModel) {
    val questions = viewModel.data.value.data?.toMutableList()
    val initial_index = remember {
        (0..1000).random()
    }
    val questionIndex = remember {
        mutableStateOf(initial_index)
    }


    val gameEnded = remember {
        mutableStateOf(false)
    }

    if(viewModel.data.value.loading == true) {
        CircularProgressIndicator()
    }else {
        val question = try {
            questions?.get(questionIndex.value)
        }catch (ex:Exception) {
            null
        }
        if(questions != null) {
            QuestionDisplay(question = question!!, questionIndex = questionIndex,
                totalQuestion = viewModel.getQuestionSize(), gameEnded = gameEnded,
                inicialIndex = initial_index)
        }
    }

}

//@Preview(showBackground = true)
@Composable
fun QuestionDisplay(
    question: QuestionItem,
    questionIndex: MutableState<Int>,
    gameEnded: MutableState<Boolean>,
                   // viewModel: QuestionsViewModel,
    onNextClick: (Int) -> Unit = { currentIndex ->


        if (currentIndex < inicialIndex + Constants.MAX_NUMBER_QUESTIONS) {
            questionIndex.value = currentIndex + 1
        } else {
            gameEnded.value = true


        }


    },
    totalQuestion: Int = 100,
    inicialIndex: Int
) {
    val choicesState = remember(question) {
        question.choices.toMutableList()
    }

    val answerState = remember(question){
        mutableStateOf<Int?>(null)
    }

    val correctAnswerState = remember(question) {
        mutableStateOf<Boolean?>(null)

    }

    val updateAnswer: (Int) -> Unit = remember(question) {
        {
            answerState.value = it
            correctAnswerState.value = choicesState[it] == question.answer
            if(correctAnswerState.value == false) {
                gameEnded.value = true
            }
        }

    }
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f,10f),0f)

    Surface(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
    color = AppColors.mDarkPurple){
        Column(modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start) {

            if(questionIndex.value >= 3) {
                ShowProgress(score = questionIndex.value)
            }

            QuestionTracker(counter = questionIndex.value + 1, outOff = totalQuestion)
            DrawDottedLine(pathEffect = pathEffect)
            Column(modifier = Modifier.fillMaxHeight()) {
                Text(
                    text = question.question,
                    modifier = Modifier
                        .padding(6.dp)
                        .align(alignment = Alignment.Start)
                        .fillMaxHeight(0.3f),
                    fontSize = 17.sp,
                    color = AppColors.mOffWhite,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp)
                //choices
                choicesState.forEachIndexed {
                    index,answerText->
                    Row(modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                        .border(
                            width = 4.dp, brush = Brush.linearGradient(
                                colors = listOf(AppColors.mOffDarkPurple, AppColors.mOffDarkPurple),
                            ), shape = RoundedCornerShape(15.dp)
                        )
                        .clip(
                            RoundedCornerShape(
                                topStartPercent = 50,
                                topEndPercent = 50,
                                bottomEndPercent = 50,
                                bottomStartPercent = 50
                            )
                        )
                        .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically) {
                        
                        RadioButton(selected = (answerState.value == index), onClick = {
                            updateAnswer(index)
                        },
                        modifier = Modifier.padding(start = 16.dp),
                        enabled = !gameEnded.value,
                        colors = RadioButtonDefaults.
                        colors(
                            selectedColor =
                                if(correctAnswerState.value == true && index == answerState.value){
                                    Color.Green.copy(alpha = 0.2f)
                                }else {
                                    AppColors.mRed.copy(alpha = 0.2f)
                                }
                        , unselectedColor = AppColors.mOffWhite))//end rb

                        val anotatedString = buildAnnotatedString { 
                            
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Light,
                                    color =
                                        if(correctAnswerState.value == true && index == answerState.value){
                                            Color.Green
                                        } else if(correctAnswerState.value == false && index == answerState.value){
                                            AppColors.mRed
                                        }else {
                                            AppColors.mOffWhite
                                        }
                                )
                            ){
                                append(answerText)
                            }//end with style
                            
                        }//end anotated string
                        Text(text = anotatedString, modifier = Modifier.padding(6.dp))
                    }
                }

                Button(onClick = { onNextClick(questionIndex.value) },
                modifier = Modifier
                    .padding(3.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                shape = RoundedCornerShape(34.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = AppColors.mBlue

                ), enabled =  !gameEnded.value && correctAnswerState.value == true
                ) {
                        Text(text = "Next", modifier = Modifier.padding(4.dp),
                        color = AppColors.mOffWhite, fontSize = 17.sp,
                        fontWeight = FontWeight.Bold)
                }
                
            }
        }
    }
}

@Composable
fun DrawDottedLine(pathEffect: PathEffect) {

 androidx.compose.foundation.Canvas(
     modifier = Modifier
         .fillMaxWidth()
         .height(1.dp),
 ) {
     drawLine(color = AppColors.mLigthGray,
     start = Offset(0f,0f),
     end = Offset(size.width,y = 0f),
     pathEffect = pathEffect)
 }
}

@Preview
@Composable
fun ShowProgress(score:Int = 12) {

    val gradient = Brush.linearGradient(listOf(Color(0xFFF95075),Color(0xFFbe6be5)))

    val progressFactor = remember(score) {
        mutableStateOf(score*0.005f)
    }
    Row(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth()
        .height(45.dp)
        .border(
            width = 4.dp,

            brush = Brush.linearGradient(
                colors = listOf(
                    AppColors.mLigthPurple, AppColors.mLigthPurple
                )
            ), shape = RoundedCornerShape(35.dp)
        )
        .clip(
            RoundedCornerShape(
                topStartPercent = 50,
                topEndPercent = 50,
                bottomStartPercent = 50,
                bottomEndPercent = 50
            )
        )
        .background(Color.Transparent), verticalAlignment = Alignment.CenterVertically)
    {
        Button(onClick = {}, contentPadding = PaddingValues(1.dp),
        modifier = Modifier
            .fillMaxWidth(progressFactor.value)
            .background(brush = gradient),
        enabled = false,
        elevation = null,
        colors = buttonColors(
        backgroundColor = Color.Transparent,
        disabledBackgroundColor = Color.Transparent)) {
            Text(text = (score).toString(), modifier = Modifier
                .clip(RoundedCornerShape(23.dp))
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(6.dp),
                color = AppColors.mOffWhite,
                textAlign = TextAlign.Center)

        }


    }

}

@Composable
fun QuestionTracker(counter:Int = 10,
                    outOff:Int = 100) {
    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)){
            withStyle(style = SpanStyle(color = AppColors.mLigthGray,
                fontWeight = FontWeight.Bold,
                fontSize = 27.sp) ){
                    append("Question $counter/")

            }

            withStyle(style = SpanStyle(color = AppColors.mLigthGray,
                fontWeight = FontWeight.Light,
                fontSize = 18.sp)
            ){
                append("$outOff")
            }

        }

    },
    modifier = Modifier.padding(20.dp))

}