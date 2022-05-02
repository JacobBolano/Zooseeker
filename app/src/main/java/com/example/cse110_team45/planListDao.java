package com.example.cse110_team45;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface planListDao {

    @Insert
    long insert(planListItem planlistItem);

    @Insert
    List<Long> insertAll(List<planListItem> planListItem);

    @Query("SELECT * FROM `plan_list_items` WHERE `id`=:id")
    planListItem get(long id);

    @Query("SELECT * FROM `plan_list_items` ORDER BY `order`")
    List<planListItem> getAll();

    @Update
    int update(planListItem planListItem);

    @Delete
    int delete(planListItem planListItem);

    @Query("SELECT * FROM `plan_list_items` ORDER BY `order`")
    LiveData<List<planListItem>> getAllLive();

    @Query("SELECT `order` + 1 FROM `plan_list_items` ORDER BY `order` DESC LIMIT 1")
    int getOrderForAppend();

}
