package com.example.models;

import javax.persistence.*;

@Entity
@Table(name = "\"book\"")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    private String author;
    private String title;
    private int numberOfPages;
    private short publishingYear;
    private int price;
    private boolean inStock;

    public Book() {
    }

    public Book(String author, String title, int numberOfPages, short publishingYear, int price, boolean inStock) {
        this.author = author;
        this.title = title;
        this.numberOfPages = numberOfPages;
        this.publishingYear = publishingYear;
        this.price = price;
        this.inStock = inStock;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public short getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(short publishingYear) {
        this.publishingYear = publishingYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
