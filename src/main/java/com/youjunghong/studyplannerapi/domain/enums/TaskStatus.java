package com.youjunghong.studyplannerapi.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TaskStatus {
    NOT_STARTED, STARTED, FINISHED, PUT_OFF;

    @JsonValue
    public int getOrdinal() {
        return this.ordinal();
    }
}
