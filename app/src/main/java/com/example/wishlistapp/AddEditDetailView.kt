package com.example.wishlistapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.wishlistapp.Data.Wish
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(id : Long, viewModel: WishViewModel, navController: NavHostController)
{

    val snackMessage = remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()

    if(id != 0L)
    {
        val wish = viewModel.getWishbyId(id).collectAsState(initial = Wish(0L,"",""))
        viewModel.WishTitleState = wish.value.title
        viewModel.WishDescriptionState = wish.value.description
    }else
    {
        viewModel.WishTitleState = ""
        viewModel.WishDescriptionState = ""
    }


    Scaffold(scaffoldState = scaffoldState,topBar = {AppBarView(title =
    if( id != 0L) stringResource(id = R.string.update_wish)
    else stringResource(id = R.string.add_wish))
    {navController.navigateUp()}})
    {
        Column(modifier = Modifier
            .padding(it)
            .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
            Spacer(modifier = Modifier.height(10.dp))
            
            WishTextField(label = "Title", value = viewModel.WishTitleState, onValueChange = {viewModel.onWishTitleChange(it)})
            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(label = "Description", value = viewModel.WishDescriptionState, onValueChange = {viewModel.onWishDescriptionChange(it)})
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                if(viewModel.WishTitleState.isNotEmpty() && viewModel.WishDescriptionState.isNotEmpty())
                {
                    if(id != 0L)
                    {
                        //update
                        viewModel.updateWish(
                            Wish(id = id,title = viewModel.WishTitleState.trim(), description = viewModel.WishDescriptionState.trim())
                        )
                    }
                    else{
                       // add
                        viewModel.addWish(Wish(title = viewModel.WishTitleState.trim(), description = viewModel.WishDescriptionState.trim()))
                        snackMessage.value = "Wish has Been Created !"
                    }
                }
                else{
                        snackMessage.value = "Enter Wish to be Created!"
                }

                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                    navController.navigateUp()
                }
            }) {
                Text(text = if(id != 0L) stringResource(id = R.string.update_wish)
                            else stringResource(id = R.string.add_wish),
                    style = TextStyle(fontSize = 18.sp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishTextField(label : String, value : String, onValueChange : (String) -> Unit)
{
    OutlinedTextField(value = value, onValueChange = onValueChange,
        label = { Text(text = label, color = Color.Black)},
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors()

    )
}
