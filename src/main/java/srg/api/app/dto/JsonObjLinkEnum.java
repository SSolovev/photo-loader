package srg.api.app.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum JsonObjLinkEnum {
    EDIT("edit"),
    PHOTOS("photos"),

    SELF("self"),
    ALTERNATE("alternate"),

    YMAPSML("ymapsml");

    String val;

    JsonObjLinkEnum(String val) {
        this.val = val;
    }

    @JsonCreator
    public static JsonObjLinkEnum forValue(String value) {
        return Arrays.stream(JsonObjLinkEnum.values()).filter(
                e -> e.val.equals(value)).findFirst().get();
//        if (res.isPresent()) {
//            return res.get();
//        } else {
//            return null;
//        }
    }

    @JsonValue
    public String toValue() {
        return this.val;
//        for (Entry<String, DeviceScheduleFormat> entry : namesMap.entrySet()) {
//            if (entry.getValue() == this)
//                return entry.getKey();
//        }
//
//        return null; // or fail
    }
}
