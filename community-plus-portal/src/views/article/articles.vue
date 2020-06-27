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
                <div class="edit" v-show="userId===articleDTO.userDTO.id">
                  <i class="iconfont" style="font-size: 13px;">&#xe66d;</i>编辑
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
            <div class="comment-list" v-for="(commentDTO,index) in commentDTOList" :key="index"
                 :id="'comment'+commentDTO.id">
              <div>
                <el-row>
                  <el-col :span="2">
                    <!-- 一级评论头像-->
                    <div class="commentator-avatar">
                      <img style="height: 50px;width: 50px;border-radius: 5px;box-shadow: 1px 1px 2px #888888;"
                           :src="commentDTO.userDTO.avatarUrl"/>
                    </div>
                  </el-col>
                  <el-col :span="22">
                    <div class="comment-info-box">
                      <div class="commentator-name-box">
                        <!--一级评论者name-->
                        <span v-show="commentDTO.commentatorId === articleDTO.userDTO.id"
                              class="commentatorIsAuthor">作者</span>
                        <div class="commentator-name">{{commentDTO.userDTO.name}}</div>
                        <!--一级评论者积分-->
                        <div class="commentator-bonus">
                          <i class="iconfont" style="font-size: 11px;">&#xe6c9;</i>
                          <span>&nbsp;{{commentDTO.userDTO.bonus}}&nbsp;积分</span>
                        </div>
                      </div>
                      <!--评论内容框-->
                      <!--一级评论内容-->
                      <div class="comment-content-box" :span="3">
                        <div class="comment-content">
                          {{commentDTO.content}}
                        </div>
                        <div class="comment-count-and-action">
                          <!-- 评论时间 -->
                          <span><i class="iconfont" style="font-size: 11px;">&#xe619;</i>&nbsp;{{getTheDateDiff(commentDTO.createTime)}}</span>
                          <!-- 点赞按钮和点赞数 -->
                          <div :class="['comment-like-button',{'comment-like-button-clicked':commentLikeFlag}]"
                               @click="commentLikeFlag=!commentLikeFlag">
                            <i class="iconfont" style="font-size: 15px;">&#xe60a;</i>&nbsp;{{commentDTO.likeCount}}
                          </div>
                          <!-- 展开二级评论按钮 -->
                          <div
                            :class="['comment-second-show-button',{'comment-second-show-button-show':commentDTO.commentSecondShow}]"
                            @click="showCommentSecondList(commentDTO)">
                            <i class="iconfont" style="font-size: 15px;">&#xe604;</i>&nbsp;{{commentDTO.commentCount}}
                          </div>
                          <!--删除评论按钮-->
                          <div v-show="userId===commentDTO.commentatorId" class="comment-delete-button"
                               @click="deleteComment(commentDTO.id)">
                            <i class="iconfont" style="font-size: 15px;">&#xe63d;</i>
                          </div>
                        </div>
                        <!-- 二级评论框-->
                        <div>
                          <el-collapse-transition>
                            <div v-show="commentDTO.commentSecondShow" class="comment-second-box">
                              <div>
                                <!--标签: 被回复的人-->
                                <el-tag v-show="commentDTO.commentSecondParentName"
                                        size="small" type="danger" closable @close="closeCommentTag(commentDTO)">
                                  {{commentDTO.commentSecondParentName}}
                                </el-tag>
                                <!--二级评论输入框-->
                                <el-input
                                  :placeholder="commentDTO.commentSecondParentName?commentDTO.commentSecondParentName:'回复...'"
                                  v-model="commentDTO.commentSecondContent"
                                  :ref="'commentSecondInput'+index"
                                  class="input-with-select"
                                  maxlength="1000"
                                >
                                  <el-button slot="append"
                                             @click="commentCreate(commentDTO.id,commentDTO.currentCommentId,2,commentDTO.commentSecondParentName+commentDTO.commentSecondContent)">
                                    评论
                                  </el-button>
                                </el-input>
                              </div>

                              <!--二级评论列表-->
                              <div class="comment-second-list-box"
                                   v-for="(commentSecondDTO,index2) in commentDTO.commentSecondDTOList" :key="index2"
                                   :id="'comment'+commentSecondDTO.id">
                                <div class="commentator-second-name-box">
                                  <!--头像-->
                                  <div style="float: left;">
                                    <img :src="commentSecondDTO.userDTO.avatarUrl"
                                         style="height: 26px;width: 26px;margin: 2px;border-radius: 3px"/>
                                  </div>
                                  <!--名字-->
                                  <div class="commentator-second-name">
                                    <span v-show="commentSecondDTO.commentatorId === articleDTO.userDTO.id"
                                          class="commentatorIsAuthor">作者</span>
                                    {{commentSecondDTO.userDTO.name}}
                                  </div>
                                  <!--评论时间-->
                                  <div class="commentator-second-comment-time">
                                    {{getTheDateDiff(commentSecondDTO.createTime)}}
                                  </div>
                                </div>
                                <!--评论内容-->
                                <div class="commentator-second-content-box">
                                  <!-- 被回复者的名字 -->
                                  <span class="commentedName" v-show="commentSecondDTO.commentedName">
                                    {{commentSecondDTO.commentedName}}
                                  </span>
                                  <!-- 内容 -->
                                  {{commentSecondDTO.content}}
                                </div>
                                <!--操作此评论：点赞、评论-->
                                <div class="comment-second-action">
                                  <!-- 点赞按钮和点赞数 -->
                                  <div
                                    :class="['comment-second-like-button',{'comment-like-second-button-clicked':commentSecondLikeFlag}]"
                                    @click="commentSecondLikeFlag=!commentSecondLikeFlag">
                                    <i class="iconfont" style="font-size: 15px;">&#xe60a;</i>&nbsp;{{commentSecondDTO.likeCount}}
                                  </div>
                                  <!-- 评论此条评论 -->
                                  <div class="comment-second-button"
                                       @click="turnCommentSecondInput(commentDTO,commentSecondDTO,index)">
                                    <i class="iconfont" style="font-size: 15px;">&#xe604;</i>
                                  </div>
                                  <!--删除此条评论-->
                                  <div v-show="userId===commentSecondDTO.commentatorId"
                                       class="comment-second-delete-button" @click="deleteComment(commentSecondDTO.id)">
                                    <i class="iconfont" style="font-size: 15px;">&#xe63d;</i>
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
            <div class="comment-area" ref="commentInputBox">
              <!-- 警告 -->
              <div class="comment-warning">
                <i class="iconfont" style="font-size: 16px;">&#xe62f;</i>
                请勿发布不友善或者负能量的内容。与人为善，比聪明更重要！
              </div>
              <!-- 文本框-->
              <el-input
                ref="commentInput"
                type="textarea"
                placeholder="评论..."
                :autosize="{ minRows: 4}"
                v-model="commentContent"
                maxlength="1000"
                show-word-limit
              >
              </el-input>
              <!--评论按钮-->
              <div style="margin-top: 15px;padding-bottom:25px;position: relative"
                   @click="commentCreate(articleDTO.id,articleDTO.id,1,commentContent)">
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
          <div @click="turnCommentInput()">
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
  import {ARTICLE_DETAIL_URL, COMMENT_CREATE_URL, GET_COMMENT_LIST_URL, DELETE_COMMENT_URL} from "@/utils/api";

  export default {
    name: "articles",
    mounted() {
      //文章详情
      this.getArticleDTOById(this.$route.params.id);
      //评论列表
      // this.commentList(this.$route.params.id,1,null)
      this.commentList(this.$route.params.id)

      //根据url定位到某一具体评论
      this.$nextTick(() => {

        this.turnCommentBoxByURL()
      })


    },


    data() {
      return {
        //样式相关
        //一级评论点赞或取消点赞
        commentLikeFlag: false,
        //二级评论点赞或取消点赞
        commentSecondLikeFlag: false,

        userId: this.$store.getters.getUser.id,

        /**
         * 文章
         */
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


        /**
         *  评论
         */
        //创建评论
        commentCreateDTO: {
          articleId: '',
          commentListId: '',
          parentId: '',
          commentatorId: '',
          type: '',
          content: ''
        },
        //一级评论的内容
        commentContent: '',
        // //二级评论的内容
        // commentSecondContent: '',
        //一级评论列表
        commentDTOList: [],
        //二级评论列表在获取数据时动态添加到一级评论列表中
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
      },


      /**
       * 定位到一级评论框
       */
      turnCommentInput() {
        //定位到一级评论框
        this.$refs.commentInputBox.scrollIntoView();
        //input获取焦点
        this.$refs.commentInput.focus()
      },
      /**
       *根据id定位到某条具体的评论
       */
      turnCommentBoxByURL() {
        let url = window.location.hash
        if (url && url.length !== 0) {
          let commentId = url.substring(1, url.length);
          setTimeout(function () {
            let comment = document.getElementById(commentId);
            if (comment) {
              comment.scrollIntoView(false);
            }
          }, 1000);
        }
      },
      /**
       * 创建评论
       */
      commentCreate(commentListId, parentId, type, content) {
        //评论前先校验
        //判断是否已登录
        if (!this.userId) {
          this.$message({
            message: '你必须登录之后才能评论！',
            type: 'warning'
          });
          return;
        }
        this.commentCreateDTO.articleId = this.articleDTO.id;
        this.commentCreateDTO.commentListId = commentListId;
        this.commentCreateDTO.parentId = parentId;
        this.commentCreateDTO.commentatorId = this.$store.getters.getUser.id;
        this.commentCreateDTO.type = type;
        this.commentCreateDTO.content = content;
        //评论内容不能为空
        if (!content) {
          this.$message({
            message: '评论内容不能为空！',
            type: 'warning'
          });
          return;
        }
        //请求后端api发布评论
        request({
          url: COMMENT_CREATE_URL,
          method: 'post',
          data: this.commentCreateDTO
        }).then(() => {
          this.$message({
            message: '评论成功！',
            type: 'success'
          });
          location.reload();
        }).catch(err => {
          console.log(err);
          this.$message.error('服务器异常，请稍后再试！');
        })
      },


      /**
       * 评论点赞
       */
      commentAddLike(index) {

      },

      /**
       *展开/关闭 二级评论列表
       */
      showCommentSecondList(dto) {
        dto.commentSecondShow = !dto.commentSecondShow;
      },


      /**
       *定位到二级评论框
       */
      turnCommentSecondInput(dto, dto2, index) {

        if (this.userId === dto2.commentatorId) {
          this.$message({
            message: '不能自己回复自己！',
            type: 'warning'
          });
          return;
        }

        //修改被评论的id
        dto.currentCommentId = dto2.id
        //二级评论输入框清空
        dto.commentSecondContent = '';
        //如果被回复的是作者
        if (dto2.commentatorId === this.articleDTO.userDTO.id) {
          dto.commentSecondParentName = "@" + dto2.userDTO.name + " (作者):"
        } else {
          //如果被回复的是普通评论者
          dto.commentSecondParentName = "@" + dto2.userDTO.name + ":";
        }
        //聚焦到二级评论输入框
        this.$refs['commentSecondInput' + index][0].focus();
      },

      /**
       * 取消评论这个用户(关闭此标签)
       */
      closeCommentTag(dto) {
        //改变要被回复的评论的id为原来的id(当前一级评论的id)
        dto.currentCommentId = dto.id;
        dto.commentSecondParentName = '';
      },


      /**
       * 获取评论列表
       * @param articleId 文章id
       */
      commentList(articleId) {
        request.get(GET_COMMENT_LIST_URL, {
          params: {
            articleId: articleId,
          }
        }).then(({data}) => {
          data.forEach(comment => {
            //新增一个属性,用来是否展示二级评论框(如果二级评论列表不为null则展示)
            comment.commentSecondShow = !!comment.commentSecondDTOList[0];
            //新增一个属性,绑定二级评论输入框内容
            comment.commentSecondContent = '';
            //新增一个属性,用于暂存二级评论中被回复的评论的id,初始值为当前一级评论的id
            comment.currentCommentId = comment.id;
            //新增一个属性,用于暂存二级评论中被回复的人的名字,初始值为空(为空代表回复的是当前一级评论)
            comment.commentSecondParentName = '';
            if (comment.commentSecondDTOList) {
              comment.commentSecondDTOList.forEach(commentSecond => {
                if (commentSecond.commentListId !== commentSecond.parentId) {
                  //如果是针对某个二级评论的回复,就从评论内容中截取出被回复者的名字和回复内容,名字添加到评论的属性中
                  let index = commentSecond.content.indexOf(":");
                  commentSecond.commentedName = commentSecond.content.substring(0, index + 1);
                  //冒号前面还有空格,所以index+2
                  commentSecond.content = commentSecond.content.substring(index + 1, commentSecond.content.length);
                }
              })
            }
          })
          this.commentDTOList = data
        }).catch(err => {
          console.log(err)
        })
      },

      /**
       * 删除评论
       * @param id
       */
      deleteComment(id) {
        this.$confirm('你确定要删除该条评论吗?', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //请求后台删除评论
          request.delete(DELETE_COMMENT_URL + id).then(() => {
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
  @import '../../assets/css/articles.css';
</style>
