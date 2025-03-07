package com.lpg.customtipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Switch
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lpg.customtipcalculator.ui.theme.CustomTipCalculatorTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomTipCalculatorTheme {
                Surface() {
                    TipTimeLayout()
                }
            }
        }
    }
}

@Composable
fun TipTimeLayout() {
    var billAmount by remember { mutableStateOf("") }
    var tipPercent by remember { mutableStateOf("") }
    var roundUp by remember { mutableStateOf(false) }
    val amount = billAmount.toDoubleOrNull() ?: 0.0
    val percent = tipPercent.toDoubleOrNull() ?: 15.0
    val tip = calculateTip(amount, percent, roundUp)
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start)
        )
        EditNumberFiled(
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth(),
            value = billAmount,
            onValueChange = { billAmount = it },
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next,
            label = stringResource(
                R.string.bill_amount
            )
        )
        Spacer(modifier = Modifier.height(50.dp))
        EditNumberFiled(
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth(),
            value = tipPercent,
            onValueChange = { tipPercent = it },
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done,
            label = stringResource(
                R.string.tip_percentage
            )
        )
        Spacer(modifier = Modifier.height(50.dp))
        RoundedTipOption(
            roundUp = roundUp,
            onRoundUpChanged = { roundUp = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(150.dp))
    }
}

@Composable
fun EditNumberFiled(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType,
    imeAction: ImeAction
) {

    TextField(
        label = { Text(label) },
        value = value,
        singleLine = true,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        modifier = modifier
    )
}

@Composable
fun RoundedTipOption(
    modifier: Modifier = Modifier,
    roundUp: Boolean,
    onRoundUpChanged: ((Boolean) -> Unit)?
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {

        Text(text = stringResource(R.string.round_up_tip))
        Switch(
            checked = roundUp,
            onCheckedChange = onRoundUpChanged,
        )
    }
}
@VisibleForTesting
internal fun calculateTip(amount: Double, tipPercent: Double = 15.0, roundUp: Boolean): String {
    var tip = tipPercent / 100 * amount
    if (roundUp) {
        tip = kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CustomTipCalculatorTheme {
        Surface() {
            TipTimeLayout()
        }
    }
}