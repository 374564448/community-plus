
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
                 :key="index" v-for="(i,index) in articleSort" @click="sortArticleList(index,i.value)">{{i.name}}
            </div>
          </div>
          <!-- 文章list-->
          <div class="article-list" :key="index" v-for="(i,index) in articleList">
            <!-- 作者头像 -->
            <div style="cursor: pointer">
              <el-image
                style="width: 40px; height: 40px; border-radius: 50%;float: left"
                :src="i.avatarUrl"
                :onerror="defaultAvatarUrl"
                fit="fill"/>
            </div>
            <!-- 文章 原创 & 转载 -->
            <div class="article-list-isOriginal" v-text="i.isOriginal===0?'原创':'转载'"></div>
            <!--文章分类图片-->
            <div class="article-list-category-img">
              <el-image
                style="width: 16px; height: 16px; border-radius: 3px;"
                :src="i.categoryImage"
                fit="fill"/>
            </div>
            <!-- 文章标题 -->
            <div class="article-list-title">{{i.title}}
              <div class="article-list-newFlag" v-if="new Date().getTime()-i.modifyTime<604800000">new</div>
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

        <!-- 分页 -->
        <div class="pagination-box">
          <el-pagination
            background
            layout="prev, pager, next"
            :total="1000">
          </el-pagination>
        </div>
      </el-col>

      <!-- 右边信息栏 -->
      <el-col :span="6">
        <div class="info-box"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import "@/assets/css/index.css";
  import request from "@/utils/request";
  import {CATEGORY_API_URL} from "@/utils/api";
  import { getDateDiff } from "@/utils/common";

  export default {
    name: "index",
    mounted() {
      this.getAllCategory();
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

        test: 1589472620622,
        //文章排序
        articleSort: [
          {name: '最新', value: 'new'},
          {name: '最热', value: 'hot'},
          {name: '点赞', value: 'like'},
          {name: '评论', value: 'comment'},
          {name: '收藏', value: 'collection'},
          {name: '我的关注', value: 'myFocus'},
          ],

        //分类
        category: [],

        //文章列表
        articleList: [
          {
            id: '',
            userId: '',
            avatarUrl: '',
            categoryId: '',
            categoryImage: 'http://banmingi-community-plus.oss-cn-qingdao.aliyuncs.com/8ce89335-e8f6-4191-bd07-da08f73916a1.png',
            title: 'Java反射机制',
            viewCount: 3,
            likeCount: 6,
            commentCount: 0,
            collectionCount: 0,
            isOriginal: 0,
            modifyTime: 1589472620622
          },
        ],
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
       * 根据分类渲染文章列表
       */
      categoryArticleList(index, categoryId) {
        this.categoryBoxIndex = index;
      },

      /**
       * 文章排序
       */
      sortArticleList(index, sort) {
        this.sortBoxIndex = index;
      }
    }

  }
</script>

<style scoped>


</style>
