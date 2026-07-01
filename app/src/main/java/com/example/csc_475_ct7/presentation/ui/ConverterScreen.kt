package com.example.csc_475_ct7.presentation.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.CompareArrows
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.csc_475_ct7.data.local.entity.ConversionHistoryEntity
import com.example.csc_475_ct7.domain.model.ConversionCategory
import com.example.csc_475_ct7.domain.model.UnitType
import com.example.csc_475_ct7.presentation.ConverterUiState
import com.example.csc_475_ct7.presentation.ConverterViewModel
import com.example.csc_475_ct7.ui.theme.CSC475CT7Theme
import com.example.csc_475_ct7.util.ClipboardUtil
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun ConverterPreview() {
    CSC475CT7Theme {
        ConverterDetail(
            uiState = ConverterUiState(),
            onInputChange = {},
            onFromUnitChange = {},
            onToUnitChange = {},
            onSwapUnits = {},
            onBack = {},
            onSave = {},
            onCopy = {},
            onShare = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ConverterScreen(
    viewModel: ConverterViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val history by viewModel.history.collectAsState()
    val navigator = rememberListDetailPaneScaffoldNavigator<Nothing>()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            CategoryList(
                selectedCategory = uiState.category,
                onCategorySelected = {
                    viewModel.onCategoryChange(it)
                    scope.launch {
                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                    }
                }
            )
        },
        detailPane = {
            ConverterDetail(
                uiState = uiState,
                history = history,
                onInputChange = viewModel::onInputChange,
                onFromUnitChange = viewModel::onFromUnitChange,
                onToUnitChange = viewModel::onToUnitChange,
                onSwapUnits = viewModel::onSwapUnits,
                onBack = {
                    scope.launch {
                        navigator.navigateBack()
                    }
                },
                onSave = viewModel::saveToHistory,
                onClearHistory = viewModel::clearHistory,
                onCopy = { ClipboardUtil.copyToClipboard(context, it) },
                onShare = { ClipboardUtil.shareText(context, it) }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryList(
    selectedCategory: ConversionCategory,
    onCategorySelected: (ConversionCategory) -> Unit
) {
    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Categories", fontWeight = FontWeight.ExtraBold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .background(backgroundBrush)
            .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(ConversionCategory.entries) { category ->
                    CategoryItem(
                        category = category,
                        isSelected = category == selectedCategory,
                        onClick = { onCategorySelected(category) }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: ConversionCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val icon = when (category) {
        ConversionCategory.TEMPERATURE -> Icons.Rounded.Thermostat
        ConversionCategory.LENGTH -> Icons.Rounded.Straighten
        ConversionCategory.WEIGHT -> Icons.Rounded.Scale
        ConversionCategory.VOLUME -> Icons.Rounded.Opacity
        ConversionCategory.SPEED -> Icons.Rounded.Speed
        ConversionCategory.TIME -> Icons.Rounded.Timer
    }

    val containerBrush = if (isSelected) {
        Brush.horizontalGradient(
            colors = listOf(
                MaterialTheme.colorScheme.primaryContainer,
                MaterialTheme.colorScheme.secondaryContainer
            )
        )
    } else {
        Brush.horizontalGradient(
            colors = listOf(
                MaterialTheme.colorScheme.surfaceVariant,
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
            )
        )
    }

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = MaterialTheme.shapes.large
            )
            .shadow(
                elevation = if (isSelected) 8.dp else 2.dp,
                shape = MaterialTheme.shapes.large
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(modifier = Modifier.background(containerBrush)) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = category.name.lowercase().replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterDetail(
    uiState: ConverterUiState,
    history: List<ConversionHistoryEntity> = emptyList(),
    onInputChange: (String) -> Unit,
    onFromUnitChange: (UnitType) -> Unit,
    onToUnitChange: (UnitType) -> Unit,
    onSwapUnits: () -> Unit,
    onBack: () -> Unit,
    onSave: () -> Unit,
    onClearHistory: () -> Unit = {},
    onCopy: (String) -> Unit,
    onShare: (String) -> Unit
) {
    val detailBackground = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = uiState.category.name.lowercase().replaceFirstChar { it.uppercase() },
                        fontWeight = FontWeight.ExtraBold
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onSave) {
                        Icon(
                            imageVector = Icons.Rounded.Save,
                            contentDescription = "Save to History",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .background(detailBackground)
            .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = uiState.inputValue,
                        onValueChange = onInputChange,
                        label = { Text("Input Value") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        shape = MaterialTheme.shapes.extraLarge,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                        )
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        UnitDropdown(
                            label = "From",
                            selectedUnit = uiState.fromUnit,
                            units = uiState.availableUnits,
                            onUnitSelected = onFromUnitChange,
                            modifier = Modifier.weight(1f)
                        )

                        FloatingActionButton(
                            onClick = onSwapUnits,
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            elevation = FloatingActionButtonDefaults.elevation(4.dp),
                            shape = CircleShape,
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.CompareArrows,
                                contentDescription = "Swap Units"
                            )
                        }

                        UnitDropdown(
                            label = "To",
                            selectedUnit = uiState.toUnit,
                            units = uiState.availableUnits,
                            onUnitSelected = onToUnitChange,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                item {
                    val resultText = if (uiState.outputValue.isEmpty()) "0" else "${uiState.outputValue} ${uiState.toUnit.abbreviation}"
                    
                    val resultBrush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.tertiaryContainer
                        )
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(16.dp, MaterialTheme.shapes.extraLarge)
                            .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), MaterialTheme.shapes.extraLarge),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        shape = MaterialTheme.shapes.extraLarge
                    ) {
                        Box(modifier = Modifier.background(resultBrush)) {
                            Column(
                                modifier = Modifier.padding(32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Converted Result",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                AnimatedContent(
                                    targetState = uiState.outputValue,
                                    transitionSpec = {
                                        fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
                                    }
                                ) { output ->
                                    Text(
                                        text = if (output.isEmpty()) "0" else "$output ${uiState.toUnit.abbreviation}",
                                        style = MaterialTheme.typography.displayMedium,
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                                
                                Spacer(modifier = Modifier.height(24.dp))
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Button(
                                        onClick = { onCopy(resultText) },
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                            contentColor = MaterialTheme.colorScheme.primaryContainer
                                        )
                                    ) {
                                        Icon(Icons.Rounded.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp))
                                        Spacer(Modifier.width(8.dp))
                                        Text("Copy")
                                    }
                                    OutlinedButton(
                                        onClick = { onShare(resultText) },
                                        shape = RoundedCornerShape(12.dp),
                                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimaryContainer)
                                    ) {
                                        Icon(Icons.Rounded.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                                        Spacer(Modifier.width(8.dp))
                                        Text("Share")
                                    }
                                }
                            }
                        }
                    }
                }

                if (history.isNotEmpty()) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Recent History",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            TextButton(onClick = onClearHistory) {
                                Text("Clear All", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    
                    items(history) { entry ->
                        HistoryItem(entry)
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryItem(entry: ConversionHistoryEntity) {
    val date = remember(entry.timestamp) {
        SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()).format(Date(entry.timestamp))
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = entry.category.lowercase().replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = date,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${entry.inputValue} ${entry.inputUnit} = ${entry.outputValue} ${entry.outputUnit}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitDropdown(
    label: String,
    selectedUnit: UnitType,
    units: List<UnitType>,
    onUnitSelected: (UnitType) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedUnit.name,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
            shape = MaterialTheme.shapes.large
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            units.forEach { unit ->
                DropdownMenuItem(
                    text = { Text(unit.name) },
                    onClick = {
                        onUnitSelected(unit)
                        expanded = false
                    }
                )
            }
        }
    }
}
