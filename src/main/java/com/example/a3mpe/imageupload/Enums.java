package com.example.a3mpe.imageupload;

enum Enums {
    GET(7), POST(6), PUT(5), DELETE(4),
    CAMERA_PERMISSION(3), READ_EXTERNAL_STORAGE_PERMISSION(2),SELECT_FILE(0),REQUEST_CAMERA(1);

    private int value;
    Enums(int value) {
        this.value = value;
    }

    public static Enums fromInt(int i) {
        for (Enums b : Enums.values()) {
            if (b.getValue() == i) {
                return b;
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }
}
