package org.codingpedia.demo.rmqc.core.product.bean;

import java.util.Date;

public class Product {
    private Long id;

    private String title;

    private String feed;

    private Date insertionDate;

    private String description;

    private String linkOnPodcastpedia;

    public Product(Long id, String title, String feed, Date insertionDate, String description, String linkOnPodcastpedia) {
        this.id = id;
        this.title = title;
        this.feed = feed;
        this.insertionDate = insertionDate;
        this.description = description;
        this.linkOnPodcastpedia = linkOnPodcastpedia;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getFeed() {
        return feed;
    }

    public Date getInsertionDate() {
        return insertionDate;
    }

    public String getDescription() {
        return description;
    }

    public String getLinkOnPodcastpedia() {
        return linkOnPodcastpedia;
    }
}