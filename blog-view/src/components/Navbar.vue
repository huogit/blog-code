<template>
  <!--顶部导航栏-->
  <div class="blog-navbar" :class="{'transparent':$route.name==='Home' }">

    <router-link style="text-decoration-line: none;color: white" to="/">
      <h3 :class="{'m-mobile-show': mobileHide,'active':$route.name==='About'}"
          class="ui header item m-blue" style="display: inline;padding:20px 20px 20px 60px">别亭の
        Blog</h3>
    </router-link>

    <router-link :class="{'m-mobile-show': mobileHide,'active':$route.name==='Index'}" class="item" style="text-decoration-line: none;color: white;padding:20px;"
                 to="/">
      首页
    </router-link>

    <!--el-Dropdown 下拉菜单-->
    <el-dropdown trigger="click" @mousedown.native="getTypes">

				<span :class="{'m-mobile-show': mobileHide,'active':$route.name==='Category'}" class="el-dropdown-link item"
              style="text-decoration-line: none;color: white; font-size: 16px;padding:20px">
					分类<i class="el-icon-arrow-down el-icon--right"></i>
				</span>

      <el-dropdown-menu slot="dropdown" style="background-color: #333333;padding: 20px">
        <el-dropdown-item v-for="(type,index) in types" :key="index" @click.native="categoryRoute(type.typeName)" style="color: white">
          {{ type.typeName }}
        </el-dropdown-item>
      </el-dropdown-menu>

    </el-dropdown>


    <router-link :class="{'m-mobile-show': mobileHide,'active':$route.name==='Archives'}" class="item" style="text-decoration-line: none;color: white;padding:20px"
                 to="/archives">
      归档
    </router-link>

    <router-link :class="{'m-mobile-show': mobileHide,'active':$route.name==='Friends'}" class="item" style="text-decoration-line: none;color: white;padding:20px"
                 to="/friends">
      友链
    </router-link>
    <router-link :class="{'m-mobile-show': mobileHide,'active':$route.name==='About'}" class="item" style="text-decoration-line: none;color: white;padding: 20px"
                 to="/about">
      关于我
    </router-link>

    <!--自带防抖-->
    <!--el-autocomplete 带建议Input 输入框-->
    <el-autocomplete  v-model="queryString" :class="{'m-mobile-hide': mobileHide}" :fetch-suggestions="debounceQuery"
                     class="right item m-search"
                     placeholder="请输入内容" popper-class="m-search-item" style="margin-left: 500px; "
                     suffix-icon="el-icon-search" @select="handleSelect">
      <i slot="suffix" class="search icon el-input__icon"></i>
      <template slot-scope="{ item }">
        <div class="title">{{ item.title }}</div>
        <span class="content">{{ item.content }}</span>
      </template>
    </el-autocomplete>


  </div>
</template>

<script>
export default {
  name: "Navbar",
  data() {
    return {
      types: {},
      input: '',
      queryString: '',
      queryResult: [],
      timer: null,
      mobileHide: false, //如果是手机，则为true
      ifShowInput: false


    };
  },

  methods: {
    debounceQuery(queryString, callback) {
      //如果setTimeout( ) 开始了循环的工作, 我们要使它停下来, 可使用 clearTimeout( ) 这方法。
      this.timer && clearTimeout(this.timer)
      
      //setTimeout() 是属于 window 的方法，该方法用于在指定的毫秒数后 调用 函数或计算表达式。 
      ///1秒后执行querySearchAsync() 方法
      this.timer = setTimeout(() => this.querySearchAsync(queryString, callback), 1000)
    },
    // 获取是否是手机
    getIsPhone() {

      let flag = navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i)
      return flag;
    },

    //请求搜索接口，执行搜索
    querySearchAsync(queryString, callback) {
      if (queryString == null
          || queryString.trim() === ''
          || queryString.indexOf('%') !== -1
          || queryString.indexOf('_') !== -1
          || queryString.indexOf('[') !== -1
          || queryString.indexOf('#') !== -1
          || queryString.indexOf('*') !== -1
          || queryString.trim().length > 20) {
        return
      }

      const _this = this
      this.$axios.get('/search?queryString=' + queryString).then((res) => {

        _this.queryResult = res.data.data
        if (_this.queryResult.length === 0) {
          _this.queryResult.push({title: '无相关搜索结果'})
        }


        console.log(_this.queryResult)
        //通过 callback(data) 返回到 autocomplete 组件中
        callback(_this.queryResult)


      }).catch(() => {
        _this.msgError("请求失败")
      })


    },
    //跳转到分类详情页
    categoryRoute(name) {
      this.$router.push(`/category/${name}`)
    },

    //处理建议
    handleSelect(item) {
      if (item.id) {
        this.$router.push(`/blog/${item.id}`) //点击输入建议 执行跳转页面
      }
    },


    getTypes() {
      const _this = this
      this.$axios.get('/types').then((res) => {

        _this.types = res.data.data;


      })
    },

  },
  mounted() {
    if (this.getIsPhone()) {
      this.mobileHide = true
    }

  },


}
</script>

<style scoped>

.el-popper .popper__arrow::after {
  content: none !important;
}
.popper__arrow {
  display: none !important;
}

.el-dropdown-link {
  outline-style: none !important;
  outline-color: unset !important;
  height: 100%;
  cursor: pointer;
}

.el-dropdown-menu {
  margin: 20px 0 0 0 !important;
  padding: 0 !important;
  border: 0 !important;
  background: #1b1c1d !important;
}


.el-dropdown-menu__item {
  padding: 0px 15px 0px 15px !important;

}

.el-dropdown-menu__item:hover {
   background-color: #399BDD!important;
}


.el-menu-demo {
  border-bottom-color: #ffffff !important;
}

.blog-navbar {


  height: 50px;
  padding-top:  20px;
  background-color: #333333;



  margin-left: auto !important;
  margin-right: auto !important;


}

.m-search {
  min-width: 220px;
  padding: 0 !important;
  margin-top: -5px;
}

.m-search input {
  color: rgba(255, 255, 255, .9);;
  border: 0px !important;
  background-color: inherit;



}

.m-search i {
  color: rgba(255, 255, 255, .9) !important;
}

.m-search-item {

  min-width: 350px !important;

}


.m-search-item li {
  line-height: normal !important;
  padding: 8px 10px !important;
  width: 1000px;


}

.m-search-item li .title {
  text-overflow: ellipsis;
  overflow: hidden;
  color: rgba(0, 0, 0, 0.87);
}

.m-search-item li .content {
  text-overflow: ellipsis;
  font-size: 12px;
  color: rgba(0, 0, 0, .70);
}

.m-mobile-hide {
  display: none !important;

}

.m-mobile-show {
  padding: 0px 1px 0px 1px;
  margin: 20px 1px 0px 1px;

}
.el-autocomplete {
  position: absolute;
  right: 100px;
}


</style>

