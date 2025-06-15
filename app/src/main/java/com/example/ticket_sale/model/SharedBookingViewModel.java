package com.example.ticket_sale.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedBookingViewModel extends ViewModel {
    private final MutableLiveData<Integer> countdownTime = new MutableLiveData<>();

    public LiveData<Integer> getCountdownTime() {
        return countdownTime;
    }

    public void setCountdownTime(int time) {
        countdownTime.setValue(time);
    }

    public void decreaseCountdown() {
        Integer current = countdownTime.getValue();
        if (current != null && current > 0) {
            countdownTime.setValue(current - 1);
        }
    }
}
