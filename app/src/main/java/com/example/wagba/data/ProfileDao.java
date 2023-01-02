package com.example.wagba.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProfileDao {
    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
    @Query("SELECT * from profile_table ORDER BY id ASC")
    LiveData<List<Profile>> getOrderedProfiles();

    @Query("SELECT * FROM profile_table WHERE id = :id")
    LiveData<Profile> getProfileById(String id);

    @Query("SELECT COUNT(*) FROM profile_table")
    LiveData<Integer>  countProfiles();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Profile profile);

    @Update
    void updateProfile(Profile profile);

    @Query("DELETE FROM profile_table")
    void deleteAll();
}
