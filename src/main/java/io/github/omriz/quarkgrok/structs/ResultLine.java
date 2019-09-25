package io.github.omriz.quarkgrok.structs;

import java.util.Objects;

public class ResultLine {
    private String line;
    private Integer lineNumber;

    public ResultLine() {
    }

    public ResultLine(String line, String lineNumber) {
        this.line = line;
        this.lineNumber = Integer.valueOf(lineNumber);
    }

    public ResultLine(String line, Integer lineNumber) {
        this.line = line;
        this.lineNumber = lineNumber;
    }

    public String getLine() {
        return this.line;
    }

    public Integer getlineNumber() {
        return this.lineNumber;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = Integer.valueOf(lineNumber);
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ResultLine)) {
            return false;
        }

        ResultLine other = (ResultLine) obj;

        return Objects.equals(other.line, this.line) && Objects.equals(other.lineNumber, this.lineNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.line, this.lineNumber);
    }
}