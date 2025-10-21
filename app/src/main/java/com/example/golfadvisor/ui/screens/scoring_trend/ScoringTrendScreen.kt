package com.example.golfadvisor.ui.screens.scoring_trend

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.yml.charts.axis.AxisConfig
import co.yml.charts.axis.AxisData
import co.yml.charts.common.extensions.formatToSinglePrecision
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.example.golfadvisor.R
import com.example.golfadvisor.ui.screens.commons.composables.UserInfoContainer

@Composable
fun ScoringTrendScreen(
    scoringTrendViewModel: ScoringTrendViewModel,
    username: String,
    modifier: Modifier = Modifier
) {
    val scoringTrendState by scoringTrendViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { scoringTrendViewModel.actions.loadScores(username) }

    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(top = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val userInfo = scoringTrendState.userInfo
        val scorePoints = scoringTrendState.scorePoints
        val maxScore = scoringTrendState.maxScore
        val minScore = scoringTrendState.minScore

        if (userInfo == null) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = stringResource(R.string.scoring_trend_error_icon_description),
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.scoring_trend_error_message),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        } else {
            UserInfoContainer(userInfo)
            if (scorePoints.isEmpty() || maxScore == null || minScore == null) {
                Text(
                    text = stringResource(R.string.scoring_trend_no_scores_yet),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                    val box = this

                    val xAxisData = AxisData.Builder()
                        .axisStepSize(size = box.maxWidth * 0.8f / scorePoints.size)
                        .steps(count = scorePoints.size)
                        .axisConfig(AxisConfig(isAxisLineRequired = false))
                        .build()

                    val yAxisData = AxisData.Builder()
                        .steps(count = 5)
                        .labelData { i ->
                            val stepSize = (maxScore - minScore) / 5
                            ((i * stepSize) + minScore).formatToSinglePrecision()
                        }
                        .backgroundColor(Color.White)
                        .build()

                    val lineChartData = LineChartData(
                        linePlotData = LinePlotData(
                            lines = listOf(
                                Line(
                                    dataPoints = scorePoints,
                                    lineStyle = LineStyle(
                                        color = Color.Black,
                                        width = 6f,
                                        alpha = 0.7f
                                    ),
                                    intersectionPoint = IntersectionPoint(
                                        radius = 5.dp,
                                        color = Color.Black
                                    ),
                                    selectionHighlightPoint = SelectionHighlightPoint(
                                        isHighlightLineRequired = false
                                    ),
                                    shadowUnderLine = ShadowUnderLine(),
                                    selectionHighlightPopUp = SelectionHighlightPopUp(
                                        paddingBetweenPopUpAndPoint = 10.dp,
                                        popUpLabel = { _, y ->
                                            y.toInt().toString()
                                        }
                                    )
                                )
                            ),
                        ),
                        xAxisData = xAxisData,
                        yAxisData = yAxisData,
                        gridLines = GridLines(),
                        backgroundColor = Color.White
                    )

                    LineChart(
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        lineChartData = lineChartData
                    )
                }
            }
        }
    }
}