<template>
  <div>
    <div class="articles-box">
      <el-row :gutter="16">
        <el-col :span="5">
          <!--作者基本信息-->
          <div class="author-info-box">
            <el-row>
              <el-col :span="18">
                <div>
                  <!--名字-->
                  <div class="author-name">
                    {{articleDTO.userDTO.name}}
                  </div>
                  <!--个性签名-->
                  <el-popover
                    placement="bottom"
                    width="150"
                    trigger="hover"
                  >
                    <div style="font-size: 12px;color: #909399;">{{articleDTO.userDTO.bio}}</div>
                    <div slot="reference" class="author-bio">
                      {{articleDTO.userDTO.bio}}
                    </div>
                  </el-popover>
                </div>
              </el-col>
              <el-col :span="6">
                <!--头像-->
                <div class="author-avatar">
                  <img style="height: 50px;width: 50px;border-radius: 50%" :src="articleDTO.userDTO.avatarUrl"/>
                </div>
              </el-col>
            </el-row>
            <!-- 用户数据信息-->
            <div class="author-data">
              <div>
                <el-row :gutter="1">
                  <el-col :span="6">
                    <!-- 发布文章数量 -->
                    <div style="text-align: center;color: #606266">
                      <div style="height: 20px;line-height: 20px;font-size: 13px;">文章</div>
                      <div style="height: 20px;line-height: 20px;font-size: 12px;font-weight: 600">136</div>
                    </div>
                  </el-col>
                  <el-col :span="6">
                    <!-- 粉丝数量 -->
                    <div style="text-align: center;color: #606266">
                      <div style="height: 20px;line-height: 20px;font-size: 13px;">粉丝</div>
                      <div style="height: 20px;line-height: 20px;font-size: 12px;font-weight: 600">25</div>
                    </div>
                  </el-col>
                  <el-col :span="6">
                    <!-- 被点赞总数量 -->
                    <div style="text-align: center;color: #606266">
                      <div style="height: 20px;line-height: 20px;font-size: 13px;">点赞</div>
                      <div style="height: 20px;line-height: 20px;font-size: 12px;font-weight: 600">1232</div>
                    </div>
                  </el-col>
                  <el-col :span="6">
                    <!-- 被收藏总数量 -->
                    <div style="text-align: center;color: #606266">
                      <div style="height: 20px;line-height: 20px;font-size: 13px;">收藏</div>
                      <div style="height: 20px;line-height: 20px;font-size: 12px;font-weight: 600">77</div>
                    </div>
                  </el-col>
                </el-row>
              </div>
            </div>
            <!-- 排名数据信息 -->
            <div class="sort-data">
              <el-row :gutter="1">
                <el-col :span="8">
                  <!-- 浏览 -->
                  <div style="text-align: center;color: #606266">
                    <div style="height: 20px;line-height: 20px;font-size: 13px;">浏览</div>
                    <div style="height: 20px;line-height: 20px;font-size: 12px;font-weight: 600">147057</div>
                  </div>
                </el-col>
                <el-col :span="8">
                  <!-- 浏览总量 -->
                  <div style="text-align: center;color: #606266;border: 1px solid #DCDFE6;border-radius: 50%">
                    <div style="height: 20px;line-height: 20px;font-size: 13px;">排名</div>
                    <div style="height: 20px;line-height: 20px;font-size: 12px;font-weight: 600">1277</div>
                  </div>
                </el-col>
                <el-col :span="8">
                  <!-- 积分数 -->
                  <div style="text-align: center;color: #606266">
                    <div style="height: 20px;line-height: 20px;font-size: 13px;">积分</div>
                    <div style="height: 20px;line-height: 20px;font-size: 12px;font-weight: 600">1040</div>
                  </div>
                </el-col>
              </el-row>
            </div>
            <!--关注和私信-->
            <div class="contactButton-box">
              <el-row>
                <el-col :span="12">
                  <!--关注-->
                  <div class="focusTheAuthor">
                    <i class="iconfont" style="font-size: 15px;">&#xe605;</i>&nbsp;关注
                  </div>
                </el-col>
                <el-col :span="12">
                  <!--私信-->
                  <div class="privateChatAuthor">
                    <i class="iconfont" style="font-size: 15px;">&#xe60b;</i>&nbsp;私聊
                  </div>
                </el-col>
              </el-row>
            </div>
          </div>
          <!--所有文章按钮-->
          <div class="author-all-article-button">
            <i class="iconfont" style="font-size: 16px;">&#xe87c;</i>&nbsp;所有文章
          </div>
          <!-- 作者文章列表-->
          <div class="authorPopularArticle">
            <div style="border-bottom: 2px solid #DCDFE6;user-select: none;">
              <i class="iconfont" style="font-size: 16px;">&#xe645;</i>&nbsp;最受欢迎
            </div>
            <!--列表-->
            <div class="popularArticleList">
              <!-- 点赞数 -->
              <div class="likeTag"><i class="iconfont" style="font-size: 12px;">&#xe60a;</i>125</div>
              <!-- 文章标题 -->
              <span style="font-size: 13px">docker+k8s基础</span>
            </div>
            <div class="popularArticleList">
              <!-- 点赞数 -->
              <div class="likeTag"><i class="iconfont" style="font-size: 12px;">&#xe60a;</i>49</div>
              <!-- 文章标题 -->
              <span style="font-size: 13px">Java反射机制</span>
            </div>
            <div class="popularArticleList">
              <!-- 点赞数 -->
              <div class="likeTag"><i class="iconfont" style="font-size: 12px;">&#xe60a;</i>24</div>
              <!-- 文章标题 -->
              <span style="font-size: 13px">JVM原理</span>
            </div>
          </div>
          <!-- 相似文章推荐-->
          <div class="similarArticle">
            <div style="border-bottom: 2px solid #DCDFE6;user-select: none;">
              <i class="iconfont" style="font-size: 16px;">&#xe60d;</i>&nbsp;相似文章推荐
            </div>
            <div class="popularArticleList">
              <!-- 点赞数 -->
              <div class="likeTag"><i class="iconfont" style="font-size: 12px;">&#xe60a;</i>76</div>
              <!-- 文章标题 -->
              <span style="font-size: 13px">JVM原理</span>
            </div>
          </div>
        </el-col>


        <el-col :span="19">
          <!-- 文章详情-->
          <div class="articleDetail-box">
            <!-- article-title -->
            <div class="articles-title">
              <div class="title">{{articleDTO.title}}</div>
              <div class="count">
                <div :class="['isOriginal-true',{'isOriginal-false':articleDTO.isOriginal===2}]"
                     v-text="articleDTO.isOriginal===1?'原创':'转载'"></div>
                <img style="float: left;width: 18px;height: 18px" :src="articleDTO.category.image"/>
                <div class="category-style">{{articleDTO.category.name}}</div>
                <!--浏览数、评论数、发布时间、更新时间-->
                <div class="count-style">
                  <i class="iconfont" style="font-size: 15px;">&#xe661;</i>&nbsp;{{articleDTO.viewCount}}
                  &nbsp;•&nbsp;
                  <i class="iconfont" style="font-size: 13px;">&#xe604;</i>&nbsp;{{articleDTO.commentCount}}
                  &nbsp;•&nbsp;
                  <span>发布于&nbsp;{{getTheDateDiff(articleDTO.createTime)}}</span>
                  &nbsp;•&nbsp;
                  <span>更新于&nbsp;{{getTheDateDiff(articleDTO.modifyTime)}}</span>
                </div>
                <!--编辑按钮-->
                <div class="edit" v-if="this.$store.getters.getUser.id===articleDTO.userDTO.id"><i class="iconfont"
                                                                                                   style="font-size: 13px;">&#xe66d;</i>编辑
                </div>
              </div>
              <div style="height: 13px;border-bottom: 1px solid #C0C4CC"></div>
            </div>

            <!-- article-content -->
            <mavon-editor boxShadowStyle="none"
                          style="margin: 0"
                          previewBackground="#ffffff"
                          :toolbarsFlag="false"
                          :subfield="false"
                          defaultOpen="preview"
                          v-model="articleDTO.content"
            />
          </div>

          <!--标签 -->
          <div class="article-tag-box">
            <i class="iconfont" style="font-size: 24px;color: #888888">&#xe685;</i>
            <el-tag :key="index" v-for="(i,index) in articleDTO.tag" color="rgba(0,181,173,0.7)" effect="dark"
                    size="mini">{{i}}
            </el-tag>
          </div>

          <!-- 评论 -->
          <div class="comment-box">
            <!-- 评论列表-->
            <div class="comment-list">
              <div>
                <el-row>
                  <el-col :span="2">
                    <!-- 一级评论头像-->
                    <div class="commentator-avatar">
                      <img style="height: 50px;width: 50px;border-radius: 5px;box-shadow: 1px 1px 2px #888888;"
                           :src="articleDTO.userDTO.avatarUrl"/>
                    </div>
                  </el-col>
                  <el-col :span="22">
                    <div class="comment-info-box">
                      <div class="commentator-name-box">
                        <!--一级评论者name-->
                        <div class="commentator-name">banmingi</div>
                        <!--一级评论者积分-->
                        <div class="commentator-bonus">
                          <i class="iconfont" style="font-size: 11px;">&#xe6c9;</i>
                          <span>&nbsp;232&nbsp;积分</span>
                        </div>
                      </div>
                      <!--评论内容框-->
                      <!--一级评论内容-->
                      <div class="comment-content-box">
                        <div class="comment-content">
                          明显第一个面试官更好，起码会点出你的错误，然后引导你一下，我面试的都是嗯嗯嗯嗯...
                        </div>
                        <div class="comment-count-and-action">
                          <!-- 评论时间 -->
                          <span><i class="iconfont" style="font-size: 11px;">&#xe619;</i>&nbsp;一周前</span>
                          <!-- 点赞按钮和点赞数 -->
                          <div :class="['comment-like-button',{'comment-like-button-clicked':commentLikeFlag}]"
                               @click="commentLikeFlag=!commentLikeFlag">
                            <i class="iconfont" style="font-size: 15px;">&#xe60a;</i>&nbsp;0
                          </div>
                          <!-- 展开二级评论按钮 -->
                          <div :class="['comment-second-show-button',{'comment-second-show-button-show':commentSecondShow}]"
                               @click="commentSecondShow=!commentSecondShow">
                            <i class="iconfont" style="font-size: 15px;">&#xe604;</i>&nbsp;7
                          </div>
                        </div>
                        <!-- 二级评论框-->
                        <div>
                          <el-collapse-transition>
                            <div v-show="commentSecondShow" class="comment-second-box">
                              <!--二级评论输入框-->
                              <div>
                                <el-input placeholder="回复..."
                                          v-model="commentSecond"
                                          class="input-with-select">
                                  <el-button slot="append">评论</el-button>
                                </el-input>
                              </div>
                              <!--二级评论列表-->
                              <div class="comment-second-list-box">
                                <div class="commentator-second-name-box">
                                  <!--头像-->
                                  <div style="float: left">
                                    <img :src="articleDTO.userDTO.avatarUrl"
                                         style="height: 26px;width: 26px;margin: 2px"/>
                                  </div>
                                  <!--名字-->
                                  <div class="commentator-second-name">
                                    飞龙在天
                                  </div>
                                  <!--评论时间-->
                                  <div class="commentator-second-comment-time">一周前</div>
                                </div>
                                <!--评论内容-->
                                <div class="commentator-second-content-box">
                                  贼棒，学到老活到老贼棒，学到老活到老贼棒，学到老活到老贼棒，学到老活到老贼棒，学到老活到老贼棒，学到老活到老贼棒，学到老活到老贼棒，学到老活到老贼棒，学到老活到老贼棒，学到老活到老贼棒，学到老活到老贼棒，学到老活到老
                                </div>
                                <!--操作此评论：点赞、评论-->
                                <div class="comment-second-action">
                                  <!-- 点赞按钮和点赞数 -->
                                  <div :class="['comment-second-like-button',{'comment-like-second-button-clicked':commentSecondLikeFlag}]"
                                       @click="commentSecondLikeFlag=!commentSecondLikeFlag">
                                    <i class="iconfont" style="font-size: 15px;">&#xe60a;</i>&nbsp;0
                                  </div>
                                  <!-- 评论此条评论 -->
                                  <div class="comment-second-button">
                                    <i class="iconfont" style="font-size: 15px;">&#xe604;</i>
                                  </div>
                                </div>
                              </div>


                              <div class="comment-second-list-box">
                                <div class="commentator-second-name-box">
                                  <!--头像-->
                                  <div style="float: left">
                                    <img :src="articleDTO.userDTO.avatarUrl"
                                         style="height: 26px;width: 26px;margin: 2px"/>
                                  </div>
                                  <!--名字-->
                                  <div class="commentator-second-name">
                                    飞龙在天
                                  </div>
                                  <!--评论时间-->
                                  <div class="commentator-second-comment-time">一周前</div>
                                </div>
                                <!--评论内容-->
                                <div class="commentator-second-content-box">
                                  贼棒，学到老活到老贼棒，学到老活到老贼棒，学到老活到老贼棒，学到老活到老贼棒，学到老活到老贼棒，学到老活到老贼棒，学到老活到老贼棒，学到老活到老贼棒，学到老活到老贼棒，学到老活到老贼棒，学到老活到老贼棒，学到老活到老
                                </div>
                                <!--操作此评论：点赞、评论-->
                                <div class="comment-second-action">
                                  <!-- 点赞按钮和点赞数 -->
                                  <div :class="['comment-second-like-button',{'comment-like-second-button-clicked':commentSecondLikeFlag}]"
                                       @click="commentSecondLikeFlag=!commentSecondLikeFlag">
                                    <i class="iconfont" style="font-size: 15px;">&#xe60a;</i>&nbsp;0
                                  </div>
                                  <!-- 评论此条评论 -->
                                  <div class="comment-second-button">
                                    <i class="iconfont" style="font-size: 15px;">&#xe604;</i>
                                  </div>
                                </div>
                              </div>


                            </div>
                          </el-collapse-transition>
                        </div>
                      </div>
                    </div>
                  </el-col>
                </el-row>
              </div>
            </div>

            <!--评论文本输入框-->
            <div class="comment-area">
              <!-- 警告 -->
              <div class="comment-warning">
                <i class="iconfont" style="font-size: 16px;">&#xe62f;</i>
                请勿发布不友善或者负能量的内容。与人为善，比聪明更重要！
              </div>
              <!-- 文本框-->
              <el-input
                type="textarea"
                placeholder="评论..."
                :autosize="{ minRows: 4}"
                v-model="comment"
                maxlength="300"
                show-word-limit
              >
              </el-input>
              <!--评论按钮-->
              <div style="margin-top: 15px;padding-bottom:25px;position: relative">
                <button class="comment-button"><i class="el-icon-chat-dot-round">&nbsp;</i>评论</button>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>


    <!-- 点赞、收藏等功能按钮 -->
    <div class="article-action">
      <!--点赞-->
      <div class="action">
        <el-tooltip content="点赞" placement="left" effect="light">
          <div>
            <i class="iconfont" style="font-size: 16px;">&#xe60a;</i>
            <div>{{articleDTO.likeCount}}</div>
          </div>
        </el-tooltip>
      </div>
      <!-- 评论 -->
      <div class="action">
        <el-tooltip content="评论" placement="left" effect="light">
          <div>
            <i class="iconfont" style="font-size: 16px;">&#xe604;</i>
            <div>{{articleDTO.commentCount}}</div>
          </div>
        </el-tooltip>
      </div>
      <!--收藏-->
      <div class="action">
        <el-tooltip content="收藏" placement="left" effect="light">
          <div>
            <i class="iconfont" style="font-size: 16px;">&#xe609;</i>
            <div>{{articleDTO.collectionCount}}</div>
          </div>
        </el-tooltip>
      </div>
      <!--转发-->
      <div class="action">
        <el-tooltip content="转发" placement="left" effect="light">
          <div>
            <i class="iconfont" style="font-size: 16px;">&#xe647;</i>
          </div>
        </el-tooltip>
      </div>

    </div>

  </div>
</template>

<script>
  import request from "@/utils/request";
  import {getDateDiff} from "@/utils/common";
  import {ARTICLE_DETAIL_URL} from "@/utils/api";

  export default {
    name: "articles",
    mounted() {
      this.getArticleDTOById(this.$route.params.id);
    },
    data() {
      return {
        //样式相关
        //一级评论点赞或取消点赞
        commentLikeFlag: false,
        //展开或关闭二级评论框
        commentSecondShow: false,
        //二级评论点赞或取消点赞
        commentSecondLikeFlag: false,

        //文章详情
        articleDTO: {
          id: '',
          title: '',
          content: '',
          tag: [],
          viewCount: '',
          likeCount: '',
          commentCount: '',
          collectionCount: '',
          isOriginal: '',
          createTime: '',
          modifyTime: '',
          category: {id: '', image: '', name: ''},
          userDTO: {id: '', accountType: '', name: '', avatarUrl: '', bio: '', bonus: ''}
        },
        comment: '',
        commentSecond: '',
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
       * 获取文章详情
       * @param id
       */
      getArticleDTOById(id) {
        request.get(ARTICLE_DETAIL_URL + id).then(({data}) => {
          data.tag = data.tag.split(",");
          this.articleDTO = data;
        }).catch(err => {
          window.location.href = "/error";
          console.log(err);
        })
      }
    }
  }
</script>

<style scoped>
  @import '../../assets/css/articles.css';
</style>
