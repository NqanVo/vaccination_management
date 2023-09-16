package com.api.vaccinationmanagement.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomRow {
    private List<Cell> cells;

    public CustomRow(Row row) {
        // Lưu trữ dữ liệu từ dòng vào cells
        cells = new ArrayList<>();
        for (Cell cell : row) {
            cells.add(cell);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomRow customRow = (CustomRow) o;
        return cells.equals(customRow.cells);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cells);
    }
}
