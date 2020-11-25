package com.carespeak.domain.entities.program;

import java.util.Objects;

public class ProgramOptOutForm {

    private String header;
    private String body;
    private String footer;

    public ProgramOptOutForm() {

    }

    public ProgramOptOutForm(String header, String body, String footer) {
        this.header = header;
        this.body = body;
        this.footer = footer;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgramOptOutForm that = (ProgramOptOutForm) o;
        return Objects.equals(header, that.header) &&
                Objects.equals(body, that.body) &&
                Objects.equals(footer, that.footer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, body, footer);
    }

    @Override
    public String toString() {
        return "ProgramOptOutForm{" +
                "header='" + header + '\'' +
                ", body='" + body + '\'' +
                ", footer='" + footer + '\'' +
                '}';
    }
}
