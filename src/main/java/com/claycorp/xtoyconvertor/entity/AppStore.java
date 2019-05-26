package com.claycorp.xtoyconvertor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@Document(collection = "appStore")
public class AppStore {

    private Long counter;

    @Id
    private String id;

    private String trackName;

    private Long sizeInBytes;

    private String currency;

    private Double price;

    private Long ratingTotalCount;

    private Long ratingCountVersion;

    private Double userRating;

    private Double userRatingVersion;

    private String version;

    private String contRating;

    private String primeGenre;

    private Long supDevicesNum;

    private Long iPadScUrlsNum;

    private Long langNum;

    private Long vppLic;


}