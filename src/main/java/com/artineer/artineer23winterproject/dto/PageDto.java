package com.artineer.artineer23winterproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto {
    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;
}
