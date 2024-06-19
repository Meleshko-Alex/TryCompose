package com.meleha.trycompose.ui.btnTextfieldCheckBoxes

import android.os.Parcelable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.parcelize.Parcelize


data class CheckableItem(
    val title: String,
    val isChecked: MutableState<Boolean>
)

data class CheckBoxesState(
    val checkableItems: List<CheckableItem>
) {
    val selectedItemNames: String
        get() {
            return checkableItems.asSequence()
                .filter { it.isChecked.value }
                .map { it.title }
                .joinToString()
                .takeIf { it.isNotEmpty() } ?: "[Nothing]"
        }

    companion object {
        val Saver: Saver<CheckBoxesState, *> = Saver(
            save = { state: CheckBoxesState ->
                ParcelableCheckBoxesState(
                    checkedItems = state.checkableItems.map {
                        ParcelableCheckableItem(
                            it.title,
                            it.isChecked.value
                        )
                    }
                )
            },
            restore = { state: ParcelableCheckBoxesState ->
                CheckBoxesState(
                    checkableItems = state.checkedItems.map {
                        CheckableItem(
                            it.title,
                            mutableStateOf(it.isChecked)
                        )
                    }
                )
            }
        )
    }
}

@Parcelize
data class ParcelableCheckableItem(
    val title: String,
    val isChecked: Boolean
) : Parcelable

@Parcelize
data class ParcelableCheckBoxesState(
    val checkedItems: List<ParcelableCheckableItem>
) : Parcelable

@Composable
fun CheckBoxesExample() {
    val state = rememberSaveable(saver = CheckBoxesState.Saver) {
        CheckBoxesState(
            checkableItems = List(size = 6) { index ->
                val id = index + 1
                CheckableItem(
                    title = "Item $id",
                    isChecked = mutableStateOf(false)
                )
            }
        )
    }
    state.checkableItems.forEach { checkedItem ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    onClick = {
                        val newCheckValue = !checkedItem.isChecked.value
                        checkedItem.isChecked.value = newCheckValue
                    },
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple()
                )
        ) {
            Checkbox(
                checked = checkedItem.isChecked.value,
                onCheckedChange = {
                    val newCheckedValue = !checkedItem.isChecked.value
                    checkedItem.isChecked.value = newCheckedValue
                }
            )
            Text(text = checkedItem.title)
        }
    }
    Text(text = "Selected Items: ${state.selectedItemNames}")
}

@Preview(showBackground = true)
@Composable
private fun CheckBoxesExamplePreview() {
    Column {
        CheckBoxesExample()
    }
}