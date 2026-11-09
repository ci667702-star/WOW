package com.example.JavaProject.data.loader;

import com.example.JavaProject.data.dto.GameDataItem;

import java.util.List;

/** JSON 파일 하나와 그 안의 항목들. 검증 오류에 파일 경로를 표시하려고 함께 보관한다. */
public record DataFile<T extends GameDataItem>(String path, List<T> items) {
}
