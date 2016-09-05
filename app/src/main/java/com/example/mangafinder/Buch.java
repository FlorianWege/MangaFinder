package com.example.mangafinder;

/**
 * Created by Win7 on 31.08.2016.
 */
public class Buch {
    private String _name;

    public String getTitle() {
        return _name;
    }

    public void setTitle(String val) {
        _name = val;
    }

    private String _genre;

    public String getGenre() {
        return _genre;
    }

    public void setGenre(String val) {
        _genre = val;
    }

    private int _pagesCount;

    public int getPagesCount() {
        return _pagesCount;
    }

    public void setPagesCount(int val) {
        _pagesCount = val;
    }

    public Buch() {

    }
}
