package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.Tuit
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.UserProfileDataApiRequest
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomAvatar
import ar.edu.unlam.mobile.scaffolding.ui.components.tuit.BottomRow
import ar.edu.unlam.mobile.scaffolding.ui.components.tuit.MiddleRow
import ar.edu.unlam.mobile.scaffolding.ui.components.tuit.TopRow
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.EditUserViewModel
import ar.edu.unlam.mobile.scaffolding.ui.viewmodel.UpdateProfileDataState
import ar.edu.unlam.mobile.scaffolding.utils.Constants.DEFAULT_URL_IMAGE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserScreen(
    viewModel: EditUserViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    navController: NavController,
) {
    val userProfileDataState by viewModel.userProfileDataState.collectAsState()
    var userNameUpdate by remember { mutableStateOf("") }
    var userEmailUpdate by remember { mutableStateOf("") }
    var userUrlUpdate by remember { mutableStateOf("") }
    val dataUpdateRequestStatus by viewModel.userProfileDataUpdateState.collectAsState()
    val toastContext = LocalContext.current

    val myRequestIsLoading = dataUpdateRequestStatus is UpdateProfileDataState.Loading

    val mockTuit by remember(userNameUpdate, userUrlUpdate, userProfileDataState) {
        mutableStateOf(
            Tuit(
                id = -1,
                author =
                    userNameUpdate.ifEmpty {
                        userProfileDataState?.name ?: "JUAN GONZALES"
                    },
                avatarUrl =
                    userUrlUpdate.ifEmpty {
                        userProfileDataState?.avatarUrl ?: DEFAULT_URL_IMAGE
                    },
                message = "this is a preview of how your tuit will looks like",
                liked = true,
                likes = 250,
            ),
        )
    }

    LaunchedEffect(key1 = dataUpdateRequestStatus) {
        when (dataUpdateRequestStatus) {
            is UpdateProfileDataState.Loading -> {}

            is UpdateProfileDataState.Success -> {
                Toast.makeText(toastContext, "done", Toast.LENGTH_SHORT).show()
                navController.navigate("feedTuitScreen") {
                    viewModel.resetUpdateProfileDataState()
                    popUpTo(route = "editUserScreen") { inclusive = true }
                }
            }

            is UpdateProfileDataState.Error -> {
                Toast.makeText(toastContext, "error", Toast.LENGTH_SHORT).show()
                userUrlUpdate = userProfileDataState?.avatarUrl ?: DEFAULT_URL_IMAGE
                userNameUpdate = userProfileDataState?.name ?: "JUAN GONZALES"
            }

            is UpdateProfileDataState.Idle -> {}
            // era o agregar esto o un else que haga lo mismo, no va codigo ya que arranca en idle
        }
    }
    Scaffold(
        modifier = Modifier.padding(paddingValues),
        topBar = {
            (
                TopAppBar(
                    title = { Text(text = "Edit Profile") },
                    colors =
                        TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Black,
                            titleContentColor = Color.White,
                        ),
                )
            )
        },
    ) { paddingValues1 ->
        Column(
            modifier =
                Modifier
                    .padding(paddingValues1)
                    .padding(12.dp),
        ) {
            if (myRequestIsLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
            HorizontalDivider(
                Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.secondary,
                thickness = 0.25f.dp,
            )

            Card(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                colors =
                    CardDefaults.cardColors(
                        containerColor = Color.Transparent,
                    ),
            ) {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp)
                            .padding(horizontal = 15.dp),
                ) {
                    CustomAvatar(
                        tuit = mockTuit,
                    )
                    Column(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                                .padding(top = 3.dp),
                    ) {
                        TopRow(
                            tuit = mockTuit,
                        )
                        MiddleRow(tuit = mockTuit)
                        BottomRow(tuit = mockTuit, onClick = {})
                    }
                }
            }
            HorizontalDivider(
                Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.secondary,
                thickness = 0.25f.dp,
            )
            Spacer(modifier = Modifier.height(18.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = userUrlUpdate,
                onValueChange = { userUrlUpdate = it },
                placeholder = {
                    Text(
                        text =
                            userProfileDataState?.avatarUrl
                                ?: "paste your new thumbnail here as url",
                    )
                },
            )

            Spacer(modifier = Modifier.height(18.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = userNameUpdate,
                onValueChange = { userNameUpdate = it },
                placeholder = {
                    Text(
                        text = userProfileDataState?.name ?: "type your new username",
                    )
                },
            )

            Spacer(modifier = Modifier.height(18.dp))

            OutlinedTextField(
                value = userEmailUpdate,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { userEmailUpdate = it },
                placeholder = { Text(text = userProfileDataState?.email ?: "email") },
                readOnly = true,
            )
            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = {
                    if (userUrlUpdate.isEmpty()) {
                        userUrlUpdate = userProfileDataState?.avatarUrl
                            ?: DEFAULT_URL_IMAGE
                    }
                    if (userNameUpdate.isEmpty()) {
                        userNameUpdate = userProfileDataState?.name ?: "generic name 1"
                    }
                    viewModel.updateProfileData(
                        profileDataUpdated =
                            UserProfileDataApiRequest(
                                name = userNameUpdate,
                                avatarUrl = userUrlUpdate,
                            ),
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !myRequestIsLoading,
                shape = MaterialTheme.shapes.medium,
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 16.dp),
            ) {
                Text(text = "Update Profile")
            }
            Button(
                onClick = {
                    navController.navigate("logInScreen") {
                        viewModel.resetUpdateProfileDataState()
                        popUpTo(route = "logInScreen") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White,
                    ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
            ) {
                Text(text = "Log Out")
            }
        }
    }
}
