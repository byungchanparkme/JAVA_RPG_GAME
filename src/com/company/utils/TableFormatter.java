package com.company.utils;

import java.util.LinkedList;
import java.util.List;

public class TableFormatter {
    private int columns;
    private List<String> cells = new LinkedList<>();
    private int minSpacesBetweenCells = 4;
    private boolean alignLeft = true;
    private int maxLength = 0;

    public TableFormatter(int columns) {
        this.columns = columns;
    }

    public TableFormatter insert(Object... cells) {
        for (Object content : cells) {
            String cell = content.toString();
            maxLength = Math.max(maxLength, cell.length());
            this.cells.add(cell);
        }
        return this;
    }

    public TableFormatter setMinSpacesBetweenCells(int minSpacesBetweenCells) {
        this.minSpacesBetweenCells = minSpacesBetweenCells;
        return this;
    }

    public TableFormatter alignCellsToRight() {
        this.alignLeft = false;
        return this;
    }

    public TableFormatter alignCellsToLeft() {
        this.alignLeft = true;
        return this;
    }

    @Override
    public String toString() {
        String format = "%";
        if (alignLeft)
            format += "-";
        format += maxLength + "s";
        String spaces = new String(new char[minSpacesBetweenCells]).replace("\0", " ");

        StringBuilder sb = new StringBuilder();

        int row = 0;
        int currentColumn = 0;
        for (String cell : cells) {
            if (currentColumn == 0) {
                if (row > 0)
                    sb.append("\n");
            } else {
                sb.append(spaces);
            }

            sb.append(String.format(format, cell));

            currentColumn = (currentColumn + 1) % columns;
            if (currentColumn == 0)
                row++;
        }

        return sb.toString();
    }
}
