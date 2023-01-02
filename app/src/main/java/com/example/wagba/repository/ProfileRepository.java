package com.example.wagba.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.wagba.data.Profile;
import com.example.wagba.data.ProfileDao;
import com.example.wagba.data.ProfileRoomDatabase;

import java.util.List;

public class ProfileRepository {

    private ProfileDao mProfileDao;
    private LiveData<List<Profile>> mAllProfiles;

    public ProfileRepository(Application application) {
        ProfileRoomDatabase db = ProfileRoomDatabase.getDatabase(application);
        mProfileDao = db.profileDao();
        mAllProfiles = mProfileDao.getOrderedProfiles();
    }

    public LiveData<List<Profile>> getAllProfiles() {
        return mAllProfiles;
    }

    public LiveData<Profile> getProfileById(String id) {
        return mProfileDao.getProfileById(id);
    }

    public LiveData<Integer> countProfiles() {
        return mProfileDao.countProfiles();
    }

    public void insert(Profile profile) {
        new insertAsyncTask(mProfileDao).execute(profile);
    }

    private static class insertAsyncTask extends AsyncTask<Profile, Void, Void> {

        private ProfileDao mAsyncTaskDao;

        insertAsyncTask(ProfileDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Profile... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void updateProfile(Profile profile) {
        new UpdateProfileAsyncTask(mProfileDao).execute(profile);
    }

    private static class UpdateProfileAsyncTask extends AsyncTask<Profile, Void, Void> {
        private ProfileDao mAsyncTaskDao;

        UpdateProfileAsyncTask(ProfileDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Profile... params) {
            mAsyncTaskDao.updateProfile(params[0]);
            return null;
        }
    }
}
