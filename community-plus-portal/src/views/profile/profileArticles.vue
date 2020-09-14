<template>
  <div class="article-box">
    <!-- 标题 -->
    <div class="article-title">
      <i class="iconfont" style="font-size: 20px;">&#xe7e2;&nbsp;</i>我的文章
    </div>

    <!-- 文章列表 -->
    <!-- 没有数据时显示-->
    <div v-show="!articlePageInfo || !articlePageInfo.total || articlePageInfo.total===0" style="text-align: center;padding:40px 0;user-select: none">
      <i class="iconfont" style="font-size: 120px;color: rgba(0,181,173,0.7);">&#xe612;</i>
    </div>

    <div v-show="articlePageInfo && articlePageInfo.total">
      <div class="article-list" :key="index" v-for="(i,index) in articlePageInfo.list">
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
        <div class="article-list-title" >
          <router-link :to="'/articles/'+i.id">
            {{i.title}}
            <div :class="{'article-list-showFlag-1': i.showFlag === 1,'article-list-showFlag-2': i.showFlag === 2}">
              {{i.showFlag===1?'公开':'私有'}}
            </div>
          </router-link>
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
          {{getTheDateDiff(i.createTime)}}
        </div>

        <!-- 编辑和删除按钮-->
        <div class="article-edit-delete-button-box">
          <!-- edit -->
          <div class="article-edit-button" @click="editArticle(i.id)">
            <i class="el-icon-edit"></i>
          </div>
          <!-- delete -->
          <div class="article-delete-button" @click="deleteArticle(i.id)">
            <i class="el-icon-delete"></i>
          </div>
        </div>
      </div>
    </div>

    <!-- 分页 -->
    <div v-show="articlePageInfo && articlePageInfo.total" class="pagination-box" >
      <el-pagination
        background
        layout="prev, pager, next,jumper"
        :current-page.sync="articlePageInfo.pageNum"
        :page-size="articlePageInfo.pageSize"
        :total="articlePageInfo.total"
        @current-change="pageNumArticleList(articlePageInfo.pageNum)"
        @prev-click="pageNumArticleList(articlePageInfo.pageNum - 1)"
        @next-click="pageNumArticleList(articlePageInfo.pageNum + 1)"
      >
      </el-pagination>
    </div>

  </div>
</template>

<script>
import request from "@/utils/request";
import {getDateDiff} from "@/utils/common";
import {PROFILE_ARTICLE_LIST_URL,ARTICLE_DELETE_URL} from "@/utils/api";


export default {
  name: "profileArticles",
  mounted() {
    this.pageNumArticleList(1);
  },
  data() {
    return {
      userId: this.$store.getters.getUser.id,

      articlePageInfo: []
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
     * 分页请求文章列表
     * @param pageNum
     */
    pageNumArticleList(pageNum) {
      if (this.userId) {
        request.get(PROFILE_ARTICLE_LIST_URL + this.userId,
          {params: {
              pageNum: pageNum
            }
          }).then(({data}) => {
          this.articlePageInfo = data;
        }).catch(err => {
          console.log(err);
          this.$notify.error({
            message: '获取个人文章列表失败'
          });
        })
      }
    },

    /**
     * 编辑文章
     * @param id
     */
    editArticle(id) {
      this.$router.push({ name: 'edit', params: {id: id}})
    },

    /**
     * 删除文章
     * @param id
     */
    deleteArticle(id) {
      this.$confirm('你确定要删除该篇文章吗?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //请求后台删除评论
        request.delete(ARTICLE_DELETE_URL + id).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功'
          });
          location.reload();
        }).catch(err => {
          console.log(err);
          this.$message.error('服务器异常，请稍后再试！');
        })
      }).catch(() => {
      });
    }

  }
}
</script>

<style scoped>
a {
  text-decoration: none;
}
.article-box {
  padding: 20px;
}

.article-title {
  font-size: 20px;
  height: 40px;
  color: #606266;
  border-bottom: 1px solid #DCDFE6;
}
.pagination-box {
  margin-top: 20px;
  border-radius: 5px;
  background-color: #fff;
  padding: 5px 0;
}

/*文章list*/
.article-list {
  padding: 10px 0;
  height: 40px;
  border-bottom: 1px solid #EBEEF5;
}
.article-list-isOriginal {
  float: left;
  height: 16px;
  line-height: 16px;
  text-align: center;
  font-size: 12px;
  margin: 12px 4px 12px 3px;
  user-select: none;
  padding: 0 5px;
  border-radius: 4px;
  background-color: rgba(0,0,0,0.1);
}
.article-list-category-img {
  float: left;
  height: 16px;
  width: 16px;
  margin: 12px 3px;
  cursor: pointer;
}
.article-list-title {
  float: left;
  height: 18px;
  line-height: 18px;
  font-size: 16px;
  margin: 11px 2px;
  width: 550px;
  user-select: none;
  cursor: pointer;
  overflow: hidden;
  text-overflow:ellipsis;
  white-space: nowrap;
}
.article-list-title a {
  color: #888888;
}
.article-list-showFlag-1 {
  display: inline-block;
  width: 30px;
  color: #dd4848;
  font-size: 15px;
  font-style: italic;
  margin: 0 5px;
}
.article-list-showFlag-2 {
  display: inline-block;
  width: 30px;
  color: #63dd48;
  font-size: 15px;
  font-style: italic;
  margin: 0 5px;
}
.article-list-publishTime {
  float: right;
  height: 18px;
  line-height: 18px;
  font-size: 13px;
  font-style: italic;
  margin: 11px;
  user-select: none;
}
.articleCount-style {
  width: 120px;
  margin: 0 auto;
  font-size: 12px;
  font-style: italic;
  text-align: center;
}
.article-edit-delete-button-box {
    float: right;
    height: 24px;
    line-height: 24px;
    font-size: 13px;
    font-style: italic;
    margin: 8px;
    user-select: none;
}
.article-edit-button {
  float: left;
  height: 24px;
  width: 24px;
  border: 1px solid #909399;
  border-radius: 50%;
  text-align: center;
  cursor: pointer;
  margin-right: 8px;
}
.article-edit-button:hover {
  border: 1px solid #28b3ea;
  color: #28b3ea;
}
.article-delete-button {
  float: left;
  height: 24px;
  width: 24px;
  border: 1px solid #909399;
  border-radius: 50%;
  text-align: center;
  cursor: pointer;
}
.article-delete-button:hover {
  border: 1px solid #dd4848;
  color: #dd4848;
}
</style>
