package net.ml.schema;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestModels {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class SimpleModel {
        private String message;
    }

    @Data
    public static final class FinalModel {
        private final String message;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class TransientModel {
        private transient String message;
    }
}
