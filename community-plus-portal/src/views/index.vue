
<template>
  <div class="index-box">
    <el-row :gutter="30">
      <!-- 文章列表 -->
      <el-col :span="18">
        <!-- 分类 -->
        <div class="category-box">
          <el-row :gutter="10">
            <el-col v-for="(i,index) in category" :key="index" :span="3">
              <div v-bind:class="['category-list',{'category-list-click':index===categoryBoxIndex}]"
                   @click="categoryArticleList(index,i.id)">
                <div style="float: left">
                  <el-image
                    style="width: 25px; height: 25px; border-radius: 5px"
                    :src="i.image"
                    fit="fill"/>
                </div>
                {{i.name}}
              </div>
            </el-col>
          </el-row>
        </div>
        <!-- 文章列表 -->
        <div class="article-box">
          <!-- 排序方式-->
          <div style="border-bottom: 2px solid #E4E7ED">
            <div v-bind:class="['article-sort',{'article-sort-click':sortBoxIndex===index}]"
                 :key="index" v-for="(i,index) in articleSort"
                 @click="sortArticleList(index,i.value)">{{i.name}}
            </div>
          </div>
          <!-- 没有数据时显示-->
          <div v-show="articleListPageInfo.total===0" style="text-align: center;padding:40px 0;user-select: none">
            <i class="iconfont" style="font-size: 120px;color: rgba(0,181,173,0.7);">&#xe612;</i>
          </div>
          <!-- 文章list-->
          <div v-show="articleListPageInfo.total!==0">
            <div class="article-list" :key="index" v-for="(i,index) in articleListPageInfo.list">
              <!-- 作者头像 -->
              <div style="cursor: pointer">
                <el-image
                  style="width: 40px; height: 40px; border-radius: 50%;float: left"
                  :src="i.avatarUrl"
                  :onerror="defaultAvatarUrl"
                  fit="fill"/>
              </div>
              <!-- 文章 原创 & 转载 -->
              <div class="article-list-isOriginal" v-text="i.isOriginal===1?'原创':'转载'"></div>
              <!--文章分类图片-->
              <div class="article-list-category-img">
                <el-image
                  style="width: 16px; height: 16px; border-radius: 3px;"
                  :src="i.categoryImage"
                  fit="fill"/>
              </div>
              <!-- 文章标题 -->
              <div class="article-list-title" @click="articleDetail(i.id)">{{i.title}}
                <div class="article-list-newFlag" v-if="new Date().getTime()-i.modifyTime<259200000">new</div>
              </div>
              <!-- 阅览、点赞、评论等-->

              <div class="article-list-publishTime">
                <el-popover
                  placement="top"
                  width="100"
                  trigger="hover"
                >
                  <div class="articleCount-style">
                    <div>
                      <span>浏览:&nbsp;{{i.viewCount}}</span>&nbsp;&nbsp;•&nbsp;&nbsp;
                      <span>点赞:&nbsp;{{i.likeCount}}</span>
                    </div>
                    <div>
                      <span>评论:&nbsp;{{i.commentCount}}</span>&nbsp;&nbsp;•&nbsp;&nbsp;
                      <span>收藏:&nbsp;{{i.collectionCount}}</span>
                    </div>
                  </div>
                  <span slot="reference">
                <i class="iconfont" style="float: left;font-size: 16px;cursor: pointer;">&#xe657;</i>
                <span style="float: left;font-size: 11px;font-style: italic;font-weight: 300">{{i.viewCount + i.likeCount + i.commentCount + i. collectionCount}}</span>
              </span>
                </el-popover>
                <el-divider direction="vertical"/>
                {{getTheDateDiff(i.modifyTime)}}
              </div>
            </div>
          </div>
        </div>

        <!-- 分页 -->
        <div class="pagination-box">
          <el-pagination
            background
            layout="prev, pager, next,jumper"
            :current-page.sync="articleListPageInfo.pageNum"
            :page-size="articleListPageInfo.pageSize"
            :total="articleListPageInfo.total"
            @current-change="pageNumArticleList(articleListPageInfo.pageNum)"
            @prev-click="pageNumArticleList(articleListPageInfo.pageNum - 1)"
            @next-click="pageNumArticleList(articleListPageInfo.pageNum + 1)"
          >
          </el-pagination>
        </div>
      </el-col>

      <!-- 右边信息栏 -->
      <el-col :span="6">
        <div class="info-box">
          <div class="edit-myInfo">
            <div style="float:left; height: 48px;width: 48px;border:1px solid #C0C4CC;border-radius: 5px;cursor: pointer">
              <img :src="avatarUrl" style="margin: 2px; height: 42px;width: 42px;border:1px solid #E4E7ED;border-radius: 5px"/>
            </div>
            <div class="myInfo-name">{{name}}</div>
            <div class="edit-button"><i class="iconfont" style="font-size: 13px;color: rgba(0,181,173,0.7);">&#xe66d;编辑</i></div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import "@/assets/css/index.css";
  import request from "@/utils/request";
  import {CATEGORY_API_URL,ARTICLE_LIST_URL} from "@/utils/api";
  import { getDateDiff } from "@/utils/common";

  export default {
    name: "index",
    mounted() {
      this.getAllCategory();
      this.searchArticleList(this.$route.query.search);
    },

    computed: {
      /**
       * 个人信息
       */
      userId() {
        return this.$store.getters.getUser.id;
      },
      avatarUrl() {
        return this.$store.getters.getUser.avatarUrl;
      },
      name() {
        return this.$store.getters.getUser.name;
      }
    },

    data() {
      return {
        //样式相关
        //分类选择
        categoryBoxIndex: '',
        //排序选择 默认是最新排序
        sortBoxIndex: 0,
        //默认头像
        defaultAvatarUrl: 'this.src="' + require('@/assets/images/defaultAvatar.png') + '"',

        //文章排序
        articleSort: [
          {name: '最新', value: 'new'},
          {name: '最热', value: 'hot'},
          {name: '点赞', value: 'like'},
          {name: '评论', value: 'comment'},
          {name: '收藏', value: 'collection'},
          ],
        //分类
        category: [],
        //文章列表
        articleListPageInfo: [],

        //文章列表请求参数
        articleListDTO: {
          search: '',
          categoryId: '',
          sort: '',
          pageNum: 1,
          pageSize: ''
        }
      }
    },

    methods: {

      /**
       * 把时间戳转换成 *小时前  *天前....
       */
      getTheDateDiff(time) {
        return getDateDiff(time);
      },

      /**
       * 获取所有分类
       */
      getAllCategory() {
        request.get(CATEGORY_API_URL).then(({data}) => {
          this.category = data;
        }).catch(err => {
          console.log(err);
        })
      },
      /**
       * 获取文章列表
       */
      getArticleList(params) {
        request.get(ARTICLE_LIST_URL, {
          params: params
        }).then(({data})=> {
          this.articleListPageInfo = data;
        }).catch(err => {
          console.log(err);
        })
      },
      /**
       * 根据分类渲染文章列表
       */
      categoryArticleList(index, categoryId) {
        //渲染
        this.categoryBoxIndex = index;
        //初始化当前页为第一页
        this.articleListDTO.pageNum = 1;
        //文章分类筛选
        this.articleListDTO.categoryId = categoryId;
        this.getArticleList(this.articleListDTO);

      },
      /**
       * 文章排序
       */
      sortArticleList(index, sort) {
        //渲染
        this.sortBoxIndex = index;

        //初始化当前页为第一页
        this.articleListDTO.pageNum = 1;
        //文章排序方式
        this.articleListDTO.sort = sort;
        this.getArticleList(this.articleListDTO);
      },
      /**
       * 获取第n页的文章列表
       */
      pageNumArticleList(pageNum) {
        let url = window.location.href;
        this.articleListDTO.pageNum = pageNum;
        this.getArticleList(this.articleListDTO)
      },
      /**
       * 搜索获取文章列表
       */
      searchArticleList(search) {
        this.articleListDTO.search = search;
        this.getArticleList(this.articleListDTO)
      },

      /**
       * 文章详情页
       */
      articleDetail(id) {
        this.$router.push({ name: 'article', params: {id: id}})
      }

    }

  }
</script>

<style scoped>


</style>
