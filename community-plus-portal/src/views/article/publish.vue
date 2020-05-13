<template>
  <div class="publish-box">
    <div class="publish-logo"><i class="iconfont" style="font-size: 20px;">&#xe708;</i>新建文章</div>
    <el-divider><i class="el-icon-mobile-phone"/></el-divider>
    <!-- 标题 -->
    <div>
      <el-input
        placeholder="文章标题..."
        v-model="articleDTO.title"
        clearable>
      </el-input>
    </div>
    <!-- markdown -->
    <div  class="editor">
      <mavon-editor ref=md @imgAdd="$imgAdd" placeholder="文章内容..." v-model="articleDTO.content"  style="min-height: 600px;"/>
    </div>
    <!-- 标签-->
    <div style="user-select:none;margin-left: 919px">
      <button class="save-button">
        <i class="iconfont" style="font-size: 16px;">&#xe6d0;</i>&nbsp;保存草稿
      </button>
      &nbsp;&nbsp;<span >or</span>&nbsp;&nbsp;
      <button class="publish-button" @click="showPublishDialog()">
        <i class="iconfont" style="font-size: 16px;">&#xe651;</i>&nbsp;发布文章
      </button>
    </div>


    <!--文章发布框 -->
    <div style="user-select: none">
      <el-dialog
        :visible.sync="publishDialogVisible"
        :close-on-press-escape="false"
        :close-on-click-modal="false"
        width="25%"
      >
        <div slot="title" class="publishDialog-title">发布文章</div>
        <!-- 警告 -->
        <div class="publish-warning">
          <i class="iconfont" style="font-size: 11px;">&#xe62f;</i>
          请勿发布涉及政治、广告、营销、翻墙、违反国家法律法规等内容
        </div>
        <div style="font-size: 12px">
          <!-- 分类 -->
          <div>
            文章分类:&nbsp;
            <el-select size="mini" v-model="articleDTO.categoryId" placeholder="请选择分类">
              <el-option :key="index" v-for="(i,index) in category" :label="i.name" :value="i.id"/>
            </el-select>
          </div>
          <!-- 标签 -->
          <div style="margin-top: 16px">
            文章标签:&nbsp;
            <el-input
              class="input-new-tag"
              v-if="inputVisible"
              v-model="inputValue"
              ref="saveTagInput"
              size="mini"
              @keyup.enter.native="handleInputConfirm"
              @blur="handleInputConfirm"
              maxlength="20"
            >
            </el-input>
            <button class="newTag-button" @click="showInput">+ New Tag</button>
            <div style="margin-top: 6px;">
              <el-tag
                size="mini"
                :key="i"
                v-for="i in articleDTO.tag"
                closable
                :disable-transitions="false"
                @close="handleClose(i)">
                {{i}}
              </el-tag>
            </div>
          </div>
          <!-- 类型 -->
          <div style="margin-top: 16px">
            文章类型:&nbsp;
            <el-select size="mini" v-model="articleDTO.isOriginal" placeholder="请选择">
              <el-option  label="原创" value="0"/>
              <el-option  label="转载" value="1"/>
            </el-select>
          </div>
          <!-- 发布形式 -->
          <div style="margin-top: 16px">
            发布形式:&nbsp;
            <template>
              <el-radio v-model="articleDTO.showFlag"  label="0">公开</el-radio>
              <el-radio v-model="articleDTO.showFlag" label="1">私密</el-radio>
            </template>
          </div>
          <div style="margin-top: 25px;margin-left: 147px">
            <button class="publishDialog-cancelButton" @click="publishDialogVisible=false">取消</button>
            <button class="publishDialog-saveButton">保存为草稿</button>
            <button class="publishDialog-publishButton" @click="publish()">发布文章</button>
          </div>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
  import "@/assets/css/publish.css";
  import request from "@/utils/request";
  import { CATEGORY_API_URL,UPLOAD_IMAGE_URL } from "@/utils/api";


    export default {
      name: "publish",
      //进入此页面之前先判断
      beforeRouteEnter: ((to,from,next) => {
        next(vm => {
          vm.isLogin();
        });
      }),

      mounted() {
        this.getAllCategory();
      },

      data(){
        return {
          //发布框相关
          publishDialogVisible: false,
          //标签相关
          inputVisible: false,
          inputValue: '',

          //文章提交
          articleDTO: {
            userId: this.$store.getters.getUser.id, //作者id
            title: '', //标题
            content: '', //内容
            categoryId: '', //分类id
            tag: [], //标签
            isOriginal: '', //原创 or 转载
            showFlag: '0' //公开 or 私密
          },

          //分类
          category: [
            {
              id: '1',
              name: '前端'
            },
            {
              id: '2',
              name: '后端'
            }
          ]
        }
      },
      methods: {
        //标签相关方法
        handleClose(tag) {
          this.articleDTO.tag.splice(this.articleDTO.tag.indexOf(tag), 1);
        },
        showInput() {
          if (this.articleDTO.tag.length >= 3) {
            this.$message({
              message: '最多添加3个标签',
              type: 'warning'
            });
            return;
          }
          this.inputVisible = true;
          this.$nextTick(_ => {
            this.$refs.saveTagInput.$refs.input.focus();
          });
        },
        handleInputConfirm() {
          let inputValue = this.inputValue;
          if (inputValue) {
            this.articleDTO.tag.push(inputValue);
          }
          this.inputVisible = false;
          this.inputValue = '';
        },
        //判断是否已经登录,若已登录,则不能使用注册功能
        isLogin() {
          const isLogin = this.$store.getters.getUser.id;
          if (!isLogin) {
            this.$message({
              message: '你必须登录之后才能执行此操作！',
              type: 'warning'
            });
            this.$router.push("/");
          }
        },
        //获取所有分类
        getAllCategory() {
          request.get(CATEGORY_API_URL).then(({data}) => {
            this.category = data;
          }).catch(err => {
            console.log(err);
          })
        },

        //显示发布框dialog
        showPublishDialog() {
          if(!this.articleDTO.title.trim()) {
            this.$message({
              message: '文章标题不能为空！',
              type: 'warning'
            });
            return;
          }
          if(!this.articleDTO.content.trim()) {
            this.$message({
              message: '文章内容不能为空！',
              type: 'warning'
            });
            return;
          }
          this.publishDialogVisible = true;
        },
        //上传图片
        // 绑定@imgAdd event
        $imgAdd(pos, $file){
          // 第一步.将图片上传到服务器.
          let formData = new FormData();
          formData.append('image', $file);
          request({
            url: UPLOAD_IMAGE_URL,
            method: 'post',
            data: formData,
            headers: { 'Content-Type': 'multipart/form-data' },
          }).then(({data}) => {
            // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
            // $vm.$img2Url 详情见本页末尾
            this.$refs.md.$img2Url(pos, data.path);
          })
        },
        //发布文章
        publish() {
          //发布前先校验
          if(!this.articleDTO.categoryId) {
            this.$message({
              message: '请选择文章分类！',
              type: 'warning'
            });
            return;
          }
          if(!this.articleDTO.isOriginal) {
            this.$message({
              message: '请选择文章类型！',
              type: 'warning'
            });
            return;
          }
          alert(this.articleDTO.userId+ " "+ this.articleDTO.categoryId + " "+this.articleDTO.isOriginal + " " + this.articleDTO.showFlag)
        }
      }
    };

</script>

<style scoped>

</style>
