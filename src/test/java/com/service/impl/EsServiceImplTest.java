package com.service.impl;

import com.service.EsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author sdong
 * @date 2022/5/10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class EsServiceImplTest {

    @Autowired
    EsService esService;


    @Test
    void indexCreate() throws IOException {
        esService.indexCreate();
    }

    @Test
    void indexSearch() throws IOException {
        esService.indexSearch();
    }

    @Test
    void indexDelete() throws IOException {
        esService.indexDelete();
    }

    @Test
    void docCreate() throws IOException {
        esService.docCreate();
    }

    @Test
    void docCreateBath() throws IOException {
        esService.docCreateBath();
    }

    @Test
    void docUpdate() throws IOException {
        esService.docUpdate();
    }

    @Test
    void docSearch() throws IOException {
        esService.docSearch();
    }

    @Test
    void docDelete() throws IOException {
        esService.docDelete();
    }

    @Test
    void docDeleteBath() throws IOException {
        esService.docDeleteBath();
    }

    @Test
    void docQueryAll() throws IOException {
        esService.docQueryAll();
    }

    @Test
    void docTermQuery() throws IOException {
        esService.docTermQuery();
    }

    @Test
    void docPagingQuery() throws IOException {
        esService.docPagingQuery();
    }

    @Test
    void docSortQuery() throws IOException {
        esService.docSortQuery();
    }

    @Test
    void docFilterQuery() throws IOException {
        esService.docFilterQuery();
    }

    @Test
    void docBoolQuery() throws IOException {
        esService.docBoolQuery();
    }

    @Test
    void docRangeQuery() throws IOException {
        esService.docRangeQuery();
    }


    @Test
    void docLikeQuery() throws IOException {
        esService.docLikeQuery();
    }

    @Test
    void docHighlightQuery() throws IOException {
        esService.docHighlightQuery();
    }

}