package com.example.wagba.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.example.wagba.data.Profile;
import com.example.wagba.repository.ProfileRepository;

import java.util.List;

public class ProfileViewModel extends AndroidViewModel{

        private ProfileRepository mRepository;

        private LiveData<List<Profile>> mAllProfiles;

        public ProfileViewModel (Application application) {
            super(application);
            mRepository = new ProfileRepository(application);
            mAllProfiles = mRepository.getAllProfiles();
        }

        public LiveData<List<Profile>> getAllProfiles() { return mAllProfiles; }

        public LiveData<Profile> getProfileById(String id) {
            return mRepository.getProfileById(id);
        }

        public void insert(Profile profile) { mRepository.insert(profile); }

        public void updateProfile(Profile profile) {
            mRepository.updateProfile(profile);
        }

        public LiveData<Integer> countProfiles() {
            return mRepository.countProfiles();
        }

//        public void updateAttributes(List<Object> attributes) {
//                mRepository.updateAttributes(attributes);
//        }
//        public void updateFirstName(String userFirstName, String userId) {mRepository.updateFirstName(userFirstName, userId);};
//        public void updateLastName(String userLastName, String userId){mRepository.updateLastName(userLastName, userId);};
//        public void updateEmail(String userEmail, String userId){mRepository.updateEmail(userEmail, userId);};
//        public void updateBirthDate(String userBirthdate, String userId){mRepository.updateBirthDate(userBirthdate, userId);};
        }
