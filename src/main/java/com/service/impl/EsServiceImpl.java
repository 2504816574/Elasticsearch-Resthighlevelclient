package com.service.impl;

import com.alibaba.fastjson.JSON;
import com.entity.User;
import com.service.EsService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author sdong
 * @date 2022/5/10
 */
@Service
@Slf4j
public class EsServiceImpl implements EsService {
    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * 创建索引
     *
     * @return
     */
    @Override
    public void indexCreate() throws IOException {
        //创建索引
        CreateIndexRequest request = new CreateIndexRequest("user");
        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        //响应状态
        log.info("创建index结果:{}", response);
    }

    /**
     * 查询索引
     *
     * @return
     * @throws IOException
     */
    @Override
    public void indexSearch() throws IOException {
        GetIndexRequest request = new GetIndexRequest("user");
        GetIndexResponse response = restHighLevelClient.indices().get(request, RequestOptions.DEFAULT);
        log.info("别名:{}", response.getAliases());
        log.info("映射:{}", response.getMappings());
        log.info("设置:{}", response.getSettings());

    }

    /**
     * 删除索引
     *
     * @throws IOException
     */
    @Override
    public void indexDelete() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("user");
        AcknowledgedResponse response = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        log.info("创建index结果:{}", response.isAcknowledged());
    }

    /**
     * 文档新增
     *
     * @throws IOException
     */
    @Override
    public void docCreate() throws IOException {
        IndexRequest request = new IndexRequest();
        //索引 和id
        request.index("user").id("1001");

        //向es插入数据，需要把json转为json字符串
        String valueJson = JSON.toJSONString(new User("张三", "男", 10));
        request.source(valueJson, XContentType.JSON);

        //发起请求
        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        log.info("result:{}", response.getResult());
    }

    /**
     * 批量文档新增
     *
     * @throws IOException
     */
    @Override
    public void docCreateBath() throws IOException {

        BulkRequest request = new BulkRequest();

        //批量
        request.add(new IndexRequest().index("user").id("2002").source(JSON.toJSONString(new User("zhangsan", "男", 1002)), XContentType.JSON));
        request.add(new IndexRequest().index("user").id("2003").source(JSON.toJSONString(new User("zhangsan1003", "男", 1003)), XContentType.JSON));
        request.add(new IndexRequest().index("user").id("2004").source(JSON.toJSONString(new User("zhangsan1004", "男", 10)), XContentType.JSON));
        request.add(new IndexRequest().index("user").id("2005").source(JSON.toJSONString(new User("zhangsan1005", "男", 20)), XContentType.JSON));
        request.add(new IndexRequest().index("user").id("2006").source(JSON.toJSONString(new User("zhangsan1006", "男", 30)), XContentType.JSON));
        request.add(new IndexRequest().index("user").id("2007").source(JSON.toJSONString(new User("zhangsan1007", "男", 40)), XContentType.JSON));
        request.add(new IndexRequest().index("user").id("2008").source(JSON.toJSONString(new User("zhangsan1008", "男", 40)), XContentType.JSON));
        request.add(new IndexRequest().index("user").id("2009").source(JSON.toJSONString(new User("zhangsan1009", "男", 30)), XContentType.JSON));
        request.add(new IndexRequest().index("user").id("20010").source(JSON.toJSONString(new User("zhangsan1007", "男", 20)), XContentType.JSON));
        //发起请求
        BulkResponse response = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        log.info("took:{}", response.getTook());
        log.info("items:{}", response.getItems());

    }

    /**
     * 文档修改
     *
     * @throws IOException
     */
    @Override
    public void docUpdate() throws IOException {
        UpdateRequest request = new UpdateRequest();
        //索引 和id
        request.index("user").id("1001");
        request.doc(XContentType.JSON, "name", "李四");

        UpdateResponse response = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        log.info("result:{}", response.getResult());
    }

    /**
     * 文档查询
     *
     * @throws IOException
     */
    @Override
    public void docSearch() throws IOException {
        GetRequest request = new GetRequest();
        request.index("user").id("1001");
        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        log.info("source:{}", response.getSourceAsString());
    }

    /**
     * 文档删除
     *
     * @throws IOException
     */
    @Override
    public void docDelete() throws IOException {

        DeleteRequest request = new DeleteRequest();
        request.index("user").id("1001");

        DeleteResponse response = restHighLevelClient.delete(request, RequestOptions.DEFAULT);

        log.info("response:{}", response.toString());
    }

    /**
     * 批量文档删除
     *
     * @throws IOException
     */
    @Override
    public void docDeleteBath() throws IOException {
        BulkRequest request = new BulkRequest();
        //批量
        request.add(new DeleteRequest().index("user").id("1002"));
        request.add(new DeleteRequest().index("user").id("1003"));
        request.add(new DeleteRequest().index("user").id("1004"));
        BulkResponse response = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        log.info("took:{}", response.getTook());
        log.info("items:{}", response.getItems());

    }

    /**
     * 查询所有
     *
     * @throws IOException
     */
    @Override
    public void docQueryAll() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");
        request.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        log.info("took:{}", response.getTook());
        log.info("totalHits:{}", response.getHits().getTotalHits());
        for (SearchHit hit : response.getHits().getHits()) {
            log.info("hit:{}", hit.getSourceAsString());
        }
    }

    /**
     * 条件查询
     *
     * @throws IOException
     */
    @Override
    public void docTermQuery() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");
        //查询条件
        request.source(new SearchSourceBuilder().query(QueryBuilders.termQuery("age", 30)));
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        log.info("took:{}", response.getTook());
        log.info("totalHits:{}", response.getHits().getTotalHits());
        for (SearchHit hit : response.getHits().getHits()) {
            log.info("hit:{}", hit.getSourceAsString());
        }
    }

    /**
     * 分页查询
     *
     * @throws IOException
     */
    @Override
    public void docPagingQuery() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");
        //分页 from() 默认0  size()默认10
        request.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()).from(0).size(2));
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        log.info("took:{}", response.getTook());
        log.info("totalHits:{}", response.getHits().getTotalHits());
        for (SearchHit hit : response.getHits().getHits()) {
            log.info("hit:{}", hit.getSourceAsString());
        }
    }

    /**
     * 排序查询
     *
     * @throws IOException
     */
    @Override
    public void docSortQuery() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");
        //根据 age 升序SortOrder.ASC   降序SortOrder.DESC
        request.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()).sort("age", SortOrder.DESC));
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        log.info("took:{}", response.getTook());
        log.info("totalHits:{}", response.getHits().getTotalHits());
        for (SearchHit hit : response.getHits().getHits()) {
            log.info("hit:{}", hit.getSourceAsString());
        }
    }


    /**
     * 过滤查询
     *
     * @throws IOException
     */
    @Override
    public void docFilterQuery() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");
        //排除
        String[] excludex = {};
        //包含
        String[] includex = {"name"};
        request.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()).fetchSource(includex, excludex));
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        log.info("took:{}", response.getTook());
        log.info("totalHits:{}", response.getHits().getTotalHits());
        for (SearchHit hit : response.getHits().getHits()) {
            log.info("hit:{}", hit.getSourceAsString());
        }

    }

    /**
     * 组合查询
     *
     * @throws IOException
     */
    @Override
    public void docBoolQuery() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        //age 为30
        boolQueryBuilder.must(QueryBuilders.termQuery("age", "30"));
        //age 不为40
        boolQueryBuilder.mustNot(QueryBuilders.termQuery("age", "40"));
        //age  为30或40

        boolQueryBuilder.should(QueryBuilders.termQuery("sex", "30"));
        boolQueryBuilder.should(QueryBuilders.termQuery("sex", "40"));

        searchSourceBuilder.query(boolQueryBuilder);
        request.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        log.info("took:{}", response.getTook());
        log.info("totalHits:{}", response.getHits().getTotalHits());
        for (SearchHit hit : response.getHits().getHits()) {
            log.info("hit:{}", hit.getSourceAsString());
        }


    }

    /**
     * 范围查询
     *
     * @throws IOException
     */
    @Override
    public void docRangeQuery() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //范围查询  gte()大于等于 lte()小于等于 gt()大于 le()小于
        searchSourceBuilder.query(QueryBuilders.rangeQuery("age").gte(30).lte(40));
        request.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        log.info("took:{}", response.getTook());
        log.info("totalHits:{}", response.getHits().getTotalHits());
        for (SearchHit hit : response.getHits().getHits()) {
            log.info("hit:{}", hit.getSourceAsString());
        }
    }

    /**
     * 模糊查询
     *
     * @throws IOException
     */
    @Override
    public void docLikeQuery() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //范围查询  Fuzziness.ONE　偏差1  (只填name对中文不起作用)
        searchSourceBuilder.query(QueryBuilders.fuzzyQuery("name.keyword", "张三1002").fuzziness(Fuzziness.ONE));
        request.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        log.info("took:{}", response.getTook());
        log.info("totalHits:{}", response.getHits().getTotalHits());
        for (SearchHit hit : response.getHits().getHits()) {
            log.info("hit:{}", hit.getSourceAsString());
        }
    }

    /**
     * 模糊查询
     *
     * @throws IOException
     */
    @Override
    public void docHighlightQuery() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");
        //查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //写name 张三 无法查询,需要name.keyword 张三
        searchSourceBuilder.query(QueryBuilders.termQuery("name", "zhangsan"));
        //高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color ='red'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("name");
        searchSourceBuilder.highlighter(highlightBuilder);
        request.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        log.info("took:{}", response.getTook());
        log.info("totalHits:{}", response.getHits().getTotalHits());
        for (SearchHit hit : response.getHits().getHits()) {
            log.info("hit:{}", hit.getSourceAsString());
            log.info("highlightFields:{}", hit.getHighlightFields());
        }
    }
}
