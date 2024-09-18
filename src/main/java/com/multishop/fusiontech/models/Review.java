package com.multishop.fusiontech.models;

import jakarta.persistence.*;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String userSurname;
    private String userImage;
    @Column(length = 600)
    private String comment;
    private Integer rating;
    private Date writingDate;
    @ManyToOne
    private Product product;

    public String getFormattedWritingDate() {
        if (writingDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            return sdf.format(writingDate);
        }
        return null;
    }
}
