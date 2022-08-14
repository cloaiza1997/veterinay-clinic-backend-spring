package com.test.veterinary.clinic.interfaces;

import com.test.veterinary.clinic.helper.GeneralTypes;

public interface PetInterface {
    Long getId();

    String getName();

    String getBreed();

    String getGender();

    Long getUserId();

    String getUserName();

    Long getClinicHistoryId();
}
