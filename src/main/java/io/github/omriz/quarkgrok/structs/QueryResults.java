package io.github.omriz.quarkgrok.structs;

import java.util.Map;
import java.util.Objects;

public class QueryResults {
    private Integer time;
    private Integer resultCount;
    private Integer startDocument;
    private Integer endDocument;
    private Map<String, ResultLine> results;

    public QueryResults() {
    }

    public QueryResults(Integer time, Integer resultCount, Integer startDocument, Integer endDocument,
            Map<String, ResultLine> results) {
        this.time = time;
        this.resultCount = resultCount;
        this.startDocument = startDocument;
        this.endDocument = endDocument;
        this.results = results;
    }

    public Integer getTime() {
        return this.time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getResultCount() {
        return this.resultCount;
    }

    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    public Integer getStartDocument() {
        return this.startDocument;
    }

    public void setStartDocument(Integer startDocument) {
        this.startDocument = startDocument;
    }

    public Integer getEndDocument() {
        return this.endDocument;
    }

    public void setEndDocument(Integer endDocument) {
        this.endDocument = endDocument;
    }

    public Map<String, ResultLine> getResults() {
        return this.results;
    }

    public void setResults(Map<String, ResultLine> results) {
        this.results = results;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof QueryResults)) {
            return false;
        }

        QueryResults other = (QueryResults) obj;

        return Objects.equals(other.time, this.time) && Objects.equals(other.endDocument, this.endDocument)
                && Objects.equals(other.startDocument, this.startDocument) && other.results.equals(this.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.time, this.endDocument, this.startDocument, this.resultCount, this.results);
    }
}