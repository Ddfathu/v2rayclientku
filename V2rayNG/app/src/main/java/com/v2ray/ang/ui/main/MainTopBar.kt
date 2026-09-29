package com.v2ray.ang.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.v2ray.ang.R
import com.v2ray.ang.ui.compose.verticalScrollbar

private val TopBarBgDark = Color(0xFF160B24) // Ungu gelap pekat
private val ContentWhite = Color(0xFFFFFFFF) // Putih kontras terang

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    isLoading: Boolean,
    showSearch: Boolean,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchClose: () -> Unit,
    onSearchToggle: (Boolean) -> Unit,
    onMenuClick: () -> Unit,
    onAction: (MainAction) -> Unit,
    onMoreMenuAction: (MainMoreMenuAction) -> Unit
) {
    var showImportMenu by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }
    val importMenuScrollState = rememberScrollState()
    val moreMenuScrollState = rememberScrollState()
    val maxMenuHeight = (LocalConfiguration.current.screenHeightDp.dp * 0.7f)

    TopAppBar(
        title = {
            // Tulisan "Config" dikosongkan/dihapus
        },
        navigationIcon = {
            // Garis tiga diganti tombol teks "MENU"
            Box(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onMenuClick() }
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "MENU",
                    color = ContentWhite,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp,
                    letterSpacing = 1.sp
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = TopBarBgDark,
            titleContentColor = ContentWhite,
            navigationIconContentColor = ContentWhite,
            actionIconContentColor = ContentWhite
        ),
        actions = {
            // Tombol "V" kuning sudah dihapus

            // Tombol Pencarian
            IconButton(onClick = { onSearchToggle(true) }) {
                Icon(
                    painter = painterResource(R.drawable.ic_search_24dp),
                    contentDescription = stringResource(R.string.acc_search),
                    tint = ContentWhite
                )
            }

            // Tombol Tambah / Import (+)
            Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
                IconButton(onClick = { showImportMenu = true }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_add_24dp),
                        contentDescription = stringResource(R.string.acc_add),
                        tint = ContentWhite
                    )
                }
                DropdownMenu(
                    expanded = showImportMenu,
                    onDismissRequest = { showImportMenu = false },
                    scrollState = importMenuScrollState,
                    containerColor = Color.White,
                    modifier = Modifier
                        .heightIn(max = maxMenuHeight)
                        .verticalScrollbar(importMenuScrollState)
                ) {
                    ImportMenuContent(
                        onAction = { action ->
                            showImportMenu = false
                            onAction(action)
                        }
                    )
                }
            }

            // Tombol Menu Titik Tiga
            Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_more_vert_24dp),
                        contentDescription = stringResource(R.string.acc_more),
                        tint = ContentWhite
                    )
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    scrollState = moreMenuScrollState,
                    containerColor = Color.White,
                    modifier = Modifier
                        .heightIn(max = maxMenuHeight)
                        .verticalScrollbar(moreMenuScrollState)
                ) {
                    MoreMenuContent { action ->
                        showMenu = false
                        onMoreMenuAction(action)
                    }
                }
            }
        }
    )
}
 