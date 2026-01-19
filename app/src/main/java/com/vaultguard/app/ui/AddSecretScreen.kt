package com.vaultguard.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vaultguard.app.R
import com.vaultguard.app.ui.components.VaultButton
import com.vaultguard.app.ui.components.VaultTextField
import com.vaultguard.app.ui.secret.SecretViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSecretScreen(
    onBack: () -> Unit,
    onSaved: () -> Unit,
    viewModel: SecretViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    val saveState by viewModel.saveState.collectAsState()
    var isSaving by remember { mutableStateOf(false) }

    // React to state changes
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(saveState) {
        saveState?.let { result ->
            isSaving = false
            if (result.isSuccess) {
                onSaved()
            } else {
                result.exceptionOrNull()?.message?.let { msg ->
                    snackbarHostState.showSnackbar("Error: $msg")
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_add_secret), style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.content_desc_back), tint = MaterialTheme.colorScheme.onBackground)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(24.dp)
        ) {
            // Instructions / Header (optional)
            Text(
                "New Credential", 
                style = MaterialTheme.typography.bodyMedium, 
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(24.dp))

            VaultTextField(
                value = title,
                onValueChange = { title = it },
                label = stringResource(R.string.label_service_name)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            VaultTextField(
                value = username,
                onValueChange = { username = it },
                label = stringResource(R.string.label_username)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            VaultTextField(
                value = password,
                onValueChange = { password = it },
                label = stringResource(R.string.label_password)
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            VaultButton(
                text = stringResource(R.string.btn_encrypt_save),
                onClick = {
                    if (title.isNotEmpty() && password.isNotEmpty()) {
                        isSaving = true
                        viewModel.saveSecret(title, username, password)
                    }
                },
                enabled = !isSaving && title.isNotEmpty() && password.isNotEmpty(),
                isLoading = isSaving
            )
        }
    }
}
