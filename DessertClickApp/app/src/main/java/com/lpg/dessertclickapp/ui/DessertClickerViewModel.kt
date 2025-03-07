package com.lpg.dessertclickapp.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.lpg.dessertclickapp.R
import com.lpg.dessertclickapp.data.Datasource.dessertList
import com.lpg.dessertclickapp.model.Dessert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertClickerViewModel : ViewModel() {
    //     Hago que el estado (datos) sean mutables
    private val _uiState = MutableStateFlow(DessertClickerUiState())

    //    Los datos visibles para el exterior solo van a ser observables
    val uiState: StateFlow<DessertClickerUiState> = _uiState.asStateFlow()
    private var currentDessertIndex by mutableIntStateOf(0)
    private var currentDessertPrice by
    mutableIntStateOf(dessertList[currentDessertIndex].price)
    var currentDessertImageId by
    mutableIntStateOf(dessertList[currentDessertIndex].imageId)
        private set

    private fun determineDessertToShow(
    ): Dessert {
        var dessertToShow = dessertList.first()
        for (dessert in dessertList) {
            if (_uiState.value.dessertsSold >= dessert.startProductionAmount) {
                dessertToShow = dessert
            } else {
                // The list of desserts is sorted by startProductionAmount. As you sell more desserts,
                // you'll start producing more expensive desserts as determined by startProductionAmount
                // We know to break as soon as we see a dessert who's "startProductionAmount" is greater
                // than the amount sold.
                break
            }
        }

        return dessertToShow
    }

    fun onShareButtonClicked(intentContext: Context) {
        shareSoldDessertsInformation(
            intentContext = intentContext,
        )
    }

    fun onDessertClicked() {
        // Update the revenue
        _uiState.update { currentState ->
            currentState.copy(
                revenue = currentState.revenue.plus(currentDessertPrice),
                dessertsSold = currentState.dessertsSold.inc(),
            )
        }
        // Show the next dessert
        val dessertToShow = determineDessertToShow()
        currentDessertImageId = dessertToShow.imageId
        currentDessertPrice = dessertToShow.price
    }

    /**
     * Share desserts sold information using ACTION_SEND intent
     */
    private fun shareSoldDessertsInformation(intentContext: Context) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                intentContext.getString(
                    R.string.share_text,
                    _uiState.value.dessertsSold,
                    _uiState.value.revenue
                )
            )
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)

        try {
            ContextCompat.startActivity(intentContext, shareIntent, null)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                intentContext,
                intentContext.getString(R.string.sharing_not_available),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}