<template>
  <!-- 个人名片 -->
  <div class="card">
    <!--展示名片头部--头像名称-->
    <div class="card-header">
      <el-avatar :size="50" :src="user.avatar"></el-avatar>
      <p style="margin-top: 0px;">别亭</p>
     <div class="decoration-border"></div>
    <!--卡片中部--github地址-->
      <div class="card-middle">
        <div style="max-width: 40px;margin-left: 40px">
          <el-image :fit="fit"
                    src="https://cdn.jsdelivr.net/gh/yubifeng/blog-resource/bloghosting//website/static/githubLogo.webp"
                    style="cursor:pointer" @click="goToGithub"></el-image>
        </div>    
      </div>
    </div>
    
    <div class="card-middle">
      
    </div>
    <!--卡片底部--个人喜好-->
    <!-- <div class="card-footer">
      <el-collapse>
        <el-collapse-item name="1" style="padding-left: 20px;padding-right: 20px" title="最喜欢的动漫">
          <div>轻音少女、辉夜大小姐想让我告白、青春猪头少年不会梦到兔女郎学姐、路人女主、辉夜大小姐想让我告白、超炮、俺妹</div>
        </el-collapse-item>
        <el-collapse-item name="2" style="padding-left: 20px;padding-right: 20px" title="最喜欢的女孩子">
          <div>樱岛麻衣、亚丝娜、高坂桐乃、五更琉璃、英梨梨、珈百璃、、和泉纱雾</div>
        </el-collapse-item>
        <el-collapse-item name="3" style="padding-left: 20px;padding-right: 20px" title="最喜欢的游戏">
          <div>暂无</div>
        </el-collapse-item>
        <el-collapse-item name="4" style="padding-left: 20px;padding-right: 20px" title="最喜欢的颜色">
          <div>蓝色、紫色</div>
        </el-collapse-item>
      </el-collapse>
    </div> -->
    <div class="card-footer">
      <div class="footer-header"> 
        <span >最近文章</span>
      </div>
      <div class="decoration-border"></div>
      <div v-for="item in blogs" :key="item.id" class="footer-body">
        <a :href="'/blog/'+ item.id" style="text-decoration: none;">
            <div class="footer-body-item" style="background-size: cover;" :style="'background-image: url(' + item.firstPicture + ')'">
            <div class="footer-body-font">
              <div> {{ item.createTime.split(' ')[0] }}</div>
              <div class="footer-title">{{ item.title }}</div>
            </div>
          </div>
        </a>
      </div>
      <!-- <div class="footer-body">
        <a href="https://zhaoq.me/archives/cod16-theater-how-to-increase-fps-frame-count-and-make-the-picture-not-blurred.html" style="text-decoration: none;">
          <div class="footer-body-item" style="background-image: url(https://cdn.jsdelivr.net/gh/huogit/blog-resource/bloghosting/image/2022/9/24/banner.png);background-size: cover;">
            <div class="footer-body-font">
              <div> 2020 年 03 月 19 日</div>
              <div class="footer-title">COD16战区 - 如何提升FPS帧数以及让画面不模糊</div>
            </div>
          </div>
        </a>
      </div>  -->
    
    </div>
  </div>
</template>

<script>
import avatar from"../assets/avatar.png"

export default {
  name: "Card",
  data() {
    return {
      blogs:[],
      hasLogin: false,
      fit: 'fill',
      user: {
        avatar: avatar
      },
    }
  },
  methods: {
    //跳转到个人github
    goToGithub() {
      window.location.href = "https://github.com/huogit"
    },
    fetchData(){
      this.$axios.get('/new/blogs').then((res) => {
        console.log("resdata",res.data)
        this.blogs = res.data.data.blogs
      })
    }
  },
  created() {
    this.fetchData()
  }
}
</script>

<style scoped>
.card {
  width: 250px;
}
.card-header {
  text-align: center;
  padding: 20px 0px 15px 0px;
  background-color: white;
}
.card-middle{
  padding: 15px 0px 0px 0px;
}

.decoration-border{
  border-style:solid;
	border-color: #1118271a;
  border-bottom: 1px;
  margin-left: 1rem;
  margin-right: 1rem;

}

.card-footer {
  margin-top: 20px;
  background-color: white;
}
.footer-header{
  font-size: 18px;
  font-weight: 400;
  padding: 10px 15px;
  box-sizing: border-box;
  
}
.footer-body{
  border: 1px;
  padding: 10px 15px;
  box-sizing: border-box;
  
}
.footer-body-item{
  width: 100%;
  height: 125px
}
.footer-body-font{
  padding: 85px 10px 10px 10px;
  font-size:11px;
  color:white;
}
.footer-title{
   /* 用省略号 */
  text-overflow:ellipsis;
  /*  超出部分隐藏 */
  overflow:hidden;
  /* 文本不换行，这样超出一行的部分被截取，显示... */
  white-space:nowrap;
  font-size: 13px;
  font-weight: 500;
}

.card-header:hover {
  -webkit-box-shadow: #ccc 0px 10px 10px;
  -moz-box-shadow: #ccc 0px 10px 10px;
  box-shadow: #ccc 0px 10px 10px;
}
</style>