package com.mypc.jettrivia.data

import androidx.room.*
import com.mypc.jettrivia.model.TopScorers
import com.mypc.jettrivia.util.Constants.MAX_NUMBER_SAVED_SCORERS
import kotlinx.coroutines.flow.Flow

@Dao
interface ScorerDao {

    @Query("SELECT * FROM tops_scorers_table")
    fun gelAll():List<TopScorers>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(scorer:TopScorers)

    @Delete
    suspend  fun delete(scorer:TopScorers)

    @Query(value = "DELETE FROM tops_scorers_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM tops_scorers_table WHERE total_score < :lineScore ORDER BY total_score DESC")
    suspend fun getLessScorers(lineScore:Int):List<TopScorers>

    @Query("SELECT  * FROM tops_scorers_table ORDER BY total_score DESC LIMIT :quantity")
    suspend fun getTopScorers(quantity:Int = MAX_NUMBER_SAVED_SCORERS): Flow<List<TopScorers>>

}