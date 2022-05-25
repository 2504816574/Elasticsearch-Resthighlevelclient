package com.service;

import java.io.IOException;

/**
 * @author sdong
 * @date 2022/5/10
 */
public interface EsService {
    /**
     * 创建索引
     *
     * @return
     * @throws IOException
     */
    void indexCreate() throws IOException;

    /**
     * 查询索引
     *
     * @return
     * @throws IOException
     */
    void indexSearch() throws IOException;

    /**
     * 删除索引
     *
     * @throws IOException
     */
    void indexDelete() throws IOException;

    /**
     * 文档新增,有则全量更新
     *
     * @throws IOException
     */
    void docCreate() throws IOException;

    /**
     * 批量文档新增
     *
     * @throws IOException
     */
    void docCreateBath() throws IOException;

    /**
     * 文档修改
     *
     * @throws IOException
     */
    void docUpdate() throws IOException;

    /**
     * 文档查询
     *
     * @throws IOException
     */
    void docSearch() throws IOException;

    /**
     * 文档删除
     *
     * @throws IOException
     */
    void docDelete() throws IOException;


    /**
     * 批量文档删除
     *
     * @throws IOException
     */
    void docDeleteBath() throws IOException;

    /**
     * 查询所有
     *
     * @throws IOException
     */
    void docQueryAll() throws IOException;

    /**
     * 条件查询
     *
     * @throws IOException
     */
    void docTermQuery() throws IOException;

    /**
     * 分页查询
     *
     * @throws IOException
     */
    void docPagingQuery() throws IOException;

    /**
     * 排序查询
     *
     * @throws IOException
     */
    void docSortQuery() throws IOException;

    /**
     * 过滤查询
     *
     * @throws IOException
     */
    void docFilterQuery() throws IOException;

    /**
     * 组合查询
     *
     * @throws IOException
     */
    void docBoolQuery() throws IOException;

    /**
     * 范围查询
     *
     * @throws IOException
     */
    void docRangeQuery() throws IOException;

    /**
     * 模糊查询
     *
     * @throws IOException
     */
    void docLikeQuery() throws IOException;

    /**
     * 模糊查询
     *
     * @throws IOException
     */
    void docHighlightQuery() throws IOException;
}
