package pt.blissapps.blissandroidchallenge.ui.mainscreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.blissapps.blissandroidchallenge.R

@Composable
fun AvatarSearchComponent(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState,
    isSearching: Boolean = false,
    onSearch: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Row (modifier = modifier
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.background(color = Color.Transparent),
            state = textFieldState,
            trailingIcon = {
                if(isSearching) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp,
                        color = Color.Black.copy(alpha = 0.3f)
                    )
                }
                else {
                    if (textFieldState.text.isNotEmpty()){
                        Icon(
                            modifier = Modifier.clickable(
                                onClick = {
                                    onSearch(textFieldState.text.toString())
                                    focusManager.clearFocus()
                                    keyboardController?.hide()
                                }
                            ),
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.search_avatar),
                            tint = Color.Black.copy(alpha = 0.3f)
                        )
                    }
                }
            },
            leadingIcon = {
                if(textFieldState.text.isNotEmpty()) {
                    Icon(
                        modifier = Modifier.clickable(
                            onClick = {
                                textFieldState.clearText()
                            }
                        ),
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.search_avatar),
                        tint = Color.Black.copy(alpha = 0.3f)
                    )
                }
                else {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.search_avatar),
                        tint = Color.Black.copy(alpha = 0.3f)
                    )
                }
            },
            shape = RoundedCornerShape(32.dp),
            lineLimits = TextFieldLineLimits.SingleLine,
            label = {
                Text(text = stringResource(R.string.search_avatar))
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            onKeyboardAction = { performDefaultAction ->
                onSearch(textFieldState.text.toString())
                focusManager.clearFocus()
                keyboardController?.hide()
                performDefaultAction()
            }
        )
    }
}

@Preview
@Composable
fun AvatarSearchComponentPreview() {
    AvatarSearchComponent(
        textFieldState = TextFieldState(
            initialText = "Search Avatar"
        ),
        onSearch = { }
    )
}