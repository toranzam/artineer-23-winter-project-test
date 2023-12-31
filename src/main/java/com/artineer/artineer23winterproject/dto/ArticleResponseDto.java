package com.artineer.artineer23winterproject.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class ArticleResponseDto {

    private List<Integer> pageNumList;

    private ArticlePageDto articleDto;

    private boolean prev, next;

    private int prevPage, nextPage, totalPage, current;

    @Builder
    public ArticleResponseDto(ArticlePageDto articlePageDto, long total){
        this.articleDto = articlePageDto;
        this.totalPage = (int) total;

        int end = (int)(Math.ceil((double) articlePageDto.getPage() / 10)) * 10;

        int start = end - 9;

        int last = (int)(Math.ceil((total/(double)articlePageDto.getSize())));
        end = end > last ? last : end;

        this.prev = start > 1;

        this.next = totalPage > end * articlePageDto.getSize();

        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

        this.prevPage = prev ? start - 1 : 0;

        this.nextPage = next ? end + 1 : 0;


    }


}
