package com.example.ui.components

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Architecture
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.drawing.BearingDrawingManager
import com.example.model.Bearing
import com.example.model.Inventory
import com.example.ui.theme.IndustrialBorderColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerManageModal(
    bearing: Bearing?,
    inventory: Inventory?,
    onDismiss: () -> Unit,
    onSave: (Bearing, Inventory) -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val isEditing = bearing != null

    var number by remember(bearing) { mutableStateOf(bearing?.number ?: "") }
    var manufacturer by remember(bearing) { mutableStateOf(bearing?.manufacturer ?: "SKF") }
    var bearingType by remember(bearing) { mutableStateOf(bearing?.bearingType ?: "Deep Groove Ball Bearings") }
    var currency by remember(inventory) { mutableStateOf(inventory?.currency ?: "EGP") }
    var priceStr by remember(inventory) {
        mutableStateOf(inventory?.sellingPrice?.let { if (it % 1.0 == 0.0) it.toLong().toString() else it.toString() } ?: "")
    }
    var quantityStr by remember(inventory) {
        mutableStateOf(inventory?.quantity?.toString() ?: "")
    }
    var shelfLocation by remember(inventory) { mutableStateOf(inventory?.shelfLocation ?: "") }
    var condition by remember(inventory) { mutableStateOf(inventory?.condition ?: "New") }

    var boreMmStr by remember(bearing) { mutableStateOf(bearing?.boreMm?.toString() ?: "20.0") }
    var outsideMmStr by remember(bearing) { mutableStateOf(bearing?.outsideMm?.toString() ?: "47.0") }
    var widthMmStr by remember(bearing) { mutableStateOf(bearing?.widthMm?.toString() ?: "14.0") }
    var chamferMmStr by remember(bearing) { mutableStateOf(bearing?.chamferMm?.toString() ?: "1.0") }
    var weightKgStr by remember(bearing) { mutableStateOf(bearing?.weightKg?.toString() ?: "0.106") }

    var refSpeedStr by remember(bearing) { mutableStateOf(bearing?.referenceSpeedRpm?.toString() ?: "32000") }
    var greaseSpeedStr by remember(bearing) { mutableStateOf(bearing?.limitingSpeedGreaseRpm?.toString() ?: "17000") }
    var oilSpeedStr by remember(bearing) { mutableStateOf(bearing?.limitingSpeedOilRpm?.toString() ?: "20000") }
    var dynamicLoadStr by remember(bearing) { mutableStateOf(bearing?.dynamicLoadC?.toString() ?: "12.79") }
    var staticLoadStr by remember(bearing) { mutableStateOf(bearing?.staticLoadC0?.toString() ?: "6.58") }

    var customPhotoUri by remember(bearing) { mutableStateOf(bearing?.customDrawingUri) }
    var isUploadingPhoto by remember { mutableStateOf(false) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { selectedUri: Uri? ->
        if (selectedUri != null) {
            isUploadingPhoto = true
            coroutineScope.launch {
                val targetNumber = if (number.isNotBlank()) number else (bearing?.number ?: "NEW")
                val result = BearingDrawingManager.saveUploadedBearingPhoto(
                    context = context,
                    bearingNumber = targetNumber,
                    sourceUri = selectedUri
                )
                isUploadingPhoto = false
                result.fold(
                    onSuccess = { savedPath ->
                        customPhotoUri = savedPath
                        Toast.makeText(context, "Drawing photo selected.", Toast.LENGTH_SHORT).show()
                    },
                    onFailure = {
                        Toast.makeText(context, "Failed to load drawing photo.", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        modifier = modifier.testTag("owner_manage_modal")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Box(
                            modifier = Modifier.padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isEditing) Icons.Default.Edit else Icons.Default.AdminPanelSettings,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (isEditing) "Edit Bearing Details" else "Add New Bearing Item",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Owner / Inventory Management",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (errorMessage != null) {
                Surface(
                    color = MaterialTheme.colorScheme.errorContainer,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text(
                        text = errorMessage ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            // --- SECTION 1: INVENTORY & PRICING ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, IndustrialBorderColor, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "STOCK & PRICE DETAILS",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = number,
                            onValueChange = { number = it.uppercase() },
                            label = { Text("Bearing Designation") },
                            placeholder = { Text("e.g. 6209") },
                            singleLine = true,
                            enabled = !isEditing,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
                        )

                        OutlinedTextField(
                            value = manufacturer,
                            onValueChange = { manufacturer = it },
                            label = { Text("Manufacturer") },
                            placeholder = { Text("e.g. SKF") },
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Manufacturer quick selection chips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("SKF", "FAG", "NSK", "NTN", "KOYO", "TIMKEN").forEach { mfgOption ->
                            val isSelected = manufacturer.equals(mfgOption, ignoreCase = true)
                            FilterChip(
                                selected = isSelected,
                                onClick = { manufacturer = mfgOption },
                                label = { Text(mfgOption, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = bearingType,
                        onValueChange = { bearingType = it },
                        label = { Text("Bearing Type") },
                        placeholder = { Text("e.g. Deep Groove Ball Bearings") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Bearing Type quick chips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("Deep Groove Ball Bearings", "Angular Contact", "Roller Bearing").forEach { typeOption ->
                            val isSelected = bearingType.equals(typeOption, ignoreCase = true)
                            FilterChip(
                                selected = isSelected,
                                onClick = { bearingType = typeOption },
                                label = { Text(typeOption, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = priceStr,
                            onValueChange = { priceStr = it },
                            label = { Text("Selling Price") },
                            placeholder = { Text("Price not set") },
                            leadingIcon = { Icon(Icons.Default.Sell, contentDescription = null, modifier = Modifier.size(18.dp)) },
                            singleLine = true,
                            modifier = Modifier.weight(1.2f),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )

                        OutlinedTextField(
                            value = quantityStr,
                            onValueChange = { quantityStr = it },
                            label = { Text("Quantity") },
                            placeholder = { Text("Quantity not set") },
                            leadingIcon = { Icon(Icons.Default.Inventory, contentDescription = null, modifier = Modifier.size(18.dp)) },
                            singleLine = true,
                            modifier = Modifier.weight(0.8f),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Currency Selector (EGP, USD, EUR)
                    Text(
                        text = "Price Currency:",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("EGP", "USD", "EUR").forEach { currOption ->
                            val isSelected = currency.equals(currOption, ignoreCase = true)
                            FilterChip(
                                selected = isSelected,
                                onClick = { currency = currOption },
                                label = { Text(currOption, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                                leadingIcon = if (isSelected) {
                                    { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                                } else null,
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = shelfLocation,
                        onValueChange = { shelfLocation = it.uppercase() },
                        label = { Text("Shelf Location") },
                        placeholder = { Text("e.g. A-03-B2") },
                        leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(18.dp)) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Condition:",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("New", "Sealed", "Refurbished", "Used").forEach { condOption ->
                            val isSelected = condition.equals(condOption, ignoreCase = true)
                            FilterChip(
                                selected = isSelected,
                                onClick = { condition = condOption },
                                label = { Text(condOption) },
                                leadingIcon = if (isSelected) {
                                    { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                                } else null,
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- SECTION 2: TECHNICAL SPECIFICATIONS ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, IndustrialBorderColor, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "DIMENSIONS & WEIGHT SPECS",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = boreMmStr,
                            onValueChange = { boreMmStr = it },
                            label = { Text("Bore ID (mm)") },
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )

                        OutlinedTextField(
                            value = outsideMmStr,
                            onValueChange = { outsideMmStr = it },
                            label = { Text("Outside OD (mm)") },
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )

                        OutlinedTextField(
                            value = widthMmStr,
                            onValueChange = { widthMmStr = it },
                            label = { Text("Width B (mm)") },
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = chamferMmStr,
                            onValueChange = { chamferMmStr = it },
                            label = { Text("Chamfer (mm)") },
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )

                        OutlinedTextField(
                            value = weightKgStr,
                            onValueChange = { weightKgStr = it },
                            label = { Text("Weight (kg)") },
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- SECTION 3: PERFORMANCE SPECIFICATIONS ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, IndustrialBorderColor, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "PERFORMANCE SPECS",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = refSpeedStr,
                            onValueChange = { refSpeedStr = it },
                            label = { Text("Ref Speed (RPM)") },
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )

                        OutlinedTextField(
                            value = greaseSpeedStr,
                            onValueChange = { greaseSpeedStr = it },
                            label = { Text("Grease Limit (RPM)") },
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )

                        OutlinedTextField(
                            value = oilSpeedStr,
                            onValueChange = { oilSpeedStr = it },
                            label = { Text("Oil Limit (RPM)") },
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedTextField(
                            value = dynamicLoadStr,
                            onValueChange = { dynamicLoadStr = it },
                            label = { Text("Dynamic Load C (kN)") },
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )

                        OutlinedTextField(
                            value = staticLoadStr,
                            onValueChange = { staticLoadStr = it },
                            label = { Text("Static Load C0 (kN)") },
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Card 3: Bearing Technical Drawing / Custom Photo
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, IndustrialBorderColor, RoundedCornerShape(18.dp)),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AddPhotoAlternate,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Drawing / Photo Attachment",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        if (!customPhotoUri.isNullOrBlank()) {
                            IconButton(
                                onClick = { customPhotoUri = null },
                                colors = IconButtonDefaults.iconButtonColors(
                                    contentColor = MaterialTheme.colorScheme.error
                                ),
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Remove photo"
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Attach an authentic workshop photo or custom CAD drawing for this deep groove bearing.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (!customPhotoUri.isNullOrBlank()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                                .border(1.dp, IndustrialBorderColor, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(customPhotoUri)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Attached drawing photo",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedButton(
                            onClick = { photoPickerLauncher.launch("image/*") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddPhotoAlternate,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Change Photo")
                        }
                    } else {
                        OutlinedButton(
                            onClick = { photoPickerLauncher.launch("image/*") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddPhotoAlternate,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Upload Drawing / Photo from Device")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = {
                        val cleanNum = number.trim().uppercase()
                        if (cleanNum.isEmpty()) {
                            errorMessage = "Bearing Designation cannot be empty."
                            return@Button
                        }

                        val parsedPrice = if (priceStr.trim().isEmpty()) null else priceStr.trim().toDoubleOrNull()
                        if (priceStr.trim().isNotEmpty() && parsedPrice == null) {
                            errorMessage = "Please enter a valid numeric value for price, or leave empty if not set."
                            return@Button
                        }
                        if (parsedPrice != null && parsedPrice < 0) {
                            errorMessage = "Price cannot be negative."
                            return@Button
                        }

                        val parsedQty = if (quantityStr.trim().isEmpty()) null else quantityStr.trim().toIntOrNull()
                        if (quantityStr.trim().isNotEmpty() && parsedQty == null) {
                            errorMessage = "Please enter a valid integer for quantity, or leave empty if not set."
                            return@Button
                        }
                        if (parsedQty != null && parsedQty < 0) {
                            errorMessage = "Quantity cannot be negative."
                            return@Button
                        }

                        val parsedBore = boreMmStr.toDoubleOrNull()
                        val parsedOutside = outsideMmStr.toDoubleOrNull()
                        val parsedWidth = widthMmStr.toDoubleOrNull()
                        val parsedChamfer = chamferMmStr.toDoubleOrNull() ?: 0.0
                        val parsedWeight = weightKgStr.toDoubleOrNull() ?: 0.0

                        val parsedRefSpeed = refSpeedStr.toIntOrNull() ?: 0
                        val parsedGreaseSpeed = greaseSpeedStr.toIntOrNull() ?: 0
                        val parsedOilSpeed = oilSpeedStr.toIntOrNull() ?: 0
                        val parsedDynamicLoad = dynamicLoadStr.toDoubleOrNull() ?: 0.0
                        val parsedStaticLoad = staticLoadStr.toDoubleOrNull() ?: 0.0

                        if (parsedBore == null || parsedOutside == null || parsedWidth == null ||
                            parsedBore <= 0.0 || parsedOutside <= 0.0 || parsedWidth <= 0.0) {
                            errorMessage = "Bore (d), Outside (D), and Width (B) must be valid positive numbers."
                            return@Button
                        }

                        errorMessage = null

                        val updatedBearing = Bearing(
                            number = cleanNum,
                            manufacturer = manufacturer.ifBlank { "SKF" },
                            bearingType = bearingType.ifBlank { "Deep Groove Ball Bearings" },
                            boreMm = parsedBore,
                            outsideMm = parsedOutside,
                            widthMm = parsedWidth,
                            chamferMm = parsedChamfer,
                            weightKg = parsedWeight,
                            referenceSpeedRpm = parsedRefSpeed,
                            limitingSpeedGreaseRpm = parsedGreaseSpeed,
                            limitingSpeedOilRpm = parsedOilSpeed,
                            dynamicLoadC = parsedDynamicLoad,
                            staticLoadC0 = parsedStaticLoad,
                            drawingResName = bearing?.drawingResName ?: "deepgroovebearingdrawing",
                            customDrawingUri = customPhotoUri
                        )

                        val updatedInventory = Inventory(
                            condition = condition.ifBlank { "New" },
                            quantity = parsedQty,
                            sellingPrice = parsedPrice,
                            currency = currency.ifBlank { "EGP" },
                            shelfLocation = shelfLocation.trim()
                        )

                        onSave(updatedBearing, updatedInventory)
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .height(50.dp)
                        .testTag("save_bearing_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isEditing) "Save Updates" else "Add Bearing Item",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}
