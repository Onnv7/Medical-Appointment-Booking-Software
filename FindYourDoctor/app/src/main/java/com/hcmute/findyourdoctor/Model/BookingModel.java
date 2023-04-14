package com.hcmute.findyourdoctor.Model;

import com.hcmute.findyourdoctor.Domain.BookingDomain;

public class BookingModel {
    private Boolean success;
    private String message;
    private BookingDomain result;

    public BookingModel(Boolean success, String message, BookingDomain result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BookingDomain getResult() {
        return result;
    }

    public void setResult(BookingDomain result) {
        this.result = result;
    }
}
