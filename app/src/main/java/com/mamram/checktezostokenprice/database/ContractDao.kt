package com.mamram.checktezostokenprice.database

import androidx.room.*
import com.mamram.checktezostokenprice.data.entity.Contract

@Dao
interface ContractDao {
    @Query("SELECT * FROM Contract")
    suspend fun getAll(): List<Contract>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContract(contract: Contract): Long

    @Update
    fun updateContract(contract: Contract)

    @Delete
    fun delete(contract: Contract)

    @Query("DELETE FROM Contract")
    suspend fun nukeTable()
}