package com.rusefi.util;

import java.io.IOException;

public interface LazyFile extends Output {
    interface LazyFileFactory {
        LazyFile create(String fileName);
    }

    LazyFileFactory REAL = LazyFileImpl::new;

    String LAZY_FILE_TAG = "was generated automatically by rusEFI tool ";
    String LAZY_FILE_TAG_LOWER = LAZY_FILE_TAG.toLowerCase();
    String TEST = "test_file_name";

    @Override
    void write(String line);
}
