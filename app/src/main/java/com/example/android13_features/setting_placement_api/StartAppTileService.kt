package com.example.android13_features.setting_placement_api

import android.content.Intent
import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.android13_features.MainActivity

/**
 * Simple [TileService] that starts the application when the Quick Settings tile is clicked.
 */
@RequiresApi(Build.VERSION_CODES.N)
class StartAppTileService : TileService() {
    override fun onStartListening() {
        super.onStartListening()
        Log.d(TAG, "onStartListening: Called when this tile moves into a listening state.")
        qsTile?.apply {
            state = Tile.STATE_ACTIVE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                subtitle = "Open App"
            }
            updateTile()
        }
    }

    override fun onClick() {
        super.onClick()

        startActivityAndCollapse(
            Intent(this, SettingPlacementApiActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }
    override fun onTileAdded() {
        super.onTileAdded()
        Log.d(TAG, "onTileAdded: Called when the user adds this tile to Quick Settings.")
    }

    override fun onTileRemoved() {
        super.onTileRemoved()
        Log.d(TAG, "onTileRemoved: Called when the user removes this tile from Quick Settings.")
    }



    override fun onStopListening() {
        super.onStopListening()
        Log.d(TAG, "onStopListening: Called when this tile moves out of the listening state.")
    }

    companion object
    {
        val TAG = "START_APP_TILE_SRVICE"
    }
}
