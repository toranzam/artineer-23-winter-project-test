package com.artineer.artineer23winterproject.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResponseDto {

    private List<Integer> pageNumList;

    private PageDto pageDto;

    private boolean prev, next;

    private int prevPage, nextPage, totalPage;

//    @Builder
//    public ArticleResponseDto(PageDto pageDto, long total){
//        this.articleDto = pageDto;
//        this.totalPage = (int) total;
//
//        int end = (int)(Math.ceil((double) pageDto.getPage() / 10)) * 10;
//
//        int start = end - 4;
//
//        int last = (int)(Math.ceil((total/(double) pageDto.getSize())));
//
//        end = end > last ? last : end;
//
//        this.prev = start > 1;
//
//        this.next = totalPage > end * pageDto.getSize();
//
//        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
//
//        this.prevPage = prev ? start - 1 : 0;
//
//        this.nextPage = next ? end + 1 : 0;
//
//        System.out.println();
//
//    }

    @Builder
    public PageResponseDto(PageDto pageDto, long total){
        this.pageDto = pageDto;
        this.totalPage = (int) total;

        int showPages = 10;

        int group = (int) Math.ceil((double) pageDto.getPage() / showPages);

        int start = (group - 1) * showPages + 1;

        int end = group * showPages;

        int last = (int)(Math.ceil((total/(double) pageDto.getSize())));

        end = end > last ? last : end;

        this.prev = start > 1;

        this.next = totalPage > end * pageDto.getSize();

        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

        this.prevPage = prev ? start - 1 : 0;

        this.nextPage = next ? end + 1 : 0;

        System.out.println();

    }


}
