cmake_minimum_required(VERSION 3.8.0)


add_library(
        native
        SHARED
        src/main/cpp/native.cpp
        src/main/cpp/atomic_test.cpp
)

find_library(android-log log)
target_link_libraries(native ${android-log})