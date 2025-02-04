package com.easyhz.patchnote.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.easyhz.patchnote.core.database.defect.dao.OfflineDefectDao
import com.easyhz.patchnote.core.database.defect.entity.OfflineDefectImageEntity
import com.easyhz.patchnote.core.database.defect.entity.OfflineDefectEntity

@Database(
    entities = [OfflineDefectEntity::class, OfflineDefectImageEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val offlineDefectDao: OfflineDefectDao
}