package br.com.amparocuidado.data.local.chats

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ChatEntity::class],
    version = 1,
)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
}